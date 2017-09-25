package pl.bookshop.hibernate;

import java.util.Collection;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

public class CollectionStringConverter implements AttributeConverter<Collection<? extends GrantedAuthority>, String> {
	@Override
	public String convertToDatabaseColumn(Collection<? extends GrantedAuthority> attribute) {
		StringBuilder authorities = new StringBuilder("");
		attribute.forEach(c -> {
			authorities.append(c.getAuthority() + ",");
		});
		return StringUtils.removeEnd(authorities.toString(), ",");
	}

	@Override
	public Collection<? extends GrantedAuthority> convertToEntityAttribute(String authorityString) {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
	}
}
