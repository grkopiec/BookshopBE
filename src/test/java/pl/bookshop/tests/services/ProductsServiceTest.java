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
import org.springframework.beans.BeanUtils;

import pl.bookshop.criteria.ProductsCriteria;
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.repositories.jpa.ProductsRepository;
import pl.bookshop.services.ProductsServiceImpl;
import pl.bookshop.tests.utils.TestUtils;

public class ProductsServiceTest {
	@Mock
	private ProductsRepository productsRepository;
	
	@InjectMocks
	private ProductsServiceImpl productsService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_findAll_success() {
		List<Product> products = Arrays.asList(getProduct0(), getProduct1());

		Mockito.when(productsRepository.findAll()).thenReturn(products);

		List<Product> answer = productsService.findAll();

		Assert.assertEquals(products, answer);

		Mockito.verify(productsRepository, Mockito.times(1)).findAll();
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_findOne_success() {
		Product product = getProduct0();
		Optional<Product> productOptional = Optional.of(product);

		Mockito.when(productsRepository.findById(product.getId())).thenReturn(productOptional);

		Product answer = productsService.findOne(product.getId());

		Assert.assertEquals(product, answer);

		Mockito.verify(productsRepository, Mockito.times(1)).findById(product.getId());
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_findOne_failWhenNotExists() {
		Long id = getProduct0().getId();

		Mockito.when(productsRepository.findById(id)).thenReturn(Optional.empty());

		Product answer = productsService.findOne(id);

		Assert.assertNull(answer);

		Mockito.verify(productsRepository, Mockito.times(1)).findById(id);
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_findByName_success() {
		Product product = getProduct0();

		Mockito.when(productsRepository.findByName(product.getName())).thenReturn(product);

		Product answer = productsService.findByName(product.getName());

		Assert.assertEquals(product, answer);

		Mockito.verify(productsRepository, Mockito.times(1)).findByName(product.getName());
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_search_success() {
		List<Product> products = Arrays.asList(getProduct0(), getProduct1());
		ProductsCriteria productsCriteria = new ProductsCriteria();
		
		Mockito.when(productsRepository.search(productsCriteria)).thenReturn(products);
		
		List<Product> answer = productsService.search(productsCriteria);
		
		Assert.assertEquals(products, answer);
		
		Mockito.verify(productsRepository, Mockito.times(1)).search(productsCriteria);
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_isExist_success() {
		Product product = getProduct0();

		Mockito.when(productsRepository.findByName(product.getName())).thenReturn(product);

		Boolean answer = productsService.isExist(product);

		Assert.assertTrue(answer);

		Mockito.verify(productsRepository, Mockito.times(1)).findByName(product.getName());
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_isExist_failWhenNotExists() {
		Product product = getProduct0();

		Mockito.when(productsRepository.findByName(product.getName())).thenReturn(null);

		Boolean answer = productsService.isExist(product);

		Assert.assertFalse(answer);

		Mockito.verify(productsRepository, Mockito.times(1)).findByName(product.getName());
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_create_success() {
		Product product = getProduct0();

		productsService.create(product);

		Mockito.verify(productsRepository, Mockito.times(1)).save(product);
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_update_success() {
		Long id = RandomUtils.nextLong(0, 100);
		Product product = getProduct0();
		Product updatedProduct = getUpdatedProduct(product, id);

		productsService.update(id, product);

		Mockito.verify(productsRepository, Mockito.times(1)).save(updatedProduct);
		Mockito.verifyNoMoreInteractions(productsRepository);
	}

	@Test
	public void test_delete_success() {
		Long id = RandomUtils.nextLong(0, 100);

		productsService.delete(id);

		Mockito.verify(productsRepository, Mockito.times(1)).deleteById(id);
		Mockito.verifyNoMoreInteractions(productsRepository);
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
		return product0;
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
		return product1;
	}

	private Product getUpdatedProduct(Product product, Long id) {
		Product updatedProduct = new Product();
		BeanUtils.copyProperties(product, updatedProduct);
		updatedProduct.setId(id);
		return updatedProduct;
	}
}
