package pl.bookshop.tests.repositories;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder;

import pl.bookhoop.tests.context.MongoContext;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.repositories.mongo.OrderItemsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoContext.class})
@ActiveProfiles(profiles = "test")
public class OrderItemsRepositoryTest {
	@Rule
	public MongoDbRule mongoDbRule = MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb("bookshop-test");

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_findByOrderId_success() {
		List<OrderItem> userDetails = orderItemsRepository.findByOrderId(0L);

		Assert.assertEquals(1, userDetails.size());
	}

	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_findByOrderId_notExistFail() {
		List<OrderItem> userDetails = orderItemsRepository.findByOrderId(2L);

		Assert.assertTrue(userDetails.isEmpty());
	}

	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_deleteByOrderId_success() {
		List<OrderItem> allOrdersItemsBefore = orderItemsRepository.findAll();
		orderItemsRepository.deleteByOrderId(0L);
		List<OrderItem> allOrdersItemsAfter = orderItemsRepository.findAll();

		Assert.assertEquals(2, allOrdersItemsBefore.size());
		Assert.assertEquals(1, allOrdersItemsAfter.size());
	}

	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_deleteByOrderId_deleteNotExists() {
		List<OrderItem> allOrdersItemsBefore = orderItemsRepository.findAll();
		orderItemsRepository.deleteByOrderId(2L);
		List<OrderItem> allOrdersItemsAfter = orderItemsRepository.findAll();

		Assert.assertEquals(2, allOrdersItemsBefore.size());
		Assert.assertEquals(2, allOrdersItemsAfter.size());
	}
}
