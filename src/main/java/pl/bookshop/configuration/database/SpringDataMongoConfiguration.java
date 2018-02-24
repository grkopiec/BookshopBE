package pl.bookshop.configuration.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
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
	@Value("${mongo.username}")
	private String username;
	@Value("${mongo.password}")
	private String password;
	
	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	public MongoClient mongoClient() {
		ServerAddress serverAddress = new ServerAddress(host, port);
		
		char[] passwordArray = password.toCharArray();
		MongoCredential mongoCredential = MongoCredential.createCredential(username, databaseName, passwordArray);
		List<MongoCredential> mongoCredentials = new ArrayList<>();
		mongoCredentials.add(mongoCredential);
		
		MongoClient mongoClient = new MongoClient(serverAddress, mongoCredentials);
		return mongoClient;
	}
}
