package pl.bookshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.Product;
import pl.bookshop.repositories.ProductsRepository;

@Service
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	private ProductsRepository productsRepository;
	
	@Override
	public List<Product> findByName(String name) {
		return productsRepository.findByName(name);
	}
}
