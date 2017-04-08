package pl.bookshop.services;

import java.util.List;

import org.springframework.stereotype.Service;

import pl.bookshop.domains.Product;

@Service
public interface ProductsService {
	public List<Product> findByName(String name);
}
