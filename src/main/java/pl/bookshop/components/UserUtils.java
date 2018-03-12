package pl.bookshop.components;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.objects.AuthenticationRequest;
import pl.bookshop.mvc.objects.UserData;

@Component
public class UserUtils {
	public void makeAdminUser(UserData userData) {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		userData.getUser().setAuthorities(authorities);
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
