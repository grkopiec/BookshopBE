package pl.bookshop.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.criteria.ProductsCriteria;
import pl.bookshop.domains.Product;
import pl.bookshop.services.ProductsService;

@RestController
@RequestMapping(path = "/products")
public class ProductsController {
	@Autowired
	private ProductsService productsService;
	
	@RequestMapping
	public ResponseEntity<List<Product>> findAll() {
		List<Product> products = productsService.findAll();
		
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}")
	public ResponseEntity<Product> findOne(@PathVariable Long id) {
		Product product = productsService.findOne(id);
		
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(product, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/search")
	public ResponseEntity<List<Product>> search(@ModelAttribute ProductsCriteria productsCriteria) {
		List<Product> products = productsService.search(productsCriteria);
		
		if (products.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Product product) {
		if (productsService.isExist(product) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		productsService.create(product);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
		if (productsService.isExist(product) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		Product updatedProduct = productsService.update(id, product);
		
		if (updatedProduct == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Product product = productsService.findOne(id);
		
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		productsService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
