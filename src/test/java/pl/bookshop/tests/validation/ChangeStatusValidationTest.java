package pl.bookshop.tests.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.bookshop.enums.OrderStatus;
import pl.bookshop.mvc.objects.ChangeStatus;

public class ChangeStatusValidationTest {
	private static Validator validator;

	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void test_allFields_success() {
		ChangeStatus changeStatus = getValidChangeStatus();

		Set<ConstraintViolation<ChangeStatus>> constraintViolations = validator.validate(changeStatus);
		Assert.assertEquals(0, constraintViolations.size());
	}

	@Test
	public void test_changeStatusOrderStatus_whenIsNull() {
		ChangeStatus changeStatus = getValidChangeStatus();
		changeStatus.setOrderStatus(null);

		Set<ConstraintViolation<ChangeStatus>> constraintViolations = validator.validate(changeStatus);
		Assert.assertEquals(1, constraintViolations.size());
	
		Iterator<ConstraintViolation<ChangeStatus>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Order status cannot be empty", iterator.next().getMessage());
	}

	private ChangeStatus getValidChangeStatus() {
		ChangeStatus changeStatus = new ChangeStatus();
		changeStatus.setOrderStatus(OrderStatus.NEW);
		return changeStatus;
	}

}
