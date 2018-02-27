package pl.bookshop.components;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.bookshop.domains.User;
import pl.bookshop.domainsmongo.UserDetails;
import pl.bookshop.mvc.controllers.objects.AuthenticationRequest;
import pl.bookshop.mvc.controllers.objects.UserData;

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
	
	public UserData createNormalUser(AuthenticationRequest authenticationRequest) {
		User user = new User();
		user.setUsername(authenticationRequest.getUsername());
		user.setPassword(authenticationRequest.getPassword());
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		user.setAuthorities(authorities);
		
		UserDetails userDetails = new UserDetails();
		
		UserData userData = new UserData();
		userData.setUser(user);
		userData.setUserDetails(userDetails);
		return userData;
	}
	
	public String encodePassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}
}
