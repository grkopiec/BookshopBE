package pl.bookshop.mvc.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.components.TokenUtils;
import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.jpa.User;
import pl.bookshop.mvc.objects.AuthenticationRequest;
import pl.bookshop.mvc.objects.AuthenticationResponse;
import pl.bookshop.mvc.objects.UserData;
import pl.bookshop.services.UsersService;
import pl.bookshop.utils.Constants;

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
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserData userData) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userData.getUser().getUsername(), userData.getUser().getPassword());
    	
    	userUtils.makeNormalUser(userData);
		if (usersService.isExist(userData) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
    	usersService.create(userData);
    	
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = (User) userDetailsService.loadUserByUsername(userData.getUser().getUsername());
        String token = tokenUtils.generateToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, user.getId(), user.getUsername(), user.getAuthorities());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authenticationRequest)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        );
        User user = (User) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        if (user == null) {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Authentication authentication;
        try {
        	authentication = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException exception)	 {
        	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        String token = tokenUtils.generateToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        AuthenticationResponse authenticationResponse = new AuthenticationResponse(token, user.getId(), user.getUsername(), user.getAuthorities());
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(HttpServletRequest request) {
        String token = request.getHeader(Constants.AUTHORIZATION_HEADER);
        token = token.substring(Constants.TOKEN_HEADER_STARTS_WITH.length());
        String username = tokenUtils.getUsernameFromToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        
        if (tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordReset())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            AuthenticationResponse response = new AuthenticationResponse(refreshedToken, user.getId(), user.getUsername(), user.getAuthorities());
            return new ResponseEntity<AuthenticationResponse>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
