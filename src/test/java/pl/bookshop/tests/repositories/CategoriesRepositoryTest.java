package pl.bookshop.tests.repositories;

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
import pl.bookshop.domains.jpa.Category;
import pl.bookshop.repositories.jpa.CategoriesRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
@ActiveProfiles(profiles = "test")
@DatabaseSetup("classpath:testSchemas/oracle.xml")
public class CategoriesRepositoryTest {
	@Autowired
	private CategoriesRepository categoriesRepository;

	@Test
	public void test_findByName_success() {
		Category category = categoriesRepository.findByName("Books");
		Assert.assertNotNull(category);
		
		Assert.assertEquals(new Long(0), category.getId());
		Assert.assertEquals(2, category.getProducts().size());
	}

	/**
	 * Should not find not exist value
	 */
	@Test
	public void test_findByName_notExistFail() {
		Category category = categoriesRepository.findByName("Abstract");
		Assert.assertNull(category);
	}

	/**
	 * Should find nothing because passed only half of name of category	
	 */
	@Test
	public void test_findByName_halfNameFail() {
		Category category = categoriesRepository.findByName("Office");
		Assert.assertNull(category);
	}
}
