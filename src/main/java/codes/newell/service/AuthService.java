package codes.newell.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codes.newell.dto.AuthenticationResponse;
import codes.newell.dto.LoginRequest;
import codes.newell.dto.RegisterRequest;
import codes.newell.exceptions.SpringRedditException;
import codes.newell.model.NotificationEmail;
import codes.newell.model.User;
import codes.newell.model.VerificationToken;
import codes.newell.repository.UserRepository;
import codes.newell.repository.VerificationTokenRepository;
import codes.newell.security.JwtProvider;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	private final PasswordEncoder encoder;
	private final UserRepository ur;
	private final VerificationTokenRepository vtr;
	private final MailService ms;
	private final AuthenticationManager am;
	private final JwtProvider jp;

	@Transactional
	public void signup(RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
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

	@Transactional(readOnly = true)
	public User getCurrentUser() {
		org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
				getContext().getAuthentication().getPrincipal();
		return ur.findByUsername(principal.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verifier = new VerificationToken();
		verifier.setToken(token);
		verifier.setUser(user);
		vtr.save(verifier);
		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> o = vtr.findByToken(token);
		VerificationToken vt = o.orElseThrow(() -> new SpringRedditException("Invalid Token"));
		fetchUserAndEnable(vt);
	}

	private void fetchUserAndEnable(VerificationToken vt) {
		String username = vt.getUser().getUsername();
		Optional<User> o = ur.findByUsername(username);
		User user = o.orElseThrow(() -> new SpringRedditException("User not found: " + username));
		user.setEnabled(true);
		ur.save(user);
	}

	public AuthenticationResponse login(LoginRequest request) {
		Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String token = jp.generateToken(auth);
		return new AuthenticationResponse(token, request.getUsername());
	}
}
