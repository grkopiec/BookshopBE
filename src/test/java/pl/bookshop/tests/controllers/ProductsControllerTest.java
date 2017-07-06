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

import pl.bookshop.domains.Product;
import pl.bookshop.mvc.controllers.ProductsController;
import pl.bookshop.services.ProductsService;
import pl.bookshop.tests.utils.TestUtils;

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
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].producer", Matchers.is(products.get(0).getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is(products.get(0).getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(products.get(0).getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].discount", Matchers.is(products.get(0).getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].imagePath", Matchers.is(products.get(0).getImagePath())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(products.get(1).getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(products.get(1).getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].producer", Matchers.is(products.get(1).getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is(products.get(1).getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].price", Matchers.is(products.get(1).getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].imagePath", Matchers.is(products.get(1).getImagePath())));
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
				.andExpect(MockMvcResultMatchers.jsonPath("$.producer", Matchers.is(product1.getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(product1.getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(product1.getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discount", Matchers.is(product1.getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imagePath", Matchers.is(product1.getImagePath())));
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
						.content(TestUtils.toJson(product1)))
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
						.content(TestUtils.toJson(product1)))
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
		
		Mockito.when(productsService.isExist(beforeUpdateProduct)).thenReturn(false);
		Mockito.when(productsService.update(previousProduct.getId(), beforeUpdateProduct)).thenReturn(afterUpdateProduct);
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", previousProduct.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(beforeUpdateProduct)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(afterUpdateProduct.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(afterUpdateProduct.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.producer", Matchers.is(afterUpdateProduct.getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(afterUpdateProduct.getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(afterUpdateProduct.getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discount", Matchers.is(afterUpdateProduct.getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imagePath", Matchers.is(afterUpdateProduct.getImagePath())));
		Mockito.verify(productsService, Mockito.times(1)).isExist(beforeUpdateProduct);
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
		
		Mockito.when(productsService.isExist(beforeUpdateProduct)).thenReturn(false);
		Mockito.when(productsService.update(previousProduct.getId(), beforeUpdateProduct)).thenReturn(null);
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", previousProduct.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(beforeUpdateProduct)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(productsService, Mockito.times(1)).isExist(beforeUpdateProduct);
		Mockito.verify(productsService, Mockito.times(1)).update(previousProduct.getId(), beforeUpdateProduct);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 409 code error, product name already exists
	 * In this scenario object will not be change because product name already exists
	 */
	@Test
	public void test_update_nameColisionFail() throws Exception {
		Product previousProduct = getProduct1();
		Product beforeUpdateProduct = getProduct2();
		
		Mockito.when(productsService.isExist(beforeUpdateProduct)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", previousProduct.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(beforeUpdateProduct)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		Mockito.verify(productsService, Mockito.times(1)).isExist(beforeUpdateProduct);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void test_delete_success() throws Exception {
		Product product1 = getProduct1();
		
		Mockito.when(productsService.findOne(product1.getId())).thenReturn(product1);
		Mockito.doNothing().when(productsService).delete(product1.getId());
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/products/{id}", product1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
		Mockito.verify(productsService, Mockito.times(1)).findOne(product1.getId());
		Mockito.verify(productsService, Mockito.times(1)).delete(product1.getId());
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 409 code error, product do not exist
	 */
	@Test
	public void test_delete_fail() throws Exception {
		Product product1 = getProduct1();
		
		Mockito.when(productsService.findOne(product1.getId())).thenReturn(null);
		Mockito.doNothing().when(productsService).delete(product1.getId());
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/products/{id}", product1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		Mockito.verify(productsService, Mockito.times(1)).findOne(product1.getId());
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	private Product getProduct1() {
		Product product1 = new Product();
		product1.setId(1L);
		product1.setName(RandomStringUtils.randomAlphabetic(10));
		product1.setProducer(RandomStringUtils.randomAlphabetic(15));
		product1.setDescription(RandomStringUtils.randomAlphabetic(100));
		product1.setPrice(RandomUtils.nextDouble(10, 20));
		product1.setDiscount(RandomUtils.nextDouble(2, 4));
		product1.setImagePath(RandomStringUtils.randomAlphabetic(60));
		return product1;
	}
	
	private Product getProduct2() {
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName(RandomStringUtils.randomAlphabetic(5));
		product2.setProducer(RandomStringUtils.randomAlphabetic(10));
		product2.setDescription(RandomStringUtils.randomAlphabetic(50));
		product2.setPrice(RandomUtils.nextDouble(5, 15));
		product2.setDiscount(RandomUtils.nextDouble(10, 15));
		product2.setImagePath(RandomStringUtils.randomAlphabetic(60));
		return product2;
	}
}
