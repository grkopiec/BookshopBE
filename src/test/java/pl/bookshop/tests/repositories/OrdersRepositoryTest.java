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
import pl.bookshop.domains.jpa.Order;
import pl.bookshop.repositories.jpa.OrdersRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceContext.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@Transactional
@ActiveProfiles(profiles = "test")
@DatabaseSetup("classpath:testSchemas/oracle.xml")
public class OrdersRepositoryTest {
	@Autowired
	public OrdersRepository ordersRepository;

	@Test
	public void test_findByName_success() {
		List<Order> orders = ordersRepository.findByUserId(0L);
		Assert.assertEquals(1, orders.size());
		Assert.assertEquals(new Long(0), orders.get(0).getId());
	}

	/**
	 * Should not find exists orders
	 */
	@Test
	public void test_findByName_notExistFail() {
		List<Order> orders = ordersRepository.findByUserId(2L);
		Assert.assertEquals(0, orders.size());
	}
}
