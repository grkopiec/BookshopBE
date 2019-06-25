package pl.bookshop.tests.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.jpa.User;
import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;
import pl.bookshop.tests.utils.TestUtils;

public class OrderValidationTest {
	private static Validator validator;
	
	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}
	
	@Test
	public void test_allFields_success() {
		Order order = getValidOrder();

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(0, constraintViolations.size());
	}

	@Test
	public void test_orderTotalPrice_whenIsNull() {
		Order order = getValidOrder();
		order.setTotalPrice(null);
		
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Total price cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderTotalPrice_whenIsNotPositive() {
		Double totalPrice = TestUtils.nextNegativeDoubleWithDecimalPlaces(-99, -10, 2);
		Order order = getValidOrder();
		order.setTotalPrice(totalPrice);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Total price must be zero or greater", iterator.next().getMessage());
	}

	@Test
	public void test_orderTotalPrice_whenTooMuchNumbersBeforeComma() {
		Double totalPrice = TestUtils.nextDoubleWithDecimalPlaces(1000000, 9999999, 3);
		Order order = getValidOrder();
		order.setTotalPrice(totalPrice);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Total price: " + totalPrice + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}

	@Test
	public void test_orderTotalPrice_whenTooMuchDecimalPlaces() {
		Double totalPrice = TestUtils.nextDoubleWithDecimalPlaces(100000, 999999, 3);
		Order order = getValidOrder();
		order.setTotalPrice(totalPrice);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Total price: " + totalPrice + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}

	@Test
	public void test_orderStatus_whenIsNull() {
		Order order = getValidOrder();
		order.setStatus(null);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Status cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderPaymentMethod_whenIsNull() {
		Order order = getValidOrder();
		order.setPaymentMethod(null);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Payment method cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderShippingMethod_whenIsNull() {
		Order order = getValidOrder();
		order.setShippingMethod(null);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Shipping method cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderAdditionalMessage_whenIsTooShort() {
		String additionalMessage = RandomStringUtils.randomAlphabetic(0);
		Order order = getValidOrder();
		order.setAdditionalMessage(additionalMessage);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Additional message: " + additionalMessage + " size must be between 1 and 1000", iterator.next().getMessage());
	}

	@Test
	public void test_orderAdditionalMessage_whenIsTooLong() {
		String additionalMessage = RandomStringUtils.randomAlphabetic(1001);
		Order order = getValidOrder();
		order.setAdditionalMessage(additionalMessage);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Additional message: " + additionalMessage + " size must be between 1 and 1000", iterator.next().getMessage());
	}

	@Test
	public void test_orderPaid_whenIsNull() {
		Order order = getValidOrder();
		order.setPaid(null);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Paid value must be set on true or false", iterator.next().getMessage());
	}

	@Test
	public void test_orderUser_whenIsNull() {
		Order order = getValidOrder();
		order.setUser(null);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<Order>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderUser_successWhenHasOneIncorrectField() {
		User user = getValidUser();
		user.setUsername(null);
		Order order = getValidOrder();
		order.setUser(user);

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		Assert.assertEquals(0, constraintViolations.size());
	}

	private Order getValidOrder() {
		Order order = new Order();
		order.setId(RandomUtils.nextLong(0, 100));
		order.setTotalPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		order.setStatus(OrderStatus.NEW);
		order.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
		order.setShippingMethod(ShippingMethod.COURIER);
		order.setAdditionalMessage(RandomStringUtils.randomAlphabetic(100));
		order.setPaid(false);
		order.setUser(getValidUser());
		return order;
	}

	private User getValidUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		return user;
	}
}
