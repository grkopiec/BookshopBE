package pl.bookshop.tests.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.objects.UserData;
import pl.bookshop.repositories.jpa.UsersRepository;
import pl.bookshop.repositories.mongo.UsersDetailsRepository;
import pl.bookshop.services.UsersServiceImpl;
import pl.bookshop.tests.utils.TestUtils;

public class UsersServiceTest {
	@Mock
	private UsersRepository usersRepository;
	@Mock
	private UsersDetailsRepository usersDetailsRepository;
	@Mock
	private UserUtils userUtils;
	
	@InjectMocks
	private UsersServiceImpl usersService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_findAll_success() {
		List<User> users = Arrays.asList(getUser0(0L), getUser1(1L));
		List<Long> usersIds = Arrays.asList(0L, 1L);
		List<UserDetails> usersDetails = Arrays.asList(getUserDetails0(0L), getUserDetails1(1L));
		List<UserData> usersData = Arrays.asList(getUserData(users.get(0), usersDetails.get(0)), getUserData(users.get(1), usersDetails.get(1)));

		Mockito.when(usersRepository.findAll()).thenReturn(users);
		Mockito.when(usersDetailsRepository.findByUserIdIn(usersIds)).thenReturn(usersDetails);

		List<UserData> answer = usersService.findAll();

		Assert.assertEquals(usersData, answer);

		Mockito.verify(usersRepository, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(usersRepository);
		Mockito.verify(usersDetailsRepository, Mockito.times(1)).findByUserIdIn(usersIds);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	@Test
	public void test_findOne_success() {
		Long id = 0L;
		UserDetails userDetails = getUserDetails0(id);
		Optional<UserDetails> userDetailsOptional = Optional.of(userDetails);

		Mockito.when(usersDetailsRepository.findByUserId(id)).thenReturn(userDetailsOptional);

		UserDetails answer = usersService.findOne(id);

		Assert.assertEquals(userDetails, answer);

		Mockito.verify(usersDetailsRepository, Mockito.times(1)).findByUserId(id);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	@Test
	public void test_findOne_failWhenNotExists() {
		Long id = 0L;

		Mockito.when(usersDetailsRepository.findByUserId(id)).thenReturn(Optional.empty());

		UserDetails answer = usersService.findOne(id);

		Assert.assertNull(answer);

		Mockito.verify(usersDetailsRepository, Mockito.times(1)).findByUserId(id);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	@Test
	public void test_findByName_success() {
		User user = getUser0(0L);

		Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(user);

		User answer = usersService.findByUsername(user.getUsername());

		Assert.assertEquals(user, answer);

		Mockito.verify(usersRepository, Mockito.times(1)).findByUsername(user.getUsername());
		Mockito.verifyNoMoreInteractions(usersRepository);
	}

	@Test
	public void test_isExist_success() {
		User user = getUser0(0L);
		UserData userData = getUserData(user, getUserDetails0(0L));

		Mockito.when(usersRepository.findByUsername(userData.getUser().getUsername())).thenReturn(user);

		Boolean answer = usersService.isExist(userData);

		Assert.assertTrue(answer);

		Mockito.verify(usersRepository, Mockito.times(1)).findByUsername(userData.getUser().getUsername());
		Mockito.verifyNoMoreInteractions(usersRepository);
	}

	@Test
	public void test_isExist_failWhenNotExists() {
		UserData userData = getUserData(getUser0(0L), getUserDetails0(0L));

		Mockito.when(usersRepository.findByUsername(userData.getUser().getUsername())).thenReturn(null);

		Boolean answer = usersService.isExist(userData);

		Assert.assertFalse(answer);

		Mockito.verify(usersRepository, Mockito.times(1)).findByUsername(userData.getUser().getUsername());
		Mockito.verifyNoMoreInteractions(usersRepository);
	}

	@Test
	public void test_create_success() throws Exception {
		Long id = 0L;
		Long updatedId = 1L;
		String password = RandomStringUtils.randomAlphabetic(15);
		String encodedPassword = RandomStringUtils.randomAlphabetic(15);
		User user = getUser0(0L);
		user.setPassword(password);
		User updatedUser = TestUtils.clone(user);
		updatedUser.setPassword(encodedPassword);
		User savedUser = TestUtils.clone(updatedUser);
		savedUser.setId(updatedId);
		UserDetails userDetails = getUserDetails0(id);
		UserDetails updatedUserDetails = TestUtils.clone(userDetails);
		updatedUserDetails.setUserId(updatedId);
		UserData userData = getUserData(user, userDetails);
		
		Mockito.when(userUtils.encodePassword(password)).thenReturn(encodedPassword);
		Mockito.when(usersRepository.save(updatedUser)).thenReturn(savedUser);

		usersService.create(userData);

		Mockito.verify(userUtils, Mockito.times(1)).encodePassword(password);
		Mockito.verifyNoMoreInteractions(userUtils);
		Mockito.verify(usersRepository, Mockito.times(1)).save(updatedUser);
		Mockito.verifyNoMoreInteractions(usersRepository);
		Mockito.verify(usersDetailsRepository, Mockito.times(1)).save(updatedUserDetails);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	@Test
	public void test_update_success() throws Exception {
		Long userId = 1L;
		String updatingUserDetailsId = RandomStringUtils.randomAlphabetic(20);
		UserDetails userDetails = getUserDetails0(userId);
		UserDetails updatingUserDetails = getUserDetails0(userId);
		updatingUserDetails.setId(updatingUserDetailsId);
		Optional<UserDetails> updatingUserDetailsOptional = Optional.ofNullable(updatingUserDetails);
		UserDetails updatedUserDetails = TestUtils.clone(userDetails);
		updatedUserDetails.setId(updatingUserDetailsId);
		updatedUserDetails.setUserId(userId);
		
		Mockito.when(usersDetailsRepository.findByUserId(userId)).thenReturn(updatingUserDetailsOptional);
		Mockito.when(usersDetailsRepository.save(updatedUserDetails)).thenReturn(updatedUserDetails);
		
		UserDetails answer = usersService.update(userId, userDetails);
		
		Assert.assertEquals(updatedUserDetails, answer);
		
		Mockito.verify(usersDetailsRepository, Mockito.times(1)).findByUserId(userId);
		Mockito.verify(usersDetailsRepository, Mockito.times(1)).save(updatedUserDetails);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	@Test
	public void test_update_failWhenNotExists() {
		Long userId = 1L;
		UserDetails userDetails = getUserDetails0(userId);
		
		Mockito.when(usersDetailsRepository.findByUserId(userId)).thenReturn(Optional.empty());
		
		UserDetails answer = usersService.update(userId, userDetails);
		
		Assert.assertEquals(null, answer);
		
		Mockito.verify(usersDetailsRepository, Mockito.times(1)).findByUserId(userId);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	@Test
	public void test_changePassword_success() {
		Long userId = 0L;
		String password = RandomStringUtils.randomAlphabetic(15);
		String encodedPassword = RandomStringUtils.randomAlphabetic(15);
		
		Mockito.when(userUtils.encodePassword(password)).thenReturn(encodedPassword);
		
		usersService.changePassword(userId, password);
		
		Mockito.verify(userUtils, Mockito.times(1)).encodePassword(password);
		Mockito.verifyNoMoreInteractions(userUtils);
		Mockito.verify(usersRepository, Mockito.times(1)).setPassword(userId, encodedPassword);
		Mockito.verifyNoMoreInteractions(usersRepository);
	}

	@Test
	public void test_delete_success() {
		Long userId = 1L;
		
		usersService.delete(userId);
		
		Mockito.verify(usersRepository, Mockito.times(1)).deleteById(userId);
		Mockito.verifyNoMoreInteractions(usersRepository);
		Mockito.verify(usersDetailsRepository, Mockito.times(1)).deleteByUserId(userId);
		Mockito.verifyNoMoreInteractions(usersDetailsRepository);
	}

	private UserData getUserData(User user, UserDetails userDetails) {
		UserData userData = new UserData();
		userData.setUser(user);
		userData.setUserDetails(userDetails);
		return userData;
	}
	
	private User getUser0(Long id) {
		User user0 = new User();
		user0.setId(id);
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
	
	private UserDetails getUserDetails0(Long userId) {
		UserDetails userDetails0 = new UserDetails();
		userDetails0.setId(RandomStringUtils.randomAlphabetic(20));
		userDetails0.setUserId(userId);
		userDetails0.setName(RandomStringUtils.randomAlphabetic(30));
		userDetails0.setSurname(RandomStringUtils.randomAlphabetic(50));
		userDetails0.setEmail(RandomStringUtils.randomAlphabetic(20).toLowerCase() + '@'
				+ RandomStringUtils.randomAlphabetic(10).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(5).toLowerCase());
		userDetails0.setPhone(RandomStringUtils.randomNumeric(9));
		userDetails0.setCity(RandomStringUtils.randomAlphabetic(20));
		userDetails0.setStreet(RandomStringUtils.randomAlphabetic(40));
		userDetails0.setState(RandomStringUtils.randomAlphabetic(20));
		userDetails0.setZipCode(RandomStringUtils.randomAlphabetic(5));
		return userDetails0;
	}
	
	private User getUser1(Long id) {
		User user1 = new User();
		user1.setId(id);
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
	
	private UserDetails getUserDetails1(Long userId) {
		UserDetails userDetails1 = new UserDetails();
		userDetails1.setId(RandomStringUtils.randomAlphabetic(20));
		userDetails1.setUserId(userId);
		userDetails1.setName(RandomStringUtils.randomAlphabetic(30));
		userDetails1.setSurname(RandomStringUtils.randomAlphabetic(50));
		userDetails1.setEmail(RandomStringUtils.randomAlphabetic(20).toLowerCase() + '@'
				+ RandomStringUtils.randomAlphabetic(10).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(5).toLowerCase());
		userDetails1.setPhone(RandomStringUtils.randomNumeric(9));
		userDetails1.setCity(RandomStringUtils.randomAlphabetic(20));
		userDetails1.setStreet(RandomStringUtils.randomAlphabetic(40));
		userDetails1.setState(RandomStringUtils.randomAlphabetic(20));
		userDetails1.setZipCode(RandomStringUtils.randomAlphabetic(5));
		return userDetails1;
	}
}
