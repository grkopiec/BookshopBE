package pl.bookshop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.bookshop.criteria.ProductCriteria;
import pl.bookshop.domains.Product;
import pl.bookshop.repositories.ProductsRepository;

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
		return productsRepository.findOne(id);
	}
	
	@Override
	public Product findByName(String name) {
		return productsRepository.findByName(name);
	}
	
	@Override
	public List<Product> search(ProductCriteria productCriteria) {
		return productsRepository.search(productCriteria);
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
		Product updatingProduct = productsRepository.findOne(id);
		
		if (updatingProduct == null) {
			return null;
		}
		product.setId(id);
		return productsRepository.save(product);
	}
	
	@Override
	public void delete(Long id) {
		productsRepository.delete(id);
	}
}
