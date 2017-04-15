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
		Product product1 = new Product();
		product1.setName(RandomStringUtils.randomAlphabetic(10));
		product1.setDescription(RandomStringUtils.randomAlphabetic(100));
		
		Product product2 = new Product();
		product2.setName(RandomStringUtils.randomAlphabetic(5));
		product2.setDescription(RandomStringUtils.randomAlphabetic(50));
		
		List<Product> products = Arrays.asList(product1, product2);
		Mockito.when(productsService.findAll()).thenReturn(products);
		mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
		Mockito.verify(productsService, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(productsService);
	}
}
