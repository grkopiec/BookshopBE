package pl.bookshop.configuration.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.support.WebApplicationContextUtils;

import pl.bookshop.components.TokenUtils;
import pl.bookshop.domains.User;
import pl.bookshop.services.UsersService;
import pl.bookshop.utils.StringUtils;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	private final String HEADER_STARTS_WITH = "Bearer ";
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(StringUtils.AUTHORIZATION_HEADER);
        
        if (authToken != null && authToken.startsWith(HEADER_STARTS_WITH)) {
            TokenUtils tokenUtils = WebApplicationContextUtils
                    .getRequiredWebApplicationContext(this.getServletContext())
                    .getBean(TokenUtils.class);
	        String username = tokenUtils.getUsernameFromToken(authToken);
	
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        	UsersService usersService = WebApplicationContextUtils
	                    .getRequiredWebApplicationContext(this.getServletContext())
	                    .getBean(UsersService.class);
	            User user = usersService.findByUsername(username);
	            
	            if (tokenUtils.validateToken(authToken, user)) {
	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        }
        }
        chain.doFilter(request, response);
    }
}
