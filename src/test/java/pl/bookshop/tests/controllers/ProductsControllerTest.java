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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(products.get(0).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(products.get(0).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is(products.get(0).getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(products.get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(products.get(1).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is(products.get(1).getDescription())));
		Mockito.verify(productsService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void test_findOne_success() throws Exception {
		Product product1 = getProduct1();
		
		Mockito.when(productsService.findOne(product1.getId())).thenReturn(product1);
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", product1.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(product1.getId().intValue())))
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
		Product product1 = getProduct1();
		
		Mockito.when(productsService.findOne(1L)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", product1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(productsService, Mockito.times(1)).findOne(1L);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void test_create_success() throws Exception {
		Product product1 = getProduct1();
		
		Mockito.when(productsService.isExist(product1)).thenReturn(false);
		Mockito.doNothing().when(productsService).create(product1);
		mockMvc.perform(MockMvcRequestBuilders
						.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(product1)))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		Mockito.verify(productsService, Mockito.times(1)).isExist(product1);
		Mockito.verify(productsService, Mockito.times(1)).create(product1);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	
	/**
	 * Should occur 409 code error, passed product already exists
	 */
	@Test
	public void test_create_fail() throws Exception {
		Product product1 = getProduct1();
		
		Mockito.when(productsService.isExist(product1)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders
						.post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(product1)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		Mockito.verify(productsService, Mockito.times(1)).isExist(product1);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Scenario for this test is like this, first we are sending to PUT endpoint id exist object in database that will be replaced
	 * and new object, next in service we are finding object in database to replace. In object we passed to endpoint we are changing
	 * id on this that already contains exists object in database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_success() throws Exception {
		Product previousProduct = getProduct1();
		Product beforeUpdateProduct = getProduct2();
		Product afterUpdateProduct = getProduct2();
		afterUpdateProduct.setId(previousProduct.getId());

		
		Mockito.when(productsService.update(previousProduct.getId(), beforeUpdateProduct)).thenReturn(afterUpdateProduct);
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", previousProduct.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(beforeUpdateProduct)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(afterUpdateProduct.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(afterUpdateProduct.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(afterUpdateProduct.getDescription())));
		Mockito.verify(productsService, Mockito.times(1)).update(previousProduct.getId(), beforeUpdateProduct);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 * In this scenario object will not be change because do not exist requested id
	 */
	@Test
	public void test_update_fail() throws Exception {
		Product previousProduct = getProduct1();
		Product beforeUpdateProduct = getProduct2();
		Product afterUpdateProduct = getProduct2();
		afterUpdateProduct.setId(previousProduct.getId());

		
		Mockito.when(productsService.update(previousProduct.getId(), beforeUpdateProduct)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", previousProduct.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(toJson(beforeUpdateProduct)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(productsService, Mockito.times(1)).update(previousProduct.getId(), beforeUpdateProduct);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	private Product getProduct1() {
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName(RandomStringUtils.randomAlphabetic(10));
		product1.setDescription(RandomStringUtils.randomAlphabetic(100));
		return product1;
	}
	
	private Product getProduct2() {
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName(RandomStringUtils.randomAlphabetic(5));
		product2.setDescription(RandomStringUtils.randomAlphabetic(50));
		return product2;
	}
	
	
	private String toJson(Object object) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(object);
	}
}
