package codes.newell.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codes.newell.dto.AuthenticationResponse;
import codes.newell.dto.LoginRequest;
import codes.newell.dto.RefreshTokenRequest;
import codes.newell.dto.RegisterRequest;
import codes.newell.service.AuthService;
import codes.newell.service.RefreshTokenService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService as;
	private final RefreshTokenService rts;

	@PostMapping("signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
		as.signup(registerRequest);
		return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
	}

	@GetMapping("accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token) {
		as.verifyAccount(token);
		return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
	}

	@PostMapping("login")
	public AuthenticationResponse login(@RequestBody LoginRequest request) {
		return as.login(request);
	}

	@PostMapping("refresh/token")
	public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
		return as.refreshToken(request);
	}

	@PostMapping("logout")
	public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest request) {
		rts.deleteRefreshToken(request.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token deleted successfully");
	}
}
