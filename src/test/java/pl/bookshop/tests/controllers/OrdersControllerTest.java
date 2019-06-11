package pl.bookshop.tests.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import pl.bookshop.domains.jpa.Category;
import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;
import pl.bookshop.mvc.controllers.OrdersController;
import pl.bookshop.mvc.objects.ChangeStatus;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.mvc.objects.OrderElements;
import pl.bookshop.services.OrdersService;
import pl.bookshop.tests.utils.TestUtils;

public class OrdersControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private Validator validator;
	@Mock
	private OrdersService ordersService;
	
	@InjectMocks
	private OrdersController ordersController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(ordersController)
				.setValidator(validator)
				.build();
	}
	
	@Test
	public void test_findAll_success() throws Exception {
		List<Order> orders = Arrays.asList(getOrder0(), getOrder1());
		
		Mockito.when(ordersService.findAll()).thenReturn(orders);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(orders.get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice", Matchers.is(orders.get(0).getTotalPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is(orders.get(0).getStatus().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentMethod", Matchers.is(orders.get(0).getPaymentMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingMethod", Matchers.is(orders.get(0).getShippingMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].additionalMessage", Matchers.is(orders.get(0).getAdditionalMessage())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].paid", Matchers.is(orders.get(0).getPaid())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(orders.get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].totalPrice", Matchers.is(orders.get(1).getTotalPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].status", Matchers.is(orders.get(1).getStatus().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentMethod", Matchers.is(orders.get(1).getPaymentMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].shippingMethod", Matchers.is(orders.get(1).getShippingMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].additionalMessage", Matchers.is(orders.get(1).getAdditionalMessage())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].paid", Matchers.is(orders.get(1).getPaid())));
		
		Mockito.verify(ordersService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(ordersService);
	}
	
	@Test
	public void test_findForUser_success() throws Exception {
		List<Order> orders = Arrays.asList(getOrder0(), getOrder1());
		Long userId = 0L;
		
		Mockito.when(ordersService.findForUser(userId)).thenReturn(orders);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/user/{id}", userId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(orders.get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].totalPrice", Matchers.is(orders.get(0).getTotalPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is(orders.get(0).getStatus().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].paymentMethod", Matchers.is(orders.get(0).getPaymentMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].shippingMethod", Matchers.is(orders.get(0).getShippingMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].additionalMessage", Matchers.is(orders.get(0).getAdditionalMessage())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].paid", Matchers.is(orders.get(0).getPaid())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(orders.get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].totalPrice", Matchers.is(orders.get(1).getTotalPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].status", Matchers.is(orders.get(1).getStatus().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].paymentMethod", Matchers.is(orders.get(1).getPaymentMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].shippingMethod", Matchers.is(orders.get(1).getShippingMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].additionalMessage", Matchers.is(orders.get(1).getAdditionalMessage())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].paid", Matchers.is(orders.get(1).getPaid())));

		Mockito.verify(ordersService, Mockito.times(1)).findForUser(userId);
		Mockito.verifyNoMoreInteractions(ordersService);
	}
	
	/**
	 * Should occur 204 code error, do not found orders for user
	 */
	@Test
	public void test_findForUser_fail() throws Exception {
		Long userId = 0L;
		
		Mockito.when(ordersService.findForUser(userId)).thenReturn(new ArrayList<Order>());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/user/{id}", userId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(ordersService, Mockito.times(1)).findForUser(userId);
		Mockito.verifyNoMoreInteractions(ordersService);
	}
	
	@Test
	public void test_findOne_success() throws Exception {
		Order order = getOrder0();
		
		Mockito.when(ordersService.findOne(order.getId())).thenReturn(order);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(order.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice", Matchers.is(order.getTotalPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is(order.getStatus().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.paymentMethod", Matchers.is(order.getPaymentMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.shippingMethod", Matchers.is(order.getShippingMethod().toString())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.additionalMessage", Matchers.is(order.getAdditionalMessage())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.paid", Matchers.is(order.getPaid())));
		
		Mockito.verify(ordersService, Mockito.times(1)).findOne(order.getId());
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	/**
	 * Should occur 404 code error, do not found order
	 */
	@Test
	public void test_findOne_fail() throws Exception {
		Order order = getOrder0();
		
		Mockito.when(ordersService.findOne(order.getId())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(ordersService, Mockito.times(1)).findOne(order.getId());
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	@Test
	public void test_findItems_success() throws Exception {
		OrderElements orderElements = getOrderElements();
		
		Mockito.when(ordersService.findItems(orderElements.getOrderItems().get(0).getOrderId())).thenReturn(orderElements);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/items/{id}", orderElements.getOrderItems().get(0).getOrderId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].id", Matchers.is(orderElements.getOrderItems().get(0).getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[0].price", Matchers.is(orderElements.getOrderItems().get(0).getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.orderItems[0].quantity", Matchers.is(orderElements.getOrderItems().get(0).getQuantity().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.orderItems[0].orderId", Matchers.is(orderElements.getOrderItems().get(0).getOrderId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.orderItems[0].productId", Matchers.is(orderElements.getOrderItems().get(0).getProductId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].id", Matchers.is(orderElements.getOrderItems().get(1).getId())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.orderItems[1].price", Matchers.is(orderElements.getOrderItems().get(1).getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.orderItems[1].quantity", Matchers.is(orderElements.getOrderItems().get(1).getQuantity().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.orderItems[1].orderId", Matchers.is(orderElements.getOrderItems().get(1).getOrderId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.orderItems[1].productId", Matchers.is(orderElements.getOrderItems().get(1).getProductId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[0].id", Matchers.is(orderElements.getProducts().get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[0].name", Matchers.is(orderElements.getProducts().get(0).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[0].producer", Matchers.is(orderElements.getProducts().get(0).getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.products[0].description", Matchers.is(orderElements.getProducts().get(0).getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[0].price", Matchers.is(orderElements.getProducts().get(0).getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[0].discount", Matchers.is(orderElements.getProducts().get(0).getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[0].imagePath", Matchers.is(orderElements.getProducts().get(0).getImagePath())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.products[0].category.id", Matchers.is(orderElements.getProducts().get(0).getCategory().getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.products[0].category.name", Matchers.is(orderElements.getProducts().get(0).getCategory().getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[1].id", Matchers.is(orderElements.getProducts().get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[1].name", Matchers.is(orderElements.getProducts().get(1).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[1].producer", Matchers.is(orderElements.getProducts().get(1).getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.products[1].description", Matchers.is(orderElements.getProducts().get(1).getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[1].price", Matchers.is(orderElements.getProducts().get(1).getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[1].discount", Matchers.is(orderElements.getProducts().get(1).getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.products[1].imagePath", Matchers.is(orderElements.getProducts().get(1).getImagePath())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.products[1].category.id", Matchers.is(orderElements.getProducts().get(1).getCategory().getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath(
						"$.products[1].category.name", Matchers.is(orderElements.getProducts().get(1).getCategory().getName())));
		
		Mockito.verify(ordersService, Mockito.times(1)).findItems(orderElements.getOrderItems().get(0).getOrderId());
		Mockito.verifyNoMoreInteractions(ordersService);
	}
	
	/**
	 * Should occur 404 code error, do not found orders
	 */
	@Test
	public void test_findItems_fail() throws Exception {
		Long orderId = 0L;
		
		Mockito.when(ordersService.findItems(orderId)).thenReturn(getEmptyOrderElements());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/orders/items/{id}", orderId))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(ordersService, Mockito.times(1)).findItems(orderId);
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	@Test
	public void test_create_success() throws Exception {
		OrderData orderData = getOrderData();
		
		Mockito.doNothing().when(ordersService).create(orderData);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/orders")
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(orderData)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
		Mockito.verify(ordersService, Mockito.times(1)).create(orderData);
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	@Test
	public void test_changeStatus_success() throws Exception {
		ChangeStatus changeStatus = getChangeStatus();
		Long orderId = 0L;
		
		Mockito.doNothing().when(ordersService).changeStatus(orderId, changeStatus.getOrderStatus());
		
		mockMvc.perform(MockMvcRequestBuilders
						.patch("/orders/change-status/{id}", orderId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(changeStatus)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(ordersService, Mockito.times(1)).changeStatus(orderId, changeStatus.getOrderStatus());
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	@Test
	public void test_markAsPaid_success() throws Exception {
		Long orderId = 0L;
		
		Mockito.doNothing().when(ordersService).markAsPaid(orderId);
		
		mockMvc.perform(MockMvcRequestBuilders
						.patch("/orders/mark-as-paid/{id}", orderId))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		Mockito.verify(ordersService, Mockito.times(1)).markAsPaid(orderId);
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	@Test
	public void test_delete_success() throws Exception {
		Order order = getOrder0();
		
		Mockito.when(ordersService.findOne(order.getId())).thenReturn(order);
		Mockito.doNothing().when(ordersService).delete(order.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/orders/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		Mockito.verify(ordersService, Mockito.times(1)).findOne(order.getId());
		Mockito.verify(ordersService, Mockito.times(1)).delete(order.getId());
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	/**
	 * Should occur 409 code error, order do not exist
	 */
	@Test
	public void test_delete_fail() throws Exception {
		Order order = getOrder0();
		
		Mockito.when(ordersService.findOne(order.getId())).thenReturn(null);
		Mockito.doNothing().when(ordersService).delete(order.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/orders/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(ordersService, Mockito.times(1)).findOne(order.getId());
		Mockito.verifyNoMoreInteractions(ordersService);
	}

	private OrderData getOrderData() {
		OrderData orderData = new OrderData();
		orderData.setOrder(getOrder0());
		orderData.setOrderItems(Arrays.asList(getOrderItem0(), getOrderItem1()));
		return orderData;
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
	
	private OrderElements getOrderElements() {
		OrderElements orderElements = new OrderElements();
		orderElements.setOrderItems(Arrays.asList(getOrderItem0(), getOrderItem1()));
		orderElements.setProducts(Arrays.asList(getProduct0(), getProduct1()));
		return orderElements;
	}
	
	private OrderItem getOrderItem0() {
		OrderItem orderItem0 = new OrderItem();
		orderItem0.setId(String.valueOf(RandomUtils.nextLong(0, 100)));
		orderItem0.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		orderItem0.setQuantity(RandomUtils.nextLong(0, 10000));
		orderItem0.setOrderId(RandomUtils.nextLong(0, 100));
		orderItem0.setProductId(RandomUtils.nextLong(0, 100));
		return orderItem0;
	}
	
	private OrderItem getOrderItem1() {
		OrderItem orderItem1 = new OrderItem();
		orderItem1.setId(String.valueOf(RandomUtils.nextLong(0, 100)));
		orderItem1.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		orderItem1.setQuantity(RandomUtils.nextLong(0, 10000));
		orderItem1.setOrderId(RandomUtils.nextLong(0, 100));
		orderItem1.setProductId(RandomUtils.nextLong(0, 100));
		return orderItem1;
	}
	
	private Product getProduct0() {
		Product product0 = new Product();
		product0.setId(RandomUtils.nextLong(0, 100));
		product0.setName(RandomStringUtils.randomAlphabetic(5));
		product0.setProducer(RandomStringUtils.randomAlphabetic(10));
		product0.setDescription(RandomStringUtils.randomAlphabetic(50));
		product0.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product0.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product0.setImagePath(RandomStringUtils.randomAlphabetic(60));
		product0.setCategory(getCategory0());
		return product0;
	}
	
	private Category getCategory0() {
		Category category0 = new Category();
		category0.setId(RandomUtils.nextLong(0, 100));
		category0.setName(RandomStringUtils.randomAlphabetic(20));
		return category0;
	}
	
	private Product getProduct1() {
		Product product1 = new Product();
		product1.setId(RandomUtils.nextLong(0, 100));
		product1.setName(RandomStringUtils.randomAlphabetic(5));
		product1.setProducer(RandomStringUtils.randomAlphabetic(10));
		product1.setDescription(RandomStringUtils.randomAlphabetic(50));
		product1.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product1.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product1.setImagePath(RandomStringUtils.randomAlphabetic(60));
		product1.setCategory(getCategory1());
		return product1;
	}
	
	private Category getCategory1() {
		Category category1 = new Category();
		category1.setId(RandomUtils.nextLong(0, 100));
		category1.setName(RandomStringUtils.randomAlphabetic(20));
		return category1;
	}
	
	private OrderElements getEmptyOrderElements() {
		OrderElements orderElements = new OrderElements();
		orderElements.setOrderItems(new ArrayList<OrderItem>());
		orderElements.setProducts(new ArrayList<Product>());
		return orderElements;
	}
	
	private ChangeStatus getChangeStatus() {
		ChangeStatus changeStatus = new ChangeStatus();
		changeStatus.setOrderStatus(OrderStatus.NEW);
		return changeStatus;
	}
}
