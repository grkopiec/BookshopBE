package pl.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.Category;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
	public Category findByName(String name);
}
