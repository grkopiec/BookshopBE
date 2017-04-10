package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.Product;

public interface ProductsService {
	public List<Product> findAll();
	public Product findOne(Long id);
	public Boolean isExsit(Product product);
	public void create(Product product);
	public Product update(Long id, Product product);
	public void delete(Long id);
	public List<Product> findByName(String name);
}
