package pl.bookshop.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.domains.Product;
import pl.bookshop.repositories.ProductsRepository;

@RestController
@RequestMapping(path = "/test")
public class ProductsController {
	@Autowired
	private ProductsRepository productsRepository;
	
	@Autowired
	private String propertyValue;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Product> test() {
		System.out.println(propertyValue);
		return productsRepository.findByName("Javaprogramming");
	}
}
