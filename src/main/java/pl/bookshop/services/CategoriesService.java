package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.Category;

public interface CategoriesService {
	public List<Category> findAll();
	public Category findOne(Long id);
	public List<Category> findByName(String name);
	public Boolean isExist(Category Category);
	public void create(Category Category);
	public Category update(Long id, Category Category);
	public void delete(Long id);
}
