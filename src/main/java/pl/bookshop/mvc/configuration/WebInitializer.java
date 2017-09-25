package pl.bookshop.mvc.configuration;

import javax.servlet.Filter;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import pl.bookshop.configuration.RootConfiguration;
import pl.bookshop.configuration.filters.AuthenticationTokenFilter;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {WebConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	

	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new AuthenticationTokenFilter()};
	}
}
