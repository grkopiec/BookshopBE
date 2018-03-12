package pl.bookshop.tests.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.controllers.UsersController;
import pl.bookshop.mvc.objects.UserData;
import pl.bookshop.services.UsersService;
import pl.bookshop.tests.utils.TestUtils;

public class UsersControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private UsersService usersService;
	@Mock
	private UserUtils userUtils;
	
	@InjectMocks
	private UsersController usersController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(usersController)
				.build();
	}
	
	@Test
	public void test_findAll_success() throws Exception {
		List<UserData> usersData = Arrays.asList(getUserData0(), getUserData1());
		List<String> authorities0 = usersData.get(0).getUser().getAuthorities().stream().map(n -> n.getAuthority()).collect(Collectors.toList());
		List<String> authorities1 = usersData.get(1).getUser().getAuthorities().stream().map(n -> n.getAuthority()).collect(Collectors.toList());
		
		Mockito.when(usersService.findAll()).thenReturn(usersData);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.id", Matchers.is(usersData.get(0).getUser().getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.username", Matchers.is(usersData.get(0).getUser().getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.password", Matchers.is(usersData.get(0).getUser().getPassword())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.lastPasswordReset", Matchers.is(usersData.get(0).getUser().getLastPasswordReset())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.authorities", Matchers.is(authorities0)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.accountNonExpired", Matchers.is(usersData.get(0).getUser().isAccountNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.accountNonLocked", Matchers.is(usersData.get(0).getUser().isAccountNonLocked())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.credentialsNonExpired", Matchers.is(usersData.get(0).getUser().isCredentialsNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].user.enabled", Matchers.is(usersData.get(0).getUser().isEnabled())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].userDetails.id", Matchers.is(usersData.get(0).getUserDetails().getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].userDetails.userId", Matchers.is(usersData.get(0).getUserDetails().getUserId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].userDetails.name", Matchers.is(usersData.get(0).getUserDetails().getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].userDetails.surname", Matchers.is(usersData.get(0).getUserDetails().getSurname())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.id", Matchers.is(usersData.get(1).getUser().getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.username", Matchers.is(usersData.get(1).getUser().getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.password", Matchers.is(usersData.get(1).getUser().getPassword())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.lastPasswordReset", Matchers.is(usersData.get(1).getUser().getLastPasswordReset())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.authorities", Matchers.is(authorities1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.accountNonExpired", Matchers.is(usersData.get(1).getUser().isAccountNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.accountNonLocked", Matchers.is(usersData.get(1).getUser().isAccountNonLocked())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.credentialsNonExpired", Matchers.is(usersData.get(1).getUser().isCredentialsNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].user.enabled", Matchers.is(usersData.get(1).getUser().isEnabled())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].userDetails.id", Matchers.is(usersData.get(1).getUserDetails().getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].userDetails.userId", Matchers.is(usersData.get(1).getUserDetails().getUserId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].userDetails.name", Matchers.is(usersData.get(1).getUserDetails().getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].userDetails.surname", Matchers.is(usersData.get(1).getUserDetails().getSurname())));
		
		Mockito.verify(usersService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	@Test
	public void test_findOne_success() throws Exception {
		UserDetails userDetails = getUserDetails0();
		
		Mockito.when(usersService.findOne(userDetails.getUserId())).thenReturn(userDetails);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userDetails.getUserId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(userDetails.getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(userDetails.getUserId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(userDetails.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(userDetails.getSurname())));
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(userDetails.getUserId());
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 */
	@Test
	public void test_findOne_fail() throws Exception {
		UserDetails userDetails = getUserDetails0();
		
		Mockito.when(usersService.findOne(userDetails.getUserId())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", userDetails.getUserId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(userDetails.getUserId());
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	@Test
	public void test_create_success() throws Exception {
		UserData userData = getUserData0();

		Mockito.when(usersService.isExist(userData)).thenReturn(false);
		Mockito.doNothing().when(userUtils).makeAdminUser(userData);
		Mockito.doNothing().when(usersService).create(userData);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/users/admin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(userData)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(userData);
		Mockito.verify(userUtils, Mockito.times(1)).makeAdminUser(userData);
		Mockito.verify(usersService, Mockito.times(1)).create(userData);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	
	/**
	 * Should occur 409 code error, passed user already exists
	 */
	@Test
	public void test_create_fail() throws Exception {
		UserData userData = getUserData0();
		
		Mockito.when(usersService.isExist(userData)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/users/admin")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(userData)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(userData);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Scenario for this test is like this, first we are sending to PUT endpoint id exist object in database that will be replaced
	 * and new object, next in service we are finding object in database to replace. In object we passed to endpoint we are changing
	 * id on this that already contains exists object in database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_success() throws Exception {
		UserData previousUserData = getUserData0();
		UserDetails afterUpdateUserDetails = getUserDetails0();

		Mockito.when(usersService.isExist(previousUserData)).thenReturn(false);
		Mockito.when(usersService.update(previousUserData.getUser().getId(), previousUserData)).thenReturn(afterUpdateUserDetails);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/users/{id}", previousUserData.getUser().getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(previousUserData)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(afterUpdateUserDetails.getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId", Matchers.is(afterUpdateUserDetails.getUserId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(afterUpdateUserDetails.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname", Matchers.is(afterUpdateUserDetails.getSurname())));
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(previousUserData);
		Mockito.verify(usersService, Mockito.times(1)).update(previousUserData.getUser().getId(), previousUserData);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 * In this scenario object will not be change because do not exist requested id
	 */
	@Test
	public void test_update_fail() throws Exception {
		UserData previousUserData = getUserData0();
		
		Mockito.when(usersService.isExist(previousUserData)).thenReturn(false);
		Mockito.when(usersService.update(previousUserData.getUser().getId(), previousUserData)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/users/{id}", previousUserData.getUser().getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(previousUserData)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(previousUserData);
		Mockito.verify(usersService, Mockito.times(1)).update(previousUserData.getUser().getId(), previousUserData);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 409 code error, user name already exists
	 * In this scenario object will not be change because user name already exists
	 */
	@Test
	public void test_update_nameColisionFail() throws Exception {
		UserData previousUserData = getUserData0();
		
		Mockito.when(usersService.isExist(previousUserData)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/users/{id}", previousUserData.getUser().getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(previousUserData)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(previousUserData);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	@Test
	public void test_delete_success() throws Exception {
		UserData userData = getUserData0();
		UserDetails userDetails = getUserDetails0();
		
		Mockito.when(usersService.findOne(userData.getUser().getId())).thenReturn(userDetails);
		Mockito.doNothing().when(usersService).delete(userData.getUser().getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/users/{id}", userData.getUser().getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(userData.getUser().getId());
		Mockito.verify(usersService, Mockito.times(1)).delete(userData.getUser().getId());
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 409 code error, user do not exist
	 */
	@Test
	public void test_delete_fail() throws Exception {
		UserData userData = getUserData0();
		
		Mockito.when(usersService.findOne(userData.getUser().getId())).thenReturn(null);
		Mockito.doNothing().when(usersService).delete(userData.getUser().getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/users/{id}", userData.getUser().getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(userData.getUser().getId());
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	private User getUser0() {
		User user0 = new User();
		user0.setId(RandomUtils.nextLong(0, 100));
		user0.setUsername(RandomStringUtils.randomAlphabetic(10));
		user0.setPassword(RandomStringUtils.randomAlphabetic(15));
		user0.setLastPasswordReset(null);
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		user0.setAuthorities(authorities);
		user0.setAccountNonExpired(true);
		user0.setAccountNonLocked(true);
		user0.setCredentialsNonExpired(true);
		user0.setEnabled(true);
		return user0;
	}
	
	private UserDetails getUserDetails0() {
		UserDetails userDetails0 = new UserDetails();
		userDetails0.setId(RandomStringUtils.randomAlphabetic(20));
		userDetails0.setUserId(getUser0().getId());
		userDetails0.setName(RandomStringUtils.random(30));
		userDetails0.setSurname(RandomStringUtils.random(50));
		return userDetails0;
	}
	
	private UserData getUserData0() {
		UserData userData0 = new UserData();
		userData0.setUser(getUser0());
		userData0.setUserDetails(getUserDetails0());
		return userData0;
	}
	
	private User getUser1() {
		User user1 = new User();
		user1.setId(RandomUtils.nextLong(0, 100));
		user1.setUsername(RandomStringUtils.randomAlphabetic(10));
		user1.setPassword(RandomStringUtils.randomAlphabetic(15));
		user1.setLastPasswordReset(null);
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		user1.setAuthorities(authorities);
		user1.setAccountNonExpired(true);
		user1.setAccountNonLocked(true);
		user1.setCredentialsNonExpired(true);
		user1.setEnabled(true);
		return user1;
	}
	
	private UserDetails getUserDetails1() {
		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(RandomStringUtils.randomAlphabetic(20));
		userDetails1.setUserId(getUser1().getId());
		userDetails1.setName(RandomStringUtils.random(30));
		userDetails1.setSurname(RandomStringUtils.random(50));
		return userDetails1;
	}
	
	private UserData getUserData1() {
		UserData userData1 = new UserData();
		userData1.setUser(getUser1());
		userData1.setUserDetails(getUserDetails1());
		return userData1;
	}
}
