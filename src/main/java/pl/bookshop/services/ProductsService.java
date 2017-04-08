package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.Product;

public interface ProductsService {
	public List<Product> findByName(String name);
}
