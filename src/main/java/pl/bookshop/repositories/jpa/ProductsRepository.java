package pl.bookshop.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.bookshop.domains.jpa.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>, ProductsRepositoryCustom {
	public Product findByName(String name);
	public List<Product> findByIdIn(List<Long> ids);
}
