package pl.bookshop.tests.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
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

import pl.bookshop.domains.User;
import pl.bookshop.mvc.controllers.UsersController;
import pl.bookshop.services.UsersService;
import pl.bookshop.tests.utils.TestUtils;

public class UsersControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private UsersService usersService;
	
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
		List<User> users = Arrays.asList(getUser1(), getUser2());
		List<String> authorities1 = users.get(0).getAuthorities().stream().map(n -> n.getAuthority()).collect(Collectors.toList());
		List<String> authorities2 = users.get(1).getAuthorities().stream().map(n -> n.getAuthority()).collect(Collectors.toList());
		
		Mockito.when(usersService.findAll()).thenReturn(users);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(users.get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is(users.get(0).getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is(users.get(0).getPassword())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].lastPasswordReset", Matchers.is(users.get(0).getLastPasswordReset())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].authorities", Matchers.is(authorities1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].accountNonExpired", Matchers.is(users.get(0).isAccountNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].accountNonLocked", Matchers.is(users.get(0).isAccountNonLocked())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].credentialsNonExpired", Matchers.is(users.get(0).isCredentialsNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].enabled", Matchers.is(users.get(0).isEnabled())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(users.get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].username", Matchers.is(users.get(1).getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].password", Matchers.is(users.get(1).getPassword())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].lastPasswordReset", Matchers.is(users.get(1).getLastPasswordReset())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].authorities", Matchers.is(authorities2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].accountNonExpired", Matchers.is(users.get(1).isAccountNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].accountNonLocked", Matchers.is(users.get(1).isAccountNonLocked())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].credentialsNonExpired", Matchers.is(users.get(1).isCredentialsNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].enabled", Matchers.is(users.get(1).isEnabled())));
		
		Mockito.verify(usersService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	@Test
	public void test_findOne_success() throws Exception {
		User user1 = getUser1();
		List<String> authorities = user1.getAuthorities().stream().map(n -> n.getAuthority()).collect(Collectors.toList());
		
		Mockito.when(usersService.findOne(user1.getId())).thenReturn(user1);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", user1.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(user1.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(user1.getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is(user1.getPassword())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastPasswordReset", Matchers.is(user1.getLastPasswordReset())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorities", Matchers.is(authorities)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountNonExpired", Matchers.is(user1.isAccountNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountNonLocked", Matchers.is(user1.isAccountNonLocked())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.credentialsNonExpired", Matchers.is(user1.isCredentialsNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.enabled", Matchers.is(user1.isEnabled())));
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 */
	@Test
	public void test_findOne_fail() throws Exception {
		User user1 = getUser1();
		
		Mockito.when(usersService.findOne(1L)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", user1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	@Test
	public void test_create_success() throws Exception {
		User user1 = getUser1();

		Mockito.when(usersService.isExist(user1)).thenReturn(false);
		Mockito.doNothing().when(usersService).create(user1);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(user1)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(user1);
		Mockito.verify(usersService, Mockito.times(1)).create(user1);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	
	/**
	 * Should occur 409 code error, passed user already exists
	 */
	@Test
	public void test_create_fail() throws Exception {
		User user1 = getUser1();
		
		Mockito.when(usersService.isExist(user1)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(user1)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(user1);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Scenario for this test is like this, first we are sending to PUT endpoint id exist object in database that will be replaced
	 * and new object, next in service we are finding object in database to replace. In object we passed to endpoint we are changing
	 * id on this that already contains exists object in database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_success() throws Exception {
		User previousUser = getUser1();
		User beforeUpdateUser = getUser2();
		User afterUpdateUser = getUser2();
		List<String> authorities = afterUpdateUser.getAuthorities().stream().map(n -> n.getAuthority()).collect(Collectors.toList());

		Mockito.when(usersService.isExist(beforeUpdateUser)).thenReturn(false);
		Mockito.when(usersService.update(previousUser.getId(), beforeUpdateUser)).thenReturn(afterUpdateUser);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/users/{id}", previousUser.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(beforeUpdateUser)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(afterUpdateUser.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is(afterUpdateUser.getUsername())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is(afterUpdateUser.getPassword())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.lastPasswordReset", Matchers.is(afterUpdateUser.getLastPasswordReset())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.authorities", Matchers.is(authorities)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountNonExpired", Matchers.is(afterUpdateUser.isAccountNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.accountNonLocked", Matchers.is(afterUpdateUser.isAccountNonLocked())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.credentialsNonExpired", Matchers.is(afterUpdateUser.isCredentialsNonExpired())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.enabled", Matchers.is(afterUpdateUser.isEnabled())));
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(beforeUpdateUser);
		Mockito.verify(usersService, Mockito.times(1)).update(previousUser.getId(), beforeUpdateUser);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 * In this scenario object will not be change because do not exist requested id
	 */
	@Test
	public void test_update_fail() throws Exception {
		User previousUser = getUser1();
		User beforeUpdateUser = getUser2();
		
		Mockito.when(usersService.isExist(beforeUpdateUser)).thenReturn(false);
		Mockito.when(usersService.update(previousUser.getId(), beforeUpdateUser)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/users/{id}", previousUser.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(beforeUpdateUser)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(beforeUpdateUser);
		Mockito.verify(usersService, Mockito.times(1)).update(previousUser.getId(), beforeUpdateUser);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 409 code error, user name already exists
	 * In this scenario object will not be change because user name already exists
	 */
	@Test
	public void test_update_nameColisionFail() throws Exception {
		User previousUser = getUser1();
		User beforeUpdateUser = getUser2();
		
		Mockito.when(usersService.isExist(beforeUpdateUser)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/users/{id}", previousUser.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(beforeUpdateUser)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(usersService, Mockito.times(1)).isExist(beforeUpdateUser);
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	@Test
	public void test_delete_success() throws Exception {
		User user1 = getUser1();
		
		Mockito.when(usersService.findOne(user1.getId())).thenReturn(user1);
		Mockito.doNothing().when(usersService).delete(user1.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/users/{id}", user1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(user1.getId());
		Mockito.verify(usersService, Mockito.times(1)).delete(user1.getId());
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	/**
	 * Should occur 409 code error, user do not exist
	 */
	@Test
	public void test_delete_fail() throws Exception {
		User user1 = getUser1();
		
		Mockito.when(usersService.findOne(user1.getId())).thenReturn(null);
		Mockito.doNothing().when(usersService).delete(user1.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/users/{id}", user1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(usersService, Mockito.times(1)).findOne(user1.getId());
		Mockito.verifyNoMoreInteractions(usersService);
	}
	
	private User getUser1() {
		User user1 = new User();
		user1.setId(1L);
		user1.setUsername(RandomStringUtils.randomAlphabetic(10));
		user1.setPassword(RandomStringUtils.randomAlphabetic(15));
		user1.setLastPasswordReset(null);
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER");
		user1.setAuthorities(authorities);
		user1.setAccountNonExpired(true);
		user1.setAccountNonLocked(true);
		user1.setCredentialsNonExpired(true);
		user1.setEnabled(true);
		return user1;
	}
	
	private User getUser2() {
		User user2 = new User();
		user2.setId(2L);
		user2.setUsername(RandomStringUtils.randomAlphabetic(10));
		user2.setPassword(RandomStringUtils.randomAlphabetic(15));
		user2.setLastPasswordReset(null);
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
		user2.setAuthorities(authorities);
		user2.setAccountNonExpired(true);
		user2.setAccountNonLocked(true);
		user2.setCredentialsNonExpired(true);
		user2.setEnabled(true);
		return user2;
	}
}
