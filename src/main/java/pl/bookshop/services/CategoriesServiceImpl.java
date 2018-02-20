package pl.bookshop.services;

import java.util.List;
import java.util.Optional;

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
		return categoriesRepository.findById(id).orElse(null);
	}

	@Override
	public Category findByName(String name) {
		return categoriesRepository.findByName(name);
	}

	@Override
	public Boolean isExist(Category category) {
		if (categoriesRepository.findByName(category.getName()) == null) {
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
		Optional<Category> updatingCategory = categoriesRepository.findById(id);
		
		return updatingCategory
				.map(o -> {
					category.setId(id);
					return categoriesRepository.save(category);
				})
				.orElse(null);
	}

	@Override
	public void delete(Long id) {
		categoriesRepository.deleteById(id);
	}
}
