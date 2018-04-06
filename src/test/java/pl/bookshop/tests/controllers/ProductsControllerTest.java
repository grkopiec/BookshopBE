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

import pl.bookshop.domains.jpa.Category;
import pl.bookshop.domains.jpa.Product;
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
		List<Product> products = Arrays.asList(getProduct0(), getProduct1());
		
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
		Product product = getProduct0();
		
		Mockito.when(productsService.findOne(product.getId())).thenReturn(product);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", product.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(product.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(product.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.producer", Matchers.is(product.getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(product.getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(product.getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discount", Matchers.is(product.getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imagePath", Matchers.is(product.getImagePath())));
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product.getId());
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 404 code error, do not found user
	 */
	@Test
	public void test_findOne_fail() throws Exception {
		Product product = getProduct0();
		
		Mockito.when(productsService.findOne(product.getId())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", product.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product.getId());
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void test_create_success() throws Exception {
		Product product1 = getProduct0();
		
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
		Product product1 = getProduct0();
		
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
	 * and new object, new object has different name than existing then we need check if such name already contains any product in
	 * table of products. Next in service we are finding object in database to replace. In object we passed to endpoint we are changing
	 * id on this that already contains exists object in database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_successWithDifferentNames() throws Exception {
		Product product = getProduct0();
		Product updatingProduct = getProduct1();
		Product updatedProduct = getProduct2();
		
		Mockito.when(productsService.findOne(product.getId())).thenReturn(updatingProduct);
		Mockito.when(productsService.isExist(product)).thenReturn(false);
		Mockito.when(productsService.update(product.getId(), product)).thenReturn(updatedProduct);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", product.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(product)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(updatedProduct.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(updatedProduct.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.producer", Matchers.is(updatedProduct.getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(updatedProduct.getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(updatedProduct.getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discount", Matchers.is(updatedProduct.getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imagePath", Matchers.is(updatedProduct.getImagePath())));
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product.getId());
		Mockito.verify(productsService, Mockito.times(1)).isExist(product);
		Mockito.verify(productsService, Mockito.times(1)).update(product.getId(), product);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Scenario for this test is like this, first we are sending to PUT endpoint id exist object in database that will be replaced
	 * and new object, new object has the same name as already existing product in table of products. Next in service we are finding
	 * object in database to replace. In object we passed to endpoint we are changing id on this that already contains exists object
	 * in database, and after it we replace object into new, but we left the same id
	 */
	@Test
	public void test_update_successWithSameNames() throws Exception {
		Product product = getProduct0();
		Product updatingProduct = getProduct1();
		updatingProduct.setName(product.getName());
		Product updatedProduct = getProduct2();
		
		Mockito.when(productsService.findOne(product.getId())).thenReturn(updatingProduct);
		Mockito.when(productsService.update(product.getId(), product)).thenReturn(updatedProduct);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", product.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(product)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(updatedProduct.getId().intValue())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(updatedProduct.getName())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.producer", Matchers.is(updatedProduct.getProducer())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(updatedProduct.getDescription())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.is(updatedProduct.getPrice())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discount", Matchers.is(updatedProduct.getDiscount())))
				.andExpect(MockMvcResultMatchers.jsonPath("$.imagePath", Matchers.is(updatedProduct.getImagePath())));
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product.getId());
		Mockito.verify(productsService, Mockito.times(1)).update(product.getId(), product);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 404 code error, do not found user. In this scenario object will not be change because do not exist requested id
	 */
	@Test
	public void test_update_doNotFindIdfail() throws Exception {
		Product product = getProduct0();
		
		Mockito.when(productsService.findOne(product.getId())).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", product.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(product)))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product.getId());
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	/**
	 * Should occur 409 code error, product name already exists. In this scenario object will not be change because product name
	 * already exists
	 */
	@Test
	public void test_update_nameColisionFail() throws Exception {
		Product product = getProduct0();
		Product updatingProduct = getProduct1();
		
		Mockito.when(productsService.findOne(product.getId())).thenReturn(updatingProduct);
		Mockito.when(productsService.isExist(product)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/products/{id}", product.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(TestUtils.toJson(product)))
				.andExpect(MockMvcResultMatchers.status().isConflict());
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product.getId());
		Mockito.verify(productsService, Mockito.times(1)).isExist(product);
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	@Test
	public void test_delete_success() throws Exception {
		Product product1 = getProduct0();
		
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
		Product product1 = getProduct0();
		
		Mockito.when(productsService.findOne(product1.getId())).thenReturn(null);
		Mockito.doNothing().when(productsService).delete(product1.getId());
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/products/{id}", product1.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
		
		Mockito.verify(productsService, Mockito.times(1)).findOne(product1.getId());
		Mockito.verifyNoMoreInteractions(productsService);
	}
	
	private Category getCategory0() {
		Category category0 = new Category();
		category0.setId(RandomUtils.nextLong(0, 100));
		category0.setName(RandomStringUtils.randomAlphabetic(20));
		return category0;
	}
	
	private Product getProduct0() {
		Product product0 = new Product();
		product0.setId(RandomUtils.nextLong(0, 100));
		product0.setName(RandomStringUtils.randomAlphabetic(10));
		product0.setProducer(RandomStringUtils.randomAlphabetic(15));
		product0.setDescription(RandomStringUtils.randomAlphabetic(100));
		product0.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product0.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product0.setImagePath(RandomStringUtils.randomAlphabetic(60));
		product0.setCategory(getCategory0());
		return product0;
	}
	
	private Category getCategory1() {
		Category category1 = new Category();
		category1.setId(RandomUtils.nextLong(0, 100));
		category1.setName(RandomStringUtils.randomAlphabetic(20));
		return category1;
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
	
	private Category getCategory2() {
		Category category2 = new Category();
		category2.setId(RandomUtils.nextLong(0, 100));
		category2.setName(RandomStringUtils.randomAlphabetic(20));
		return category2;
	}
	
	private Product getProduct2() {
		Product product2 = new Product();
		product2.setId(RandomUtils.nextLong(0, 100));
		product2.setName(RandomStringUtils.randomAlphabetic(5));
		product2.setProducer(RandomStringUtils.randomAlphabetic(10));
		product2.setDescription(RandomStringUtils.randomAlphabetic(50));
		product2.setPrice(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product2.setDiscount(TestUtils.nextDoubleWithDecimalPlaces(1000, 10000, 2));
		product2.setImagePath(RandomStringUtils.randomAlphabetic(60));
		product2.setCategory(getCategory2());
		return product2;
	}
}
