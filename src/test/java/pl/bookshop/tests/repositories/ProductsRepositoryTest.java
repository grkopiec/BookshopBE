package pl.bookshop.tests.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import pl.bookhoop.tests.context.PersistanceContext;
import pl.bookshop.criteria.ProductCriteria;
import pl.bookshop.domains.Product;
import pl.bookshop.repositories.ProductsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
@ActiveProfiles(profiles = "test")
@DatabaseSetup("classpath:test/dataset.xml")
public class ProductsRepositoryTest {
	@Autowired
	private ProductsRepository productsRepository;
	
	@Test
	public void test_findByName_success() {
		Product product = productsRepository.findByName("Java programming");
		Assert.assertNotNull(product);
		
		Assert.assertEquals(new Long(0), product.getId());
		Assert.assertEquals(new Long(0), product.getCategory().getId());
	}
	
	/**
	 * Should not find not exist value
	 */
	@Test
	public void test_findByName_notExistFail() {
		Product product = productsRepository.findByName("Abstract book");
		Assert.assertNull(product);
	}
	
	/**
	 * Should find nothing because passed only half of name of product	
	 */
	@Test
	public void test_findByName_halfNameFail() {
		Product product = productsRepository.findByName("Java");
		Assert.assertNull(product);
	}
	
	@Test
	public void test_search_success() {
		ProductCriteria productCriteria = new ProductCriteria();
		productCriteria.setCategory("Books");
		
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(2, products.size());
		Assert.assertEquals("Books", products.get(0).getCategory().getName());
		Assert.assertEquals("Books", products.get(1).getCategory().getName());
	}
	
	/**
	 * Should not find not exist value
	 */
	@Test
	public void test_search_nonExistFail() {
		ProductCriteria productCriteria = new ProductCriteria();
		productCriteria.setCategory("Abstract");
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(0, products.size());
	}
	
	/**
	 * Should find nothing because passed only half of name of product	
	 */
	@Test
	public void test_search_halfNameFail() {
		ProductCriteria productCriteria = new ProductCriteria();
		productCriteria.setCategory("Office");
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(0, products.size());
	}
}
