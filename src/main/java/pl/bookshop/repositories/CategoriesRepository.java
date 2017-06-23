package pl.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.bookshop.domains.Category;

public interface CategoriesRepository extends JpaRepository<Category, Long> {}
