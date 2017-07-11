package pl.bookshop.configuration.database;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "pl.bookshop.repositories")
@EnableTransactionManagement
public class SpringDataConfiguration {
}
