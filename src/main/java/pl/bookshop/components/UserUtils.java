package pl.bookshop.components;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.bookshop.domains.User;
import pl.bookshop.mvc.controllers.objects.AuthenticationRequest;

@Component
public class UserUtils {
	public User createAdminUser(AuthenticationRequest authenticationRequest) {
		User adminUser = new User();
		adminUser.setUsername(authenticationRequest.getUsername());
		adminUser.setPassword(authenticationRequest.getPassword());
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		adminUser.setAuthorities(authorities);
		return adminUser;
	}
	
	public User createNormalUser(AuthenticationRequest authenticationRequest) {
		User normalUser = new User();
		normalUser.setUsername(authenticationRequest.getUsername());
		normalUser.setPassword(authenticationRequest.getPassword());
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		normalUser.setAuthorities(authorities);
		return normalUser;
	}
	
	public String encodePassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}
}
