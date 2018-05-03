package pl.bookshop.tests.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.objects.UserData;
import pl.bookshop.mvc.validation.AdminUser;
import pl.bookshop.mvc.validation.NormalUser;

public class UserDataValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void test_adminUserDataAllFields_success() {
		UserData userData = getValidAdminUserData();
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, AdminUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_adminUserDataUser_whenIsNull() {
		UserData userData = getValidAdminUserData();
		userData.setUser(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User main data cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDataUser_whenOneValueIsNotValid() {
		UserData userData = getValidAdminUserData();
		userData.getUser().setUsername(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDataUserDetails_whenIsNull() {
		UserData userData = getValidAdminUserData();
		userData.setUserDetails(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User details data cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDataUserDetails_whenOneValueIsNotValid() {
		UserData userData = getValidAdminUserData();
		userData.getUserDetails().setName(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDataAllFields_success() {
		UserData userData = getValidNormalUserData();
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserDataUser_whenIsNull() {
		UserData userData = getValidNormalUserData();
		userData.setUser(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User main data cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDataUser_whenOneValueIsNotValid() {
		UserData userData = getValidNormalUserData();
		userData.getUser().setUsername(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDataUserDetails_whenIsNull() {
		UserData userData = getValidNormalUserData();
		userData.setUserDetails(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User details data cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDataUserDetails_whenOneValueIsNotValid() {
		UserData userData = getValidNormalUserData();
		userData.getUserDetails().setName(null);
		
		Set<ConstraintViolation<UserData>> constraintViolations = validator.validate(userData, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name cannot be empty", iterator.next().getMessage());
	}
	
	private User getValidAdminUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		return user;
	}
	
	private UserDetails getValidAdminUserDetails() {
		UserDetails userDetails = new UserDetails();
		userDetails.setName(RandomStringUtils.randomAlphabetic(30));
		userDetails.setSurname(RandomStringUtils.randomAlphabetic(50));
		return userDetails;
	}
	
	private UserData getValidAdminUserData() {
		UserData userData = new UserData();
		userData.setUser(getValidAdminUser());
		userData.setUserDetails(getValidAdminUserDetails());
		return userData;
	}
	
	private User getValidNormalUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		return user;
	}
	
	private UserDetails getValidNormalUserDetails() {
		UserDetails userDetails = new UserDetails();
		userDetails.setName(RandomStringUtils.randomAlphabetic(30));
		userDetails.setSurname(RandomStringUtils.randomAlphabetic(50));
		userDetails.setEmail(RandomStringUtils.randomAlphabetic(20).toLowerCase() + '@'
				+ RandomStringUtils.randomAlphabetic(10).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(5).toLowerCase());
		userDetails.setPhone(RandomStringUtils.randomNumeric(9));
		userDetails.setCity(RandomStringUtils.randomAlphabetic(20));
		userDetails.setStreet(RandomStringUtils.randomAlphabetic(40));
		userDetails.setState(RandomStringUtils.randomAlphabetic(20));
		userDetails.setZipCode(RandomStringUtils.randomAlphabetic(5));
		return userDetails;
	}
	
	private UserData getValidNormalUserData() {
		UserData userData = new UserData();
		userData.setUser(getValidNormalUser());
		userData.setUserDetails(getValidNormalUserDetails());
		return userData;
	}
}
