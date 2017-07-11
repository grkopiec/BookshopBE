package pl.bookshop.services;

import java.util.List;

import pl.bookshop.criteria.ProductCriteria;
import pl.bookshop.domains.Product;

public interface ProductsService {
	public List<Product> findAll();
	public Product findOne(Long id);
	public Product findByName(String name);
	public List<Product> search(ProductCriteria productCriteria);
	
	/**
	 * This method extracts name from received object ant try find it in database
	 * 
	 * @param product object that contains name which will be compare with existing
	 * @return {@code true} if name exists, {@code false} in other case
	 */
	public Boolean isExist(Product product);
	public void create(Product product);
	public Product update(Long id, Product product);
	public void delete(Long id);
}
