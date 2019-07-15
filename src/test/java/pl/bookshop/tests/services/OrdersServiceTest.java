package pl.bookshop.tests.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.mvc.objects.OrderElements;
import pl.bookshop.repositories.jpa.OrdersRepository;
import pl.bookshop.repositories.jpa.ProductsRepository;
import pl.bookshop.repositories.mongo.OrderItemsRepository;
import pl.bookshop.services.OrdersServiceImpl;
import pl.bookshop.tests.utils.TestUtils;

public class OrdersServiceTest {
	@Mock
	private ProductsRepository productsRepository;
	@Mock
	private OrdersRepository ordersRepository;
	@Mock
	private OrderItemsRepository orderItemsRepository;
	
	@InjectMocks
	private OrdersServiceImpl ordersService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_findAll_success() {
		List<Order> orders = Arrays.asList(getOrder0(), getOrder1());

		Mockito.when(ordersRepository.findAll()).thenReturn(orders);

		List<Order> answer = ordersService.findAll();

		Assert.assertEquals(orders, answer);

		Mockito.verify(ordersRepository, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	@Test
	public void test_findForUser_success() {
		Long userId = 0L;
		List<Order> orders = Arrays.asList(getOrder0(), getOrder1());

		Mockito.when(ordersRepository.findByUserId(userId)).thenReturn(orders);

		List<Order> answer = ordersService.findForUser(userId);

		Assert.assertEquals(orders, answer);

		Mockito.verify(ordersRepository, Mockito.times(1)).findByUserId(userId);
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	@Test
	public void test_findOne_success() {
		Order order = getOrder0();
		Optional<Order> orderOptional = Optional.of(order);

		Mockito.when(ordersRepository.findById(order.getId())).thenReturn(orderOptional);

		Order answer = ordersService.findOne(order.getId());

		Assert.assertEquals(order, answer);

		Mockito.verify(ordersRepository, Mockito.times(1)).findById(order.getId());
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	@Test
	public void test_findOne_failWhenNotExists() {
		Long id = getOrder0().getId();

		Mockito.when(ordersRepository.findById(id)).thenReturn(Optional.empty());

		Order answer = ordersService.findOne(id);

		Assert.assertNull(answer);

		Mockito.verify(ordersRepository, Mockito.times(1)).findById(id);
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	@Test
	public void test_findItems_success() {
		Long orderId = 0L;
		OrderElements orderElements = getOrderElements();
		List<Long> productIds = Arrays.asList(orderElements.getProducts().get(0).getId(), orderElements.getProducts().get(1).getId());

		Mockito.when(orderItemsRepository.findByOrderId(orderId)).thenReturn(orderElements.getOrderItems());
		Mockito.when(productsRepository.findByIdIn(productIds)).thenReturn(orderElements.getProducts());

		OrderElements answer = ordersService.findItems(orderId);

		Assert.assertEquals(orderElements, answer);

		Mockito.verify(orderItemsRepository, Mockito.times(1)).findByOrderId(orderId);
		Mockito.verifyNoMoreInteractions(orderItemsRepository);
		Mockito.verify(productsRepository, Mockito.times(1)).findByIdIn(productIds);
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_create_success() {
		OrderData orderData = getOrderData();
		Order order = getOrder0();

		Mockito.when(ordersRepository.save(orderData.getOrder())).thenReturn(order);

		ordersService.create(orderData);

		orderData.getOrderItems()
				.forEach(orderItem -> orderItem.setOrderId(order.getId()));
		
		Mockito.verify(ordersRepository, Mockito.times(1)).save(orderData.getOrder());
		Mockito.verifyNoMoreInteractions(ordersRepository);
		Mockito.verify(orderItemsRepository, Mockito.times(1)).saveAll(orderData.getOrderItems());
		Mockito.verifyNoMoreInteractions(orderItemsRepository);
	}

	@Test
	public void test_changeStatus_success() {
		Long orderId = 0L;
		OrderStatus orderStatus = OrderStatus.FINISHED;

		ordersService.changeStatus(orderId, orderStatus);

		Mockito.verify(ordersRepository).setStatus(orderId, orderStatus);
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	@Test
	public void test_markAsPaid_success() {
		Long orderId = 0L;

		ordersService.markAsPaid(orderId);

		Mockito.verify(ordersRepository).setPaid(orderId, Boolean.TRUE);
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	@Test
	public void test_delete_success() {
		Long orderId = 0L;

		ordersService.delete(orderId);

		Mockito.verify(orderItemsRepository).deleteByOrderId(orderId);
		Mockito.verifyNoMoreInteractions(orderItemsRepository);
		Mockito.verify(ordersRepository).deleteById(orderId);
		Mockito.verifyNoMoreInteractions(ordersRepository);
	}

	private Order getOrder0() {
		Order order0 = new Order();
		order0.setId(RandomUtils.nextLong(0, 100));
		order0.setTotalPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		order0.setStatus(OrderStatus.FINISHED);
		order0.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
		order0.setShippingMethod(ShippingMethod.COURIER);
		order0.setAdditionalMessage(RandomStringUtils.randomAlphabetic(1000));
		order0.setPaid(true);
		return order0;
	}

	private Order getOrder1() {
		Order order1 = new Order();
		order1.setId(RandomUtils.nextLong(0, 100));
		order1.setTotalPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		order1.setStatus(OrderStatus.NEW);
		order1.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
		order1.setShippingMethod(ShippingMethod.COURIER);
		order1.setAdditionalMessage(RandomStringUtils.randomAlphabetic(1000));
		order1.setPaid(false);
		return order1;
	}

	private OrderItem getOrderItem0(Long productId) {
		OrderItem orderItem0 = new OrderItem();
		orderItem0.setId(String.valueOf(RandomUtils.nextLong(0, 100)));
		orderItem0.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		orderItem0.setQuantity(RandomUtils.nextLong(0, 100));
		orderItem0.setOrderId(RandomUtils.nextLong(0, 100));
		orderItem0.setProductId(productId);
		return orderItem0;
	}

	private OrderItem getOrderItem1(Long productId) {
		OrderItem orderItem1 = new OrderItem();
		orderItem1.setId(String.valueOf(RandomUtils.nextLong(0, 100)));
		orderItem1.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		orderItem1.setQuantity(RandomUtils.nextLong(0, 100));
		orderItem1.setOrderId(RandomUtils.nextLong(0, 100));
		orderItem1.setProductId(productId);
		return orderItem1;
	}

	private Product getProduct0(Long productId) {
		Product product0 = new Product();
		product0.setId(productId);
		product0.setName(RandomStringUtils.randomAlphabetic(5));
		product0.setProducer(RandomStringUtils.randomAlphabetic(10));
		product0.setDescription(RandomStringUtils.randomAlphabetic(50));
		product0.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product0.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product0.setImagePath(RandomStringUtils.randomAlphabetic(60));
		return product0;
	}

	private Product getProduct1(Long productId) {
		Product product1 = new Product();
		product1.setId(productId);
		product1.setName(RandomStringUtils.randomAlphabetic(5));
		product1.setProducer(RandomStringUtils.randomAlphabetic(10));
		product1.setDescription(RandomStringUtils.randomAlphabetic(50));
		product1.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product1.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product1.setImagePath(RandomStringUtils.randomAlphabetic(60));
		return product1;
	}

	private OrderElements getOrderElements() {
		Long product0Id = RandomUtils.nextLong(0, 100);
		Long product1Id = RandomUtils.nextLong(0, 100);

		OrderElements orderElements = new OrderElements();
		orderElements.setOrderItems(Arrays.asList(getOrderItem0(product0Id), getOrderItem1(product1Id)));
		orderElements.setProducts(Arrays.asList(getProduct0(product0Id), getProduct1(product1Id)));
		return orderElements;
	}

	private OrderData getOrderData() {
		Long product0Id = RandomUtils.nextLong(0, 100);
		Long product1Id = RandomUtils.nextLong(0, 100);

		OrderData orderData = new OrderData();
		orderData.setOrder(getOrder0());
		orderData.setOrderItems(Arrays.asList(getOrderItem0(product0Id), getOrderItem1(product1Id)));
		return orderData;
	}
}
