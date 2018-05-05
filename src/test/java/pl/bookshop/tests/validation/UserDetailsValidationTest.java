package pl.bookshop.tests.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import pl.bookshop.mvc.validation.NormalUser;

public class UserDetailsValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
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
	
	@Test
	public void test_normalUserDetailsAllFields_success() {
		UserDetails userDetails = getValidNormalUserDetails();
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserDetailsName_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setName(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsName_whenIsTooShort() {
		String name = RandomStringUtils.randomAlphabetic(1);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setName(name);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name: " + name + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsName_whenIsTooLong() {
		String name = RandomStringUtils.randomAlphabetic(51);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setName(name);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User name: " + name + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsSurname_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setSurname(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User surname cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsSurname_whenIsTooShort() {
		String surname = RandomStringUtils.randomAlphabetic(1);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setSurname(surname);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User surname: " + surname + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsSurname_whenIsTooLong() {
		String surname = RandomStringUtils.randomAlphabetic(51);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setSurname(surname);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User surname: " + surname + " size must be between 2 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsEmail_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setEmail(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User email cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsEmail_whenIsTooShort() {
		String email = RandomStringUtils.randomAlphabetic(1).toLowerCase() + '@' + RandomStringUtils.randomAlphabetic(1).toLowerCase()
				+ '.' + RandomStringUtils.randomAlphabetic(1).toLowerCase();
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setEmail(email);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		
		List<String> occurErrors = new ArrayList<>();
		occurErrors.add(iterator.next().getMessage());
		occurErrors.add(iterator.next().getMessage());
		
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("User email is not valid");
		expectedErrors.add("User email: " + email + " size must be between 6 and 70");
		
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(0)));
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(1)));
	}
	
	@Test
	public void test_normalUserDetailsEmail_whenIsTooLong() {
		String email = RandomStringUtils.randomAlphabetic(50).toLowerCase() + '@' + RandomStringUtils.randomAlphabetic(10).toLowerCase()
				+ '.' + RandomStringUtils.randomAlphabetic(10).toLowerCase();
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setEmail(email);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		
		List<String> occurErrors = new ArrayList<>();
		occurErrors.add(iterator.next().getMessage());
		occurErrors.add(iterator.next().getMessage());
		
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("User email is not valid");
		expectedErrors.add("User email: " + email + " size must be between 6 and 70");
		
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(0)));
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(1)));
	}
	
	@Test
	public void test_normalUserDetailsEmail_whenIsNotTypical() {
		String email = RandomStringUtils.randomAlphabetic(5).toLowerCase() + '.' + RandomStringUtils.randomNumeric(5) + '.'
				+ RandomStringUtils.randomAlphabetic(5).toLowerCase() + '_' + RandomStringUtils.randomAlphabetic(5).toLowerCase() + '@'
				+ RandomStringUtils.randomAlphabetic(10).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(10).toLowerCase() + '.'
				+ RandomStringUtils.randomNumeric(5) + '.' + RandomStringUtils.randomAlphabetic(5).toLowerCase();
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setEmail(email);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserDetailsEmail_whenNumbersAtEnd() {
		String email = RandomStringUtils.randomAlphabetic(50).toLowerCase() + '@' + RandomStringUtils.randomAlphabetic(5).toLowerCase() + '.'
				+ RandomStringUtils.randomAlphabetic(5).toLowerCase() + '.' + RandomStringUtils.randomNumeric(5);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setEmail(email);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User email is not valid", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsEmail_whenHyfenInFrontPart() {
		String email = RandomStringUtils.randomAlphabetic(20).toLowerCase() + '-' + RandomStringUtils.randomAlphabetic(20).toLowerCase() + '@'
				+ RandomStringUtils.randomAlphabetic(5).toLowerCase() + '.' + RandomStringUtils.randomAlphabetic(5).toLowerCase() + '.'
				+ RandomStringUtils.randomNumeric(5);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setEmail(email);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User email is not valid", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User phone cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenIsTooShort() {
		String phone = RandomStringUtils.randomNumeric(8);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(phone);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		
		List<String> occurErrors = new ArrayList<>();
		occurErrors.add(iterator.next().getMessage());
		occurErrors.add(iterator.next().getMessage());
		
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("User phone is not valid");
		expectedErrors.add("User phone: " + phone + " size must be between 9 and 15");
		
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(0)));
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(1)));
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenIsTooLong() {
		String phone = RandomStringUtils.randomNumeric(16);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(phone);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		
		List<String> occurErrors = new ArrayList<>();
		occurErrors.add(iterator.next().getMessage());
		occurErrors.add(iterator.next().getMessage());
		
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("User phone is not valid");
		expectedErrors.add("User phone: " + phone + " size must be between 9 and 15");
		
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(0)));
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(1)));
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenIsLongVersion() {
		String phone = "+" + RandomStringUtils.randomNumeric(2) + " " + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3)
				+ "-" + RandomStringUtils.randomNumeric(3);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(phone);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenIsShortVersion() {
		String phone = RandomStringUtils.randomNumeric(9);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(phone);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenNotAllowedChar() {
		String phone = RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3) + "_" + RandomStringUtils.randomNumeric(3);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(phone);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User phone is not valid", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsPhone_whenNotEnoughtDigits() {
		String phone = RandomStringUtils.randomNumeric(2) + "-" + RandomStringUtils.randomNumeric(3) + "-" + RandomStringUtils.randomNumeric(3);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setPhone(phone);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User phone is not valid", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsCity_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setCity(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User city cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsCity_whenIsTooShort() {
		String city = RandomStringUtils.randomAlphabetic(1);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setCity(city);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User city: " + city + " size must be between 2 and 40", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsCity_whenIsTooLong() {
		String city = RandomStringUtils.randomAlphabetic(41);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setCity(city);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User city: " + city + " size must be between 2 and 40", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsStreet_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setStreet(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User street cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsStreet_whenIsTooShort() {
		String street = RandomStringUtils.randomAlphabetic(4);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setStreet(street);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User street: " + street + " size must be between 5 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsStreet_whenIsTooLong() {
		String street = RandomStringUtils.randomAlphabetic(51);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setStreet(street);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User street: " + street + " size must be between 5 and 50", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsState_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setState(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void test_normalUserDetailsState_whenIsTooShort() {
		String state = RandomStringUtils.randomAlphabetic(1);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setState(state);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User state: " + state + " size must be between 2 and 40", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsState_whenIsTooLong() {
		String state = RandomStringUtils.randomAlphabetic(41);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setState(state);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User state: " + state + " size must be between 2 and 40", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsZipCode_whenIsNull() {
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setZipCode(null);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User zip code cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsZipCode_whenIsTooShort() {
		String zipCode = RandomStringUtils.randomAlphabetic(4);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setZipCode(zipCode);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User zip code: " + zipCode + " size must be between 5 and 7", iterator.next().getMessage());
	}
	
	@Test
	public void test_normalUserDetailsZipCode_whenIsTooLong() {
		String zipCode = RandomStringUtils.randomAlphabetic(8);
		UserDetails userDetails = getValidNormalUserDetails();
		userDetails.setZipCode(zipCode);
		
		Set<ConstraintViolation<UserDetails>> constraintViolations = validator.validate(userDetails, NormalUser.class);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<UserDetails>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User zip code: " + zipCode + " size must be between 5 and 7", iterator.next().getMessage());
	}
	
	private UserDetails getValidAdminUserDetails() {
		UserDetails userDetails = new UserDetails();
		userDetails.setName(RandomStringUtils.randomAlphabetic(30));
		userDetails.setSurname(RandomStringUtils.randomAlphabetic(50));
		return userDetails;
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
}
