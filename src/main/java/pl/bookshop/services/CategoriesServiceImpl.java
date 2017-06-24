package pl.bookshop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.Category;
import pl.bookshop.repositories.CategoriesRepository;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {
	@Autowired
	private CategoriesRepository categoriesRepository;

	@Override
	public List<Category> findAll() {
		return categoriesRepository.findAll();
	}

	@Override
	public Category findOne(Long id) {
		return categoriesRepository.findOne(id);
	}

	@Override
	public List<Category> findByName(String name) {
		return categoriesRepository.findByName(name);
	}

	@Override
	public Boolean isExist(Category category) {
		if (categoriesRepository.findByName(category.getName()).isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public void create(Category category) {
		categoriesRepository.save(category);
	}

	@Override
	public Category update(Long id, Category category) {
		Category updatingCategory = categoriesRepository.findOne(id);
		
		if (updatingCategory == null) {
			return null;
		}
		category.setId(id);
		return categoriesRepository.save(category);
	}

	@Override
	public void delete(Long id) {
		categoriesRepository.delete(id);
	}
}
