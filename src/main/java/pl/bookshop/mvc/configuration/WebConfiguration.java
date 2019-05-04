package pl.bookshop.mvc.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import pl.bookshop.utils.Constants;

@Configuration
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = "pl.bookshop.mvc.controllers")
public class WebConfiguration implements WebMvcConfigurer {
	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(Constants.resourceLocation);
	}
	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
		if (activeProfile.equals("development")) {
    		registry.addMapping("/**");
    	}
    }
}
