package codes.newell.service;

import static java.util.Collections.singletonList;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codes.newell.model.User;
import codes.newell.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository ur;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		Optional<User> o = ur.findByUsername(username);
		User user = o.orElseThrow(() -> new UsernameNotFoundException("No user found with username" + username));
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(),
				user.isEnabled(),
				true,
				true,
				true,
				getAuthorities("USER")
				);
	}

	private Collection<? extends GrantedAuthority> getAuthorities(String role) {
		return singletonList(new SimpleGrantedAuthority(role));
	}

}
