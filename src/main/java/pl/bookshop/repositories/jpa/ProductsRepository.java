package pl.bookshop.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.bookshop.domains.jpa.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>, ProductsRepositoryCustom {
	public Product findByName(String name);
}
