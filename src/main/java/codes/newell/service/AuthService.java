package codes.newell.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import codes.newell.dto.RegisterRequest;
import codes.newell.model.User;
import codes.newell.model.VerificationToken;
import codes.newell.repository.UserRepository;
import codes.newell.repository.VerificationTokenRepository;

@Service
public class AuthService {

	private final PasswordEncoder encoder;	
	private final UserRepository ur;
	private final VerificationTokenRepository vtr;

	@Autowired
	public AuthService(PasswordEncoder encoder, UserRepository ur, VerificationTokenRepository vtr) {
		this.encoder = encoder;
		this.ur = ur;
		this.vtr = vtr;
	}

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setPassword(registerRequest.getPassword());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(encoder.encode(registerRequest.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		ur.save(user);
		generateVerificationToken(user);
	}

	@Transactional
	private void generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verifier = new VerificationToken();
		verifier.setToken(token);
		verifier.setUser(user);
		vtr.save(verifier);
	}
}
