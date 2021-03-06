package pl.bookshop.tests.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.repositories.mongo.UsersDetailsRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MongoContext.class})
@ActiveProfiles(profiles = "test")
public class UsersDetailsRepositoryTest {
	@Rule
	public MongoDbRule mongoDbRule = MongoDbRuleBuilder.newMongoDbRule().defaultSpringMongoDb("bookshop-test");
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private UsersDetailsRepository usersDetailsRepository;
	
	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_findByUserId_success() {
		Optional<UserDetails> userDetails = usersDetailsRepository.findByUserId(0L);
		
		Assert.assertTrue(userDetails.isPresent());
	}
	
	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_findByUserId_notExistFail() {
		Optional<UserDetails> userDetails = usersDetailsRepository.findByUserId(2L);
		
		Assert.assertFalse(userDetails.isPresent());
	}
	
	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_findByUserIdIn_success() {
		List<Long> usersIds = new ArrayList<>();
		usersIds.add(0L);
		usersIds.add(1L);
		List<UserDetails> usersDetails = usersDetailsRepository.findByUserIdIn(usersIds);
		
		Assert.assertEquals(2, usersDetails.size());
	}
	
	/**
	 * In this scenario from <code>Mongo</code> database are finding two ids, one id exists but second does not exist.
	 */
	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_findByUserIdIn_partlyNotFound() {
		List<Long> usersIds = new ArrayList<>();
		usersIds.add(1L);
		usersIds.add(2L);
		List<UserDetails> usersDetails = usersDetailsRepository.findByUserIdIn(usersIds);
		
		Assert.assertEquals(1, usersDetails.size());
	}
	
	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_deleteByUserId_success() {
		List<UserDetails> allUsersDetailsBefore = usersDetailsRepository.findAll();
		usersDetailsRepository.deleteByUserId(0L);
		List<UserDetails> allUsersDetailsAfter = usersDetailsRepository.findAll();
		
		Assert.assertEquals(2, allUsersDetailsBefore.size());
		Assert.assertEquals(1, allUsersDetailsAfter.size());
	}
	
	@Test
	@UsingDataSet(locations = {"/testSchemas/mongo.json"}, loadStrategy = LoadStrategyEnum.CLEAN_INSERT)
	public void test_deleteByUserId_deleteNotExists() {
		List<UserDetails> allUsersDetailsBefore = usersDetailsRepository.findAll();
		usersDetailsRepository.deleteByUserId(2L);
		List<UserDetails> allUsersDetailsAfter = usersDetailsRepository.findAll();
		
		Assert.assertEquals(2, allUsersDetailsBefore.size());
		Assert.assertEquals(2, allUsersDetailsAfter.size());
	}
}
