package pl.bookshop.repositories.jpa;

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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import pl.bookshop.criteria.ProductsCriteria;
import pl.bookshop.domains.jpa.Category;
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.enums.OrderBy;

@Repository
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Product> search(ProductsCriteria productsCriteria) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		
		criteriaQuery.select(root);
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (StringUtils.isNotBlank(productsCriteria.getCategory())) {
			Path<Category> path = root.join("category").get("name");
			predicates.add(
					criteriaBuilder.equal(path, productsCriteria.getCategory()));
		}
		
		if (StringUtils.isNotBlank(productsCriteria.getName())) {
			predicates.add(
					criteriaBuilder.like(
							criteriaBuilder.lower(root.get("name")),
							"%" + productsCriteria.getName().toLowerCase() + "%"));
		}
		
		if (productsCriteria.getPriceFrom() != null) {
			predicates.add(
					criteriaBuilder.gt(root.get("price"), productsCriteria.getPriceFrom()));
		}
		
		if (productsCriteria.getPriceTo() != null) {
			predicates.add(
					criteriaBuilder.lt(root.get("price"), productsCriteria.getPriceTo()));
		}
		
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		
		if (productsCriteria.getOrderBy() != null) {
			if (productsCriteria.getOrderBy().equals(OrderBy.NAMEASCENDING)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("name")));
			} else if (productsCriteria.getOrderBy().equals(OrderBy.NAMEDESCENDING)) {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("name")));
			} else if (productsCriteria.getOrderBy().equals(OrderBy.PRICEASCENDING)) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get("price")));
			} else if (productsCriteria.getOrderBy().equals(OrderBy.PRICEDESCENDING)) {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get("price")));
			}
		}
		
		Query query = entityManager.createQuery(criteriaQuery);
		List<Product> products = query.getResultList();
		
		return products;
	}
}
