package pl.bookshop.domains;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import pl.bookshop.converters.AuthoritiesDeserializer;
import pl.bookshop.converters.AuthoritiesSerializer;
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
	@JsonSerialize(converter = AuthoritiesSerializer.class)
	@JsonDeserialize(converter = AuthoritiesDeserializer.class)
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", lastPasswordReset="
				+ lastPasswordReset + ", authorities=" + authorities + ", accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password, lastPasswordReset, authorities, accountNonExpired, accountNonLocked,
				credentialsNonExpired, enabled);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		User other = (User) obj;
		return Objects.equals(this.username, other.username) && Objects.equals(this.password, other.password)
				&& Objects.equals(this.lastPasswordReset, other.lastPasswordReset) && Objects.equals(this.authorities, other.authorities)
				&& Objects.equals(this.accountNonExpired, other.accountNonExpired)
				&& Objects.equals(this.accountNonLocked, other.accountNonLocked)
				&& Objects.equals(this.credentialsNonExpired, other.credentialsNonExpired) && Objects.equals(this.enabled, other.enabled);
	}
}
