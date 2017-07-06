package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.Category;

public interface CategoriesService {
	public List<Category> findAll();
	public Category findOne(Long id);
	public List<Category> findByName(String name);
	
	/**
	 * This method extracts name from received object ant try find it in database
	 * 
	 * @param product object that contains name which will be compare with existing
	 * @return {@code true} if name exists, {@code false} in other case
	 */
	public Boolean isExist(Category Category);
	public void create(Category Category);
	public Category update(Long id, Category Category);
	public void delete(Long id);
}
