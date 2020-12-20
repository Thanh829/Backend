package com.tlcn.thebeats.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tlcn.thebeats.models.Artist;
import com.tlcn.thebeats.models.ERole;
import com.tlcn.thebeats.models.ImageFile;
import com.tlcn.thebeats.models.Role;
import com.tlcn.thebeats.models.Song;
import com.tlcn.thebeats.models.User;
import com.tlcn.thebeats.payload.request.ArtistSignupRequest;
import com.tlcn.thebeats.payload.request.EditProfileRequest;
import com.tlcn.thebeats.payload.request.LoginRequest;
import com.tlcn.thebeats.payload.request.SignupRequest;
import com.tlcn.thebeats.payload.response.ArtistJwtResponse;
import com.tlcn.thebeats.payload.response.BoughSongResponse;
import com.tlcn.thebeats.payload.response.JwtResponse;
import com.tlcn.thebeats.payload.response.MessageResponse;
import com.tlcn.thebeats.repository.ArtistRepository;
import com.tlcn.thebeats.repository.BoughtSongRepository;
import com.tlcn.thebeats.repository.ImageFileRepository;
import com.tlcn.thebeats.repository.RoleRepository;
import com.tlcn.thebeats.repository.UserRepository;
import com.tlcn.thebeats.security.jwt.JwtUtils;
import com.tlcn.thebeats.security.services.AmazonS3ClientService;
import com.tlcn.thebeats.security.services.UserDetailsImpl;

@RestController
@RequestMapping("/api/v1/artist")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ArtistController {
	@Autowired
	private ArtistRepository artistRepository; 
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	BoughtSongRepository boughtSongRepository;
	
	@Autowired
	private AmazonS3ClientService amazonS3ClientService;
	
	@Autowired
	private ImageFileRepository imageFileRepository;
//	@PostMapping("/signin")
//	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//		
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//		long time= jwtUtils.getTimeExpireFromJwtToken(jwt).getTime();
//		Artist artist = artistRepository.findByUserId(userDetails.getId());
//		return ResponseEntity.ok(new ArtistJwtResponse(jwt, 
//												 userDetails.getId(), 
//												 userDetails.getUsername(), 
//												 userDetails.getEmail(), 
//												 roles,time,artist.getPaypalAccount()));
//	}

	@GetMapping("/all")
	public List<Artist> getAll()
	{
		return artistRepository.findAllArtist();
	}
	
	@GetMapping("")
	public Artist getOne(@RequestParam int id)
	{
		return artistRepository.findById(id).get();
	}
	
	@GetMapping("/count-active-artist")
	public int countActiveArtist()
	{
		return artistRepository.countActiveArtist();
	}
	
	@GetMapping("/all-artist")
	public List<Artist> getAllArtistActive(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "4") int size)
	{
		Pageable paging = PageRequest.of(page, size);
		Page<Artist> pageArtist= artistRepository.findActiveArtist(paging);
		return pageArtist.getContent();
	}
	
	@PostMapping("disable")
	public Artist disableArtist(@RequestBody int id) {
		Artist artist = artistRepository.findById(id).orElseThrow(()-> new RuntimeException("Artist not found"));
		artist.setActive(false);
		return artistRepository.save(artist);
	}
	
	@GetMapping("/get-song-for-pay")
	public List<?> getSongOfForPay(@RequestParam int artistId)
	{
		return this.boughtSongRepository.getBoughtSongArtist(artistId);
	}
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody ArtistSignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getEmail(),
							 signUpRequest.getUsername(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
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
		Artist artist = new Artist("paypal@gmail.com");
		artist.setActive(true);
		artist.setAvatar("assets/images/placeholder.png");
		artist.setCoverImageId("assets/images/placeholder.png");
		artist.setArtistName(user.getEmail());
		user.setRoles(roles);
		userRepository.save(user);
		artist.setUserId(user.getId());
		artistRepository.save(artist);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> EditProfile( @RequestParam long userId, @RequestParam int artistId,
			@RequestParam MultipartFile avatar,
			@RequestParam MultipartFile coverImage, @RequestParam String artistName,
			@RequestParam String paypalAccount) {
		
		User user = userRepository.findById(userId)
				.orElseThrow(()-> new RuntimeException("user not found"));
		Artist artist = artistRepository.findById(artistId)
				.orElseThrow(()-> new RuntimeException("artist not found"));
		
		user.setEmail(artistName);
		
		String urlAvatar= amazonS3ClientService.uploadImageToS3Bucket(avatar, true);
		String urlCoverImage= amazonS3ClientService.uploadImageToS3Bucket(coverImage, true);
		artist.setAvatar(urlAvatar);
		artist.setCoverImageId(urlCoverImage);
		artist.setPaypalAccount(paypalAccount);
		
		imageFileRepository.save(new ImageFile(urlAvatar,0,artist.getId()));
		imageFileRepository.save(new ImageFile(urlCoverImage,0,artist.getId()));
		
		user =userRepository.save(user);
		artist =artistRepository.save(artist);
		List<String> roles = user.getRoles().stream().map(item->item.getName().toString()).collect(Collectors.toList());
		return ResponseEntity.ok(new ArtistJwtResponse("", user.getId(), user.getUsername(),
				user.getEmail(), roles, 0, artist.getPaypalAccount(),artist.getId(),
				artist.getAvatar(), artist.getCoverImageId()));
		
	}

}
