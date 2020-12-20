package com.tlcn.thebeats.controllers;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.relation.RelationNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tlcn.thebeats.models.Artist;
import com.tlcn.thebeats.models.ERole;
import com.tlcn.thebeats.models.Role;
import com.tlcn.thebeats.models.User;
import com.tlcn.thebeats.payload.request.LoginRequest;
import com.tlcn.thebeats.payload.request.MailResetRequest;
import com.tlcn.thebeats.payload.request.SignupRequest;
import com.tlcn.thebeats.payload.response.ArtistJwtResponse;
import com.tlcn.thebeats.payload.response.JwtResponse;
import com.tlcn.thebeats.payload.response.MessageResponse;
import com.tlcn.thebeats.repository.ArtistRepository;
import com.tlcn.thebeats.repository.RoleRepository;
import com.tlcn.thebeats.repository.UserRepository;
import com.tlcn.thebeats.security.jwt.JwtUtils;
import com.tlcn.thebeats.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	private ArtistRepository artistRepository;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	public JavaMailSender emailSender;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		long time = jwtUtils.getTimeExpireFromJwtToken(jwt).getTime();
		for (String string : roles) {
			if (string.contains("ROLE_MODERATOR")) {
				Artist artist = artistRepository.findByUserId(userDetails.getId());
				return ResponseEntity.ok(new ArtistJwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
						userDetails.getEmail(), roles, time, artist.getPaypalAccount(), artist.getId(),
						artist.getAvatar(), artist.getCoverImageId(), artist.isActive()));
			}
		}

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
				userDetails.getEmail(), roles, time));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getEmail(), signUpRequest.getUsername(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	// ------------------------Xác nhận token được gửi qua mail
	@PostMapping("/verify")
	public ResponseEntity<?> verifyToken(@RequestBody String token) throws Exception {
		return jwtUtils.verifyCode(token);
	}

	@PostMapping("/forgot")
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody MailResetRequest mail)
			throws RelationNotFoundException {

		return jwtUtils.MailReset(this.emailSender, mail.getEmail());
	}

	// ------------------------------1. Xác nhận password cũ ----------------
//			@RequestMapping(value = "/verifypassword", headers = "Accept=application/json", method = RequestMethod.POST)
//			public ResponseEntity<?> verifyPassword(@RequestBody String password,
//					@RequestHeader(name = "Authorization") String jwt) throws RelationNotFoundException {
//				return jwtUtils.verifyPassword(password, jwt);
//			}

	// --------------------------------Cập nhật password mới
	// ---------------------------
	@RequestMapping(value = "/changepassword", headers = "Accept=application/json", method = RequestMethod.POST)
	public ResponseEntity<?> changePassword(@RequestHeader(name = "Authorization") String token,
			@RequestParam String oldpass, @RequestParam String newpass) throws RelationNotFoundException {
		return jwtUtils.changePassword(oldpass, newpass, token);
	}

	@GetMapping("/get-roles")
	public List<String> getRoles(@RequestParam long id) {

		return userRepository.findRolesOfUser(id);
	}
}
