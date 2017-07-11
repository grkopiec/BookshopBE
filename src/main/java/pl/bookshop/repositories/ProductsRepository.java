package pl.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.bookshop.domains.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>, ProductsRepositoryCustom {
	public Product findByName(String name);
}
