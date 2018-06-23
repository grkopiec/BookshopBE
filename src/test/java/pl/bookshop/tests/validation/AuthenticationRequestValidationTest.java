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

import pl.bookshop.mvc.objects.AuthenticationRequest;

public class AuthenticationRequestValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void test_authenticationRequestAllFields_success() {
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_authenticationRequestUsername_whenIsNull() {
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		authenticationRequest.setUsername(null);
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<AuthenticationRequest>> iterator = constraintViolations.iterator();
		Assert.assertEquals("In authentication data username cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_authenticationRequestUsername_whenIsTooShort() {
		String username = RandomStringUtils.randomAlphabetic(1);
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		authenticationRequest.setUsername(username);
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<AuthenticationRequest>> iterator = constraintViolations.iterator();
		Assert.assertEquals("In authentication data username: " + username + " size must be between 2 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_authenticationRequestUsername_whenIsTooLong() {
		String username = RandomStringUtils.randomAlphabetic(31);
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		authenticationRequest.setUsername(username);
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<AuthenticationRequest>> iterator = constraintViolations.iterator();
		Assert.assertEquals("In authentication data username: " + username + " size must be between 2 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_authenticationRequestPassword_whenIsNull() {
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		authenticationRequest.setPassword(null);
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<AuthenticationRequest>> iterator = constraintViolations.iterator();
		Assert.assertEquals("In authentication data password cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_authenticationRequestPassword_whenIsTooShort() {
		String password = RandomStringUtils.randomAlphabetic(3);
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		authenticationRequest.setPassword(password);
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<AuthenticationRequest>> iterator = constraintViolations.iterator();
		Assert.assertEquals("In authentication data password size must be between 4 and 30", iterator.next().getMessage());
	}
	
	@Test
	public void test_authenticationRequestPassword_whenIsTooLong() {
		String password = RandomStringUtils.randomAlphabetic(31);
		AuthenticationRequest authenticationRequest = getValidAuthenticationRequest();
		authenticationRequest.setPassword(password);
		
		Set<ConstraintViolation<AuthenticationRequest>> constraintViolations = validator.validate(authenticationRequest);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<AuthenticationRequest>> iterator = constraintViolations.iterator();
		Assert.assertEquals("In authentication data password size must be between 4 and 30", iterator.next().getMessage());
	}
	
	private AuthenticationRequest getValidAuthenticationRequest() {
		AuthenticationRequest authenticationRequest = new AuthenticationRequest();
		authenticationRequest.setUsername(RandomStringUtils.randomAlphabetic(10));
		authenticationRequest.setPassword(RandomStringUtils.randomAlphabetic(15));
		return authenticationRequest;
	}
}
