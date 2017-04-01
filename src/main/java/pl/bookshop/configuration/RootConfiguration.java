package pl.bookshop.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan
public class RootConfiguration {
	@Value("${testKey}")
	private String test;
	
	@Profile("development")
	@Bean
	public static PropertyPlaceholderConfigurer developmentProperties() {
		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(new ClassPathResource("development.properties"));
		return propertyPlaceholderConfigurer;
	}
	
	@Profile("production")
	@Bean
	public static PropertyPlaceholderConfigurer productionProperties() {
		PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
		propertyPlaceholderConfigurer.setLocation(new ClassPathResource("production.properties"));
		return propertyPlaceholderConfigurer;
	}
	
	@Bean
	public String testBean() {
		return test;
	}
}
