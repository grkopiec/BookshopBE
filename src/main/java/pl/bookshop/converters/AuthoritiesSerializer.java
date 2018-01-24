package pl.bookshop.converters;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.util.StdConverter;

public class AuthoritiesSerializer extends StdConverter<List<GrantedAuthority>, List<String>> {
	@Override
	public List<String> convert(List<GrantedAuthority> value) {
		List<String> authorities = value.stream().map(n -> n.getAuthority()).collect(Collectors.toList());
		return authorities;
	}
}
