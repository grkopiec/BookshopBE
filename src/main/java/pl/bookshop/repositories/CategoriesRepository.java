package pl.bookshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.bookshop.domains.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
	public List<Category> findByName(String name);
}
