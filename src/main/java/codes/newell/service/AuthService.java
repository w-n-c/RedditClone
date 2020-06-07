package codes.newell.service;

import java.time.Instant;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import codes.newell.dto.RegisterRequest;
import codes.newell.model.User;
import codes.newell.repository.UserRepository;

@Service
public class AuthService {
	
	private final PasswordEncoder passwordEncoder;	
	private final UserRepository userRepository;
	
	@Autowired
	public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

	
	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setPassword(registerRequest.getPassword());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);
	}
}
