package com.tlcn.thebeats.security.jwt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.management.relation.RelationNotFoundException;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.tlcn.thebeats.models.User;
import com.tlcn.thebeats.repository.UserRepository;
import com.tlcn.thebeats.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Value("${bezkoder.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	PasswordEncoder encoder;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
	
	public Date getTimeExpireFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
	

	public ResponseEntity<Map<String, Object>> verifyCode(String token) throws Exception {
		try {
			if (userRepository.findByCode(token) != null) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	public ResponseEntity<Map<String, Object>> MailReset(JavaMailSender emailSender, String email) {
		SimpleMailMessage message = new SimpleMailMessage();

		String random = getRandomNumberInts(1, 100);
		try {
			User user = userRepository.findByUsername(email).get();
			if (user != null) {
				
				
					message.setTo(email);
					message.setSubject("Test Simple Email");
					message.setText(" Your verify Token: " + "\n token=" + random); // Send
					emailSender.send(message);
					user.setCode(random);
					userRepository.save(user);
					return new ResponseEntity<>(HttpStatus.OK);

				
			} else {

				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	public String getMD5(String data) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");

		messageDigest.update(data.getBytes());
		byte[] digest = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
		return myHash.toString();
	}
	public String getRandomNumberInts(int min, int max) {
		Random random = new Random();
		return Long.toString(random.ints(min, (max + 1)).findFirst().getAsInt());
	}
	
	
	public boolean verifyPassword(String password, String username) throws RelationNotFoundException {

		
			String encryptPass = encoder.encode(password);
			User user =userRepository.findByUsername( username).get();
				
			return encoder.matches(password,user.getPassword());
		} 

	public ResponseEntity<?> changePassword(String oldpass,String newpass, String token) throws RelationNotFoundException {
		try {
			String username = getUserNameFromJwtToken(token.substring(7, token.length()));
			if(verifyPassword(oldpass, username)==true)
			{
				String encryptPass = encoder.encode(newpass);
				User account = userRepository.findByUsername(username).get();
				account.setPassword(encryptPass);
				userRepository.save(account);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else return new ResponseEntity<>(HttpStatus.CHECKPOINT);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
