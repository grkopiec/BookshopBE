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
import pl.bookshop.criteria.ProductsCriteria;
import pl.bookshop.domains.Product;
import pl.bookshop.enums.OrderBy;
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
	public void test_search_byCategorySuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
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
	public void test_search_byCategoryNonExistFail() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setCategory("Abstract");
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(0, products.size());
	}
	
	/**
	 * Should find nothing because passed only half of name of product	
	 */
	@Test
	public void test_search_byCategoryHalfNameFail() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setCategory("Office");
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(0, products.size());
	}
	
	@Test
	public void test_search_byNameSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setName("a progr");
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(1, products.size());
	}
	
	@Test
	public void test_serarch_byPriceFromSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setPriceFrom(20.00);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(4, products.size());
	}
	
	@Test
	public void test_serarch_byPriceToSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setPriceTo(40.00);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(4, products.size());
	}
	
	@Test
	public void test_search_byPriceFromAndPriceToSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setPriceFrom(20.00);
		productCriteria.setPriceTo(40.00);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(2, products.size());
	}
	
	/**
	 * When orderBy property is set on null then method should return default order
	 */
	@Test
	public void test_search_byOrderByWithValueNullSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setOrderBy(null);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(6, products.size());
		Assert.assertEquals(new Long(0), products.get(0).getId());
		Assert.assertEquals(new Long(5), products.get(5).getId());
	}
	
	@Test
	public void test_search_byOrderByWithValueNameAscendingSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setOrderBy(OrderBy.NAMEASCENDING);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(6, products.size());
		Assert.assertEquals(new Long(1), products.get(0).getId());
		Assert.assertEquals(new Long(3), products.get(5).getId());
	}
	
	@Test
	public void test_search_byOrderByWithValueNameDescendingSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setOrderBy(OrderBy.NAMEDESCENDING);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(6, products.size());
		Assert.assertEquals(new Long(3), products.get(0).getId());
		Assert.assertEquals(new Long(1), products.get(5).getId());
	}
	
	@Test
	public void test_search_byOrderByWithValuePriceAscendingSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setOrderBy(OrderBy.PRICEASCENDING);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(6, products.size());
		Assert.assertEquals(new Long(4), products.get(0).getId());
		Assert.assertEquals(new Long(2), products.get(5).getId());
	}
	
	@Test
	public void test_search_byOrderByWithValuePriceDescendingSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setOrderBy(OrderBy.PRICEDESCENDING);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(6, products.size());
		Assert.assertEquals(new Long(2), products.get(0).getId());
		Assert.assertEquals(new Long(4), products.get(5).getId());
	}
	
	
	/**
	 * Method should first find all element which name contains letter "i" and
	 * next add limit for upper and lower price and also method should sort
	 * received result
	 */
	@Test
	public void test_search_byMultipleConditionsSuccess() {
		ProductsCriteria productCriteria = new ProductsCriteria();
		productCriteria.setName("i");
		productCriteria.setPriceFrom(10.00);
		productCriteria.setPriceTo(40.00);
		productCriteria.setOrderBy(OrderBy.NAMEASCENDING);
		List<Product> products = productsRepository.search(productCriteria);
		Assert.assertEquals(2, products.size());
		Assert.assertEquals(new Long(1), products.get(0).getId());
		Assert.assertEquals(new Long(3), products.get(1).getId());
	}
}
