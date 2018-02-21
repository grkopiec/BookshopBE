package pl.bookshop.configuration.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

import pl.bookshop.domainsmongo.UserDetails;

@Configuration
@EnableMongoRepositories(basePackageClasses = UserDetails.class, basePackages = "pl.bookshop.repositoriesmongo")
@EnableTransactionManagement
public class SpringDataMongoConfiguration extends AbstractMongoConfiguration {
	@Value("${mongo.databaseName}")
	private String databaseName;
	@Value("${mongo.host}")
	private String host;
	@Value("${mongo.port}")
	private Integer port;
	
	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public MongoClient mongoClient() {
		ServerAddress serverAddress = new ServerAddress(host, port);
		MongoClient mongoClient = new MongoClient(serverAddress);
		//MongoCredential.createCredential("bookshop", "bookshop", password)
		return mongoClient;
	}
}
