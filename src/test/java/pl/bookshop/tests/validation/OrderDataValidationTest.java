package pl.bookshop.tests.validation;

import java.util.ArrayList;
import java.util.Arrays;
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
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.tests.utils.TestUtils;

public class OrderDataValidationTest {
	private static Validator validator;

	@BeforeClass
	public static void setup() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	public void test_allFields_success() {
		OrderData orderData = getValidOrderData();

		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(0, constraintViolations.size());
	}

	@Test
	public void test_orderDataOrder_whenIsNull() {
		OrderData orderData = getValidOrderData();
		orderData.setOrder(null);

		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Order details cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderDataOrder_hasOneIncorrectField() {
		OrderData orderData = getValidOrderData();
		orderData.getOrder().setUser(null);

		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("User cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderDataOrderItems_whenIsNull() {
		OrderData orderData = getValidOrderData();
		orderData.setOrderItems(null);

		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Ordered products lists cannot be empty", iterator.next().getMessage());
	}

	@Test
	public void test_orderDataOrderItems_whenListIsTooShort() {
		OrderData orderData = getValidOrderData();
		orderData.setOrderItems(new ArrayList<OrderItem>());

		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderData>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Actually ordered products: 0, ordered products quantity must be between 1 and 99",
				iterator.next().getMessage());
	}

	@Test
	public void test_orderDataOrderItems_whenListIsTooLong() throws Exception {
		OrderData orderData = getValidOrderData();
		orderData.setOrderItems(TestUtils.createListOfObjects(getValidOrderItem(), 100L));

		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(1, constraintViolations.size());

		Iterator<ConstraintViolation<OrderData>> iterator = constraintViolations.iterator();
		Assert.assertEquals(
				"Actually ordered products: 100, ordered products quantity must be between 1 and 99",
				iterator.next().getMessage());
	}

	@Test
	public void test_orderDataOrderItems_hasOneIncorrectField() {
		OrderItem orderItem = getValidOrderItem();
		orderItem.setProductId(null);
		OrderData orderData = getValidOrderData();
		orderData.setOrderItems(Arrays.asList(orderItem));
		
		Set<ConstraintViolation<OrderData>> constraintViolations = validator.validate(orderData);
		Assert.assertEquals(1, constraintViolations.size());
		
		Iterator<ConstraintViolation<OrderData>> iterator = constraintViolations.iterator();
		Assert.assertEquals("Product id cannot be empty", iterator.next().getMessage());
	}
	
	private OrderData getValidOrderData() {
		OrderData orderData = new OrderData();
		orderData.setOrder(getValidOrder());
		orderData.setOrderItems(Arrays.asList(getValidOrderItem()));
		return orderData;
	}

	private Order getValidOrder() {
		Order order = new Order();
		order.setId(RandomUtils.nextLong(0, 100));
		order.setTotalPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		order.setStatus(OrderStatus.FINISHED);
		order.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
		order.setShippingMethod(ShippingMethod.COURIER);
		order.setAdditionalMessage(RandomStringUtils.randomAlphabetic(1000));
		order.setPaid(true);
		order.setUser(getValidUser());
		return order;
	}
	
	private User getValidUser() {
		User user = new User();
		user.setUsername(RandomStringUtils.randomAlphabetic(10));
		user.setPassword(RandomStringUtils.randomAlphabetic(15));
		return user;
	}

	private OrderItem getValidOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(String.valueOf(RandomUtils.nextLong(0, 100)));
		orderItem.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		orderItem.setQuantity(RandomUtils.nextLong(0, 100));
		orderItem.setOrderId(RandomUtils.nextLong(0, 100));
		orderItem.setProductId(RandomUtils.nextLong(0, 100));
		return orderItem;
	}
}
