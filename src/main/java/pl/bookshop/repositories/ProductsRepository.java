package pl.bookshop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.bookshop.domains.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>, ProductsRepositoryCustom {
	public List<Product> findByName(String name);
}
