package pl.bookshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.bookshop.domains.Product;
import pl.bookshop.repositories.ProductsRepository;

public class ProductsServiceImpl implements ProductsService {
	@Autowired
	private ProductsRepository productsRepository;
	
	@Override
	public List<Product> findByName(String name) {
		return productsRepository.findByName(name);
	}
}
