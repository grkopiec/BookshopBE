package pl.bookshop.tests.controllers;

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

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;
import pl.bookshop.mvc.controllers.OrdersController;
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
}
