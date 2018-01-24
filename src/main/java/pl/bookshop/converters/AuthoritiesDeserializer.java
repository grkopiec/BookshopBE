package pl.bookshop.converters;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.fasterxml.jackson.databind.util.StdConverter;

public class AuthoritiesDeserializer extends StdConverter<List<String>, List<GrantedAuthority>> {
	@Override
	public List<GrantedAuthority> convert(List<String> value) {
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(value.toArray(new String[value.size()]));
		return authorities;
	}
}
