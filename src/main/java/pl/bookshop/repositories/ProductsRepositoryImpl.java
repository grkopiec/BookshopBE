package pl.bookshop.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;

import pl.bookshop.criteria.ProductCriteria;
import pl.bookshop.domains.Category;
import pl.bookshop.domains.Product;

public class ProductsRepositoryImpl implements ProductsRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Product> search(ProductCriteria productCriteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(productCriteria.getCategory())) {
			Path<Category> path = root.join("category").get("name");
			predicates.add(criteriaBuilder.equal(path, productCriteria.getCategory()));
		}
		
		criteriaQuery.select(root).where(predicates.toArray(new Predicate[] {}));
		
		Query query = entityManager.createQuery(criteriaQuery);
		List<Product> products = query.getResultList();
		
		return products;
	}
}
