package pl.bookshop.tests.validation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import pl.bookhoop.tests.context.SpringSecurityContext;
import pl.bookhoop.tests.context.UserDetailsServiceContext;
import pl.bookshop.mvc.objects.NewPassword;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {UserDetailsServiceContext.class, SpringSecurityContext.class})
@WebAppConfiguration
public class NewPasswordValidationTest {
    @Autowired
    private Validator validator;
	
	@Test
	@WithUserDetails("admin")
	public void test_allFields_success() {		
		NewPassword newPassword = getNewPassword();
		
		Set<ConstraintViolation<NewPassword>> constraintViolations = validator.validate(newPassword);
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	@WithUserDetails("admin")
	public void test_allFields_whenCurrentPasswordAndNewPasswordAreTheSame() {
		String password = "admin";
		
		NewPassword newPassword = getNewPassword();
		newPassword.setCurrentPassword(password);
		newPassword.setNewPassword(password);
		
		Set<ConstraintViolation<NewPassword>> constraintViolations = validator.validate(newPassword);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<NewPassword>> iterator = constraintViolations.iterator();
		Assert.assertEquals("New password cannot be the same like current", iterator.next().getMessage());
		
	}
	
	@Test
	@WithUserDetails("admin")
	public void test_currentPassword_whenIsNull() {
		NewPassword newPassword = getNewPassword();
		newPassword.setCurrentPassword(null);
		
		Set<ConstraintViolation<NewPassword>> constraintViolations = validator.validate(newPassword);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<NewPassword>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Current password cannot be empty", iterator.next().getMessage());
	}
	
	@Test
	public void test_currentPassword_whenIsTooShort() {
		NewPassword newPassword = getNewPassword();
		newPassword.setCurrentPassword(RandomStringUtils.randomAlphabetic(3));
		
		Set<ConstraintViolation<NewPassword>> constraintViolations = validator.validate(newPassword);
		Assert.assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<NewPassword>> iterator = constraintViolations.iterator();
		
		List<String> occurErrors = new ArrayList<>();
		occurErrors.add(iterator.next().getMessage());
		occurErrors.add(iterator.next().getMessage());
		
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("Current password is not correct");
		expectedErrors.add("Current password size must be between 4 and 30");
		
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(0)));
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(1)));
	}
	
	@Test
	public void test_currentPassword_whenIsTooLong() {
		NewPassword newPassword = getNewPassword();
		newPassword.setCurrentPassword(RandomStringUtils.randomAlphabetic(41));
		
		Set<ConstraintViolation<NewPassword>> constraintViolations = validator.validate(newPassword);
		Assert.assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<NewPassword>> iterator = constraintViolations.iterator();
		
		List<String> occurErrors = new ArrayList<>();
		occurErrors.add(iterator.next().getMessage());
		occurErrors.add(iterator.next().getMessage());
		
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("Current password is not correct");
		expectedErrors.add("Current password size must be between 4 and 30");
		
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(0)));
		Assert.assertTrue(occurErrors.contains(expectedErrors.get(1)));
	}
	
	@Test
	public void test_currentPassword_whenInvalidPassword() {
		NewPassword newPassword = getNewPassword();
		newPassword.setCurrentPassword(RandomStringUtils.randomAlphabetic(30));
		
		Set<ConstraintViolation<NewPassword>> constraintViolations = validator.validate(newPassword);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<NewPassword>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Current password is not correct", iterator.next().getMessage());
	}
	
	private NewPassword getNewPassword() {
		NewPassword newPassword = new NewPassword();
		newPassword.setCurrentPassword("admin");
		newPassword.setNewPassword(RandomStringUtils.randomAlphabetic(20));
		return newPassword;
	}
}
