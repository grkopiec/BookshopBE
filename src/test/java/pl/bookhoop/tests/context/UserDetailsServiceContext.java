package pl.bookhoop.tests.context;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import pl.bookshop.domains.jpa.User;

public class UserDetailsServiceContext {
    @Bean
    public UserDetailsService userDetailsService() {
    	List<GrantedAuthority> adminAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword("admin");
        adminUser.setAuthorities(adminAuthorities);

    	List<GrantedAuthority> userAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
        User normalUser = new User();
        normalUser.setUsername("user");
        normalUser.setPassword("admin");
        normalUser.setAuthorities(userAuthorities);

        List<UserDetails> users = Arrays.asList((UserDetails) adminUser, (UserDetails) normalUser);
        UserDetailsService userDetailsService = new InMemoryUserDetailsManager(users);
        return userDetailsService;
    }
}
