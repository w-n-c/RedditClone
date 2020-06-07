package codes.newell.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import codes.newell.dto.RegisterRequest;
import codes.newell.model.NotificationEmail;
import codes.newell.model.User;
import codes.newell.model.VerificationToken;
import codes.newell.repository.UserRepository;
import codes.newell.repository.VerificationTokenRepository;

@Service
public class AuthService {

	private final PasswordEncoder encoder;
	private final UserRepository ur;
	private final VerificationTokenRepository vtr;
	private final MailService ms;

	@Autowired
	public AuthService(PasswordEncoder encoder, UserRepository ur, VerificationTokenRepository vtr, MailService ms) {
		this.encoder = encoder;
		this.ur = ur;
		this.vtr = vtr;
		this.ms = ms;
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
		String token = generateVerificationToken(user);
		ms.sendMail(new NotificationEmail(
			"Please Activate your Account", // subject
			user.getEmail(), // recipient
			"Thank you for signing up for Spring Reddit!" + // body
			"Please click the link below to activate your account: \n" +
			"http://localhost:8080/api/auth/accountVerification/" +
			token
		));
	}

	@Transactional
	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verifier = new VerificationToken();
		verifier.setToken(token);
		verifier.setUser(user);
		vtr.save(verifier);
		return token;
	}
}
