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
import pl.bookshop.mvc.validation.groups.AdminUser;
import pl.bookshop.mvc.validation.groups.NormalUser;

public class UserValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void test_adminUserAllFields_success() {
		User user = getValidAdminUser();
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_adminUserUsername_whenIsNull() {
		User user = getValidAdminUser();
		user.setUsername(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserUsername_whenIsTooShort() {
		String username = RandomStringUtils.randomAlphabetic(1);
		User user = getValidAdminUser();
		user.setUsername(username);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username: " + username + " size must be between 2 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserUsername_whenIsTooLong() {
		String username = RandomStringUtils.randomAlphabetic(31);
		User user = getValidAdminUser();
		user.setUsername(username);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username: " + username + " size must be between 2 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserPassword_whenIsNull() {
		User user = getValidAdminUser();
		user.setPassword(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User password cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserPassword_whenIsTooShort() {
		String password = RandomStringUtils.randomAlphabetic(3);
		User user = getValidAdminUser();
		user.setPassword(password);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User password: " + password + " size must be between 4 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserPassword_whenIsTooLong() {
		String password = RandomStringUtils.randomAlphabetic(31);
		User user = getValidAdminUser();
		user.setPassword(password);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User password: " + password + " size must be between 4 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserAllFields_success() {
		User user = getValidNormalUser();
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserUsername_whenIsNull() {
		User user = getValidNormalUser();
		user.setUsername(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserUsername_whenIsTooShort() {
		String username = RandomStringUtils.randomAlphabetic(1);
		User user = getValidNormalUser();
		user.setUsername(username);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username: " + username + " size must be between 2 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserUsername_whenIsTooLong() {
		String username = RandomStringUtils.randomAlphabetic(31);
		User user = getValidNormalUser();
		user.setUsername(username);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User username: " + username + " size must be between 2 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserPassword_whenIsNull() {
		User user = getValidNormalUser();
		user.setPassword(null);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User password cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserPassword_whenIsTooShort() {
		String password = RandomStringUtils.randomAlphabetic(3);
		User user = getValidNormalUser();
		user.setPassword(password);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User password: " + password + " size must be between 4 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserPassword_whenIsTooLong() {
		String password = RandomStringUtils.randomAlphabetic(31);
		User user = getValidNormalUser();
		user.setPassword(password);
		
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<User>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User password: " + password + " size must be between 4 and 30", iterator.next().getMessage());
	}
	
	private User getValidAdminUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		return user;
	}
	
	private User getValidNormalUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		return user;
	}
}
