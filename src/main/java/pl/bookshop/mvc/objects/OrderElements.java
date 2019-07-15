package pl.bookshop.mvc.objects;

import java.util.List;
import java.util.Objects;

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
	
	@Override
	public String toString() {
		return "OrderElements [orderItems=" + orderItems + ", products=" + products + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(orderItems, products);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		
		OrderElements other = (OrderElements) obj;
		return Objects.equals(this.orderItems, other.orderItems) && Objects.equals(this.products, other.products);
	}
}
