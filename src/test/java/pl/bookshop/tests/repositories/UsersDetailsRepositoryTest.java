package pl.bookshop.tests.repositories;

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
}
