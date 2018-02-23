package pl.bookshop.mvc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.components.TokenUtils;
import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.User;
import pl.bookshop.mvc.controllers.objects.AuthenticationRequest;
import pl.bookshop.mvc.controllers.objects.AuthenticationResponse;
import pl.bookshop.mvc.controllers.objects.UserData;
import pl.bookshop.services.UsersService;
import pl.bookshop.utils.StringUtils;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private UserUtils userUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UsersService usersService;
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest authenticationRequest) {
    	UserData userData = userUtils.createNormalUser(authenticationRequest);
		if (usersService.isExist(userData) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    	usersService.create(userData);
    	
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = tokenUtils.generateToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (userDetails == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Authentication authentication;
        try {
        	authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException exception)	 {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String token = tokenUtils.generateToken(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, userDetails.getUsername(), userDetails.getAuthorities());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(HttpServletRequest request) {
        String token = request.getHeader(StringUtils.AUTHORIZATION_HEADER);
        token = token.substring(StringUtils.TOKEN_HEADER_STARTS_WITH.length());
        String username = tokenUtils.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        
        if (tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            AuthenticationResponse response = new AuthenticationResponse(refreshedToken, user.getUsername(), user.getAuthorities());
            return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
