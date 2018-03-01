package pl.bookshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.bookshop.criteria.ProductsCriteria;
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.repositories.jpa.ProductsRepository;

@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {
	@Autowired
	private ProductsRepository productsRepository;
	
	@Override
	public List<Product> findAll() {
		return productsRepository.findAll();
	}
	
	@Override
	public Product findOne(Long id) {
		return productsRepository.findById(id).orElse(null);
	}
	
	@Override
	public Product findByName(String name) {
		return productsRepository.findByName(name);
	}
	
	@Override
	public List<Product> search(ProductsCriteria productsCriteria) {
		return productsRepository.search(productsCriteria);
	}
	
	@Override
	public Boolean isExist(Product product) {
		if (productsRepository.findByName(product.getName()) == null) {
			return false;
		}
		return true;
	}
	
	@Override
	public void create(Product product) {
		productsRepository.save(product);
	}
	
	@Override
	public Product update(Long id, Product product) {
		Optional<Product> updatingProduct = productsRepository.findById(id);
		
		return updatingProduct
				.map(o -> {
					product.setId(id);
					return productsRepository.save(product);
				})
				.orElse(null);
	}
	
	@Override
	public void delete(Long id) {
		productsRepository.deleteById(id);
	}
}
