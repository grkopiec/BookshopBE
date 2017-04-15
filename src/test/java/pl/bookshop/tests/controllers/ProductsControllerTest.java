package pl.bookshop.tests.controllers;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
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

import pl.bookshop.domains.Product;
import pl.bookshop.mvc.controllers.ProductsController;
import pl.bookshop.services.ProductsService;

public class ProductsControllerTest {
	private MockMvc mockMvc;
	
	@Mock
	private ProductsService productsService;
	
	@InjectMocks
	private ProductsController productsController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(productsController)
				.build();
	}
	
	@Test
	public void test_findAll_success() throws Exception {
		List<Product> products = Arrays.asList(getProduct1(), getProduct2());
		
		Mockito.when(productsService.findAll()).thenReturn(products);
		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(products.get(0).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is(products.get(0).getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(products.get(1).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is(products.get(1).getDescription())));
		Mockito.verify(productsService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void test_findOne_success() throws Exception {
		Product product1 = getProduct1();
		
		Mockito.when(productsService.findOne(1L)).thenReturn(product1);
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(product1.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(product1.getDescription())));
		Mockito.verify(productsService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 */
	@Test
	public void test_findOne_fail() throws Exception {
		Mockito.when(productsService.findOne(1L)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", 1L))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(productsService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	private Product getProduct1() {
		Product product1 = new Product();
		product1.setName(RandomStringUtils.randomAlphabetic(10));
		product1.setDescription(RandomStringUtils.randomAlphabetic(100));
		return product1;
	}
	
	private Product getProduct2() {
		Product product2 = new Product();
		product2.setName(RandomStringUtils.randomAlphabetic(5));
		product2.setDescription(RandomStringUtils.randomAlphabetic(50));
		return product2;
	}
}
