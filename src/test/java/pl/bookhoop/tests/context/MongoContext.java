package pl.bookhoop.tests.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = "pl.bookshop.repositories.mongo")
@EnableTransactionManagement
@ComponentScan(basePackages = "pl.bookshop.domains.mongo")
public class MongoContext extends AbstractMongoConfiguration {
	@Bean
	@Override
	public MongoClient mongoClient() {
		Fongo fongo = new Fongo("mongo");
		MongoClient mongoClient = fongo.getMongo();
		return mongoClient;
	}

	@Override
	protected String getDatabaseName() {
		return "bookshop-test";
	}
	
}
