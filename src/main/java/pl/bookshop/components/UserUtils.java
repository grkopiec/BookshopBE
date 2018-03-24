package pl.bookshop.components;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import pl.bookshop.domains.jpa.User;
import pl.bookshop.mvc.objects.UserData;

@Component
public class UserUtils {
	public void makeAdminUser(UserData userData) {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		userData.getUser().setAuthorities(authorities);
	}
	
	public void makeNormalUser(UserData userData) {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		userData.getUser().setAuthorities(authorities);
	}
	
	public String encodePassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}
	
	public boolean isAdmin(User user) {
		Collection<? extends GrantedAuthority> grantedAuthorities = user.getAuthorities();
		Optional<? extends GrantedAuthority> grantedAuthority = grantedAuthorities.stream()
				.filter(p -> p.getAuthority().equals("ROLE_ADMIN"))
				.findFirst();
		return grantedAuthority.isPresent();
	}
}
