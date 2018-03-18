package pl.bookshop.mvc.objects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class AuthenticationResponse {
	private String token;
	private Long userId;
	private String username;
	private List<String> roles;
	
	public AuthenticationResponse() {}

	public AuthenticationResponse(String token, Long userId, String username, Collection<? extends GrantedAuthority> roles) {
		this.token = token;
		this.userId = userId;
		this.username = username;
		Set<String> authorities = AuthorityUtils.authorityListToSet(roles);
		this.roles = new ArrayList<>(authorities);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
	
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		Set<String> authorities = AuthorityUtils.authorityListToSet(roles);
		this.roles = new ArrayList<>(authorities);
		
	}
}
