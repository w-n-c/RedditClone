package codes.newell.service;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codes.newell.exceptions.SpringRedditException;
import codes.newell.model.RefreshToken;
import codes.newell.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

	private final RefreshTokenRepository tr;

	public RefreshToken generateRefreshToken() {
		RefreshToken token = new RefreshToken();
		token.setToken(UUID.randomUUID().toString());
		token.setCreatedDate(Instant.now());
		return tr.save(token);
	}

	public void validateRefreshToken(String token) {
		tr.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid refresh token"));
	}

	public void deleteRefreshToken(String token) {
		tr.deleteByToken(token);
	}
}
