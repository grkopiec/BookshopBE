package pl.bookshop.tests.validation;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.tests.utils.TestUtils;

public class OrderItemValidationTest {
	private static Validator validator;

	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void test_allFields_success() {
		OrderItem orderItem = getValidOrderItem();

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(0, constraintViolations.size());
	}

	@Test
	public void test_orderItemPrice_whenIsNull() {
		OrderItem orderItem = getValidOrderItem();
		orderItem.setPrice(null);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product price cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderItemPrice_whenIsNotPositive() {
		Double price = TestUtils.nextNegativeDoubleWithDecimalPlaces(-99, -10, 2);
		OrderItem orderItem = getValidOrderItem();
		orderItem.setPrice(price);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product price must be zero or greater", iterator.next().getMessage());
	}

	@Test
	public void test_orderItemPrice_whenTooMuchNumbersBeforeComma() {
		Double price = TestUtils.nextDoubleWithDecimalPlaces(1000000, 9999999, 2);
		OrderItem orderItem = getValidOrderItem();
		orderItem.setPrice(price);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Product price: " + price + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}

	@Test
	public void test_orderItemPrice_whenTooMuchDecimalPlaces() {
		Double price = TestUtils.nextDoubleWithDecimalPlaces(100000, 999999, 3);
		OrderItem orderItem = getValidOrderItem();
		orderItem.setPrice(price);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Product price: " + price + " cannot have more than 6 digits before decimal places and 2 in digital places",
				iterator.next().getMessage());
	}

	@Test
	public void test_orderItemQuantity_whenIsNull() {
		OrderItem orderItem = getValidOrderItem();
		orderItem.setQuantity(null);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product quantity cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderItemQuantity_whenIsTooSmall() {
		Long quantity = 0L;
		OrderItem orderItem = getValidOrderItem();
		orderItem.setQuantity(quantity);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product quantity: " + quantity + " must be between 1 and 999", iterator.next().getMessage());
	}

	@Test
	public void test_orderItemQuantity_whenIsTooLarge() {
		Long quantity = 1000L;
		OrderItem orderItem = getValidOrderItem();
		orderItem.setQuantity(quantity);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product quantity: " + quantity + " must be between 1 and 999", iterator.next().getMessage());
	}

	@Test
	public void test_orderItemProductId_whenIsNull() {
		OrderItem orderItem = getValidOrderItem();
		orderItem.setProductId(null);

		Set<ConstraintViolation<OrderItem>> constraintViolations = validator.validate(orderItem);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderItem>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product id cannot be empty", iterator.next().getMessage());
	}

	private OrderItem getValidOrderItem() {
		OrderItem orderItem0 = new OrderItem();
		orderItem0.setId(String.valueOf(RandomUtils.nextLong(0, 100)));
		orderItem0.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		orderItem0.setQuantity(RandomUtils.nextLong(0, 100));
		orderItem0.setOrderId(RandomUtils.nextLong(0, 100));
		orderItem0.setProductId(RandomUtils.nextLong(0, 100));
		return orderItem0;
	}
}
