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
import pl.bookshop.domains.jpa.User;
import pl.bookshop.repositories.jpa.UsersRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
@ActiveProfiles(profiles = "test")
@DatabaseSetup("classpath:test/dataset.xml")
public class UsersRepositoryTest {
	@Autowired
	private UsersRepository usersRepository;
	
	@Test
	public void test_findByName_success() {
		User user = usersRepository.findByUsername("admin");
		Assert.assertNotNull(user);
		
		Assert.assertEquals(new Long(0), user.getId());
		Assert.assertEquals(2, user.getAuthorities().size());
	}
	
	/**
	 * Should not find not exist value
	 */
	@Test
	public void test_findByName_notExistFail() {
		User user = usersRepository.findByUsername("abstract");
		Assert.assertNull(user);
	}
	
	
	/**
	 * Should find nothing because passed only half of name of user	
	 */
	@Test
	public void test_findByName_halfNameFail() {
		User user = usersRepository.findByUsername("adm");
		Assert.assertNull(user);
	}
}
