package pl.bookshop.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.domains.Product;
import pl.bookshop.services.ProductsService;

@RestController
@RequestMapping(path = "/test")
public class ProductsController {
	@Autowired
	private ProductsService productsService;
	
	@RequestMapping
	public List<Product> test() {
		return productsService.findByName("Javaprogramming");
	}
}
