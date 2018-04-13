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

import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.validation.AdminUser;

public class UserDetailsValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	public void test_adminUserDetailsAllFields_success() {
		UserDetails userDetails = getValidAdminUserDetails();
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_adminUserDetailsName_whenIsNull() {
		UserDetails userDetails = getValidAdminUserDetails();
		userDetails.setName(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDetailsName_whenIsTooShort() {
		String name = RandomStringUtils.randomAlphabetic(1);
		UserDetails userDetails = getValidAdminUserDetails();
		userDetails.setName(name);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name: " + name + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDetailsName_whenIsTooLong() {
		String name = RandomStringUtils.randomAlphabetic(51);
		UserDetails userDetails = getValidAdminUserDetails();
		userDetails.setName(name);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name: " + name + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDetailsSurname_whenIsNull() {
		UserDetails userDetails = getValidAdminUserDetails();
		userDetails.setSurname(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User surname cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDetailsSurname_whenIsTooShort() {
		String surname = RandomStringUtils.randomAlphabetic(1);
		UserDetails userDetails = getValidAdminUserDetails();
		userDetails.setSurname(surname);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User surname: " + surname + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_adminUserDetailsSurname_whenIsTooLong() {
		String surname = RandomStringUtils.randomAlphabetic(51);
		UserDetails userDetails = getValidAdminUserDetails();
		userDetails.setSurname(surname);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, AdminUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User surname: " + surname + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	private UserDetails getValidAdminUserDetails() {
		UserDetails userDetails = new UserDetails();
		userDetails.setName(RandomStringUtils.randomAlphabetic(30));
		userDetails.setSurname(RandomStringUtils.randomAlphabetic(50));
		return userDetails;
	}
}
