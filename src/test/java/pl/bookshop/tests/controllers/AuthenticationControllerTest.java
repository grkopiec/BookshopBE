package pl.bookshop.tests.controllers;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.bookshop.components.TokenUtils;
import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.User;
import pl.bookshop.mvc.controllers.AuthenticationController;
import pl.bookshop.mvc.controllers.objects.AuthenticationRequest;
import pl.bookshop.mvc.controllers.objects.AuthenticationResponse;
import pl.bookshop.services.UsersService;
import pl.bookshop.tests.utils.TestUtils;

public class AuthenticationControllerTest {
	private MockMvc mockMvc;
	
	@Captor
	private ArgumentCaptor<UsernamePasswordAuthenticationToken> UsernamePasswordAuthenticationTokenCaptor;
	@Captor
	private ArgumentCaptor<Authentication> authenticationCaptor;
	
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenUtils tokenUtils;
    @Mock
    private UserUtils userUtils;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private UsersService usersService;
    
    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(authenticationController)
				.build();
    }
    
    
    @Test
    public void test_register_success() throws Exception {
    	AuthenticationRequest authenticationRequest = getAuthenticationRequest();
    	User user = getUser();
    	UserDetails userDetails = getUserDetails();
    	String token = getToken();
    	AuthenticationResponse authenticationResponse = getAuthenticationResponse(userDetails, token);
    	
    	Mockito.when(userUtils.createNormalUser(authenticationRequest)).thenReturn(user);
    	Mockito.doNothing().when(usersService).create(user);
    	Mockito.when(authenticationManager.authenticate(UsernamePasswordAuthenticationTokenCaptor.capture()))
    			.thenReturn(authenticationCaptor.capture());
    	Mockito.when(userDetailsService.loadUserByUsername(authenticationRequest.getUsername())).thenReturn(userDetails);
    	Mockito.when(tokenUtils.generateToken(userDetails)).thenReturn(token);
    	
		mockMvc.perform(MockMvcRequestBuilders
						.post("/auth/register")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(authenticationRequest)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.token", Matchers.is(authenticationResponse.getToken())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(authenticationResponse.getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.roles", Matchers.is(authenticationResponse.getRoles())));
		
		Mockito.verify(userUtils, Mockito.times(1)).createNormalUser(authenticationRequest);
		Mockito.verify(usersService, Mockito.times(1)).create(user);
		Mockito.verify(authenticationManager, Mockito.times(1)).authenticate(UsernamePasswordAuthenticationTokenCaptor.capture());
		Mockito.verify(userDetailsService, Mockito.times(1)).loadUserByUsername(authenticationRequest.getUsername());
		Mockito.verify(tokenUtils, Mockito.times(1)).generateToken(userDetails);
		Mockito.verifyNoMoreInteractions(userUtils);
		Mockito.verifyNoMoreInteractions(usersService);
		Mockito.verifyNoMoreInteractions(authenticationManager);
		Mockito.verifyNoMoreInteractions(userDetailsService);
		Mockito.verifyNoMoreInteractions(tokenUtils);
    }
    
    private AuthenticationRequest getAuthenticationRequest() {
    	AuthenticationRequest authenticationRequest = new AuthenticationRequest();
    	authenticationRequest.setUsername(RandomStringUtils.randomAlphabetic(10));
    	authenticationRequest.setPassword(RandomStringUtils.randomAlphabetic(15));
    	return authenticationRequest;
    }
    
	private User getUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		user.setAuthorities(authorities);
		return user;
	}
	
	private UserDetails getUserDetails() {
		User user = new User();
		user.setId(2L);
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		user.setLastPasswordReset(null);
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		user.setAuthorities(authorities);
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		UserDetails userDetails = user;
		return userDetails;
	}
	
	private String getToken() {
		String token = RandomStringUtils.randomAscii(40);
		return token;
	}
	
	private AuthenticationResponse getAuthenticationResponse(UserDetails userDetails, String token) {
		AuthenticationResponse authenticationResponse = new AuthenticationResponse();
		authenticationResponse.setToken(token);
		authenticationResponse.setUsername(userDetails.getUsername());
		authenticationResponse.setRoles(userDetails.getAuthorities());
		return authenticationResponse;
	}
}
