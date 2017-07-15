package pl.bookshop.repositories;

import java.util.List;

import pl.bookshop.criteria.ProductsCriteria;
import pl.bookshop.domains.Product;

public interface ProductsRepositoryCustom {
	/**
	 * Search products in database using various parameters like criteria, pagination and order list.
	 * If any parameter is missed (that means is null, contains white spaces or is blank then is do 
	 * not taking into account when result is calculating.
	 *
	 * @param object that contains data like criteria, sort order, pagination
	 * @return list of filtered, sorted and after pagination products
	 */
	public List<Product> search(ProductsCriteria productsCriteria);
}
