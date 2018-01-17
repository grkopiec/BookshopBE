package pl.bookshop.domains;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.bookshop.hibernate.CollectionStringConverter;

@Entity	
@Table(name = "users")
public class User implements UserDetails {
	private static final long serialVersionUID = -7633438667478513077L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "usersSequence")
	@SequenceGenerator(name = "usersSequence", sequenceName = "users_sequence")
	private Long id;
	private String username;
	private String password;
	@Column(name = "last_password_reset")
	private Date lastPasswordReset;
	@Convert(converter = CollectionStringConverter.class)
	private Collection<? extends GrantedAuthority> authorities;
	@Column(name = "account_non_expired")
	private Boolean accountNonExpired = true;
	@Column(name = "account_non_locked")
	private Boolean accountNonLocked = true;
	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired = true;
	private Boolean enabled = true;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastPasswordReset() {
		return lastPasswordReset;
	}

	public void setLastPasswordReset(Date lastPasswordReset) {
		this.lastPasswordReset = lastPasswordReset;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
