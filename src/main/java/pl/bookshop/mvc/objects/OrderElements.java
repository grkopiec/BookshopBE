package pl.bookshop.mvc.objects;

import java.util.List;

import pl.bookshop.domains.jpa.Product;
import pl.bookshop.domains.mongo.OrderItem;

public class OrderElements {
	//TODO this two lists should be combined in one list of elements
	private List<OrderItem> orderItems;
	private List<Product> products;

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}
}
