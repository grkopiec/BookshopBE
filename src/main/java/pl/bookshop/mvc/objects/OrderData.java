package pl.bookshop.mvc.objects;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.mongo.OrderItem;

public class OrderData {
	@Valid
	@NotNull(message = "{???}")
	private Order order;
	@Valid
	@NotNull(message = "{???}")
	private List<OrderItem> orderItems;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	
	@Override
	public String toString() {
		return "UserData [order=" + order + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(order);
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
		
		OrderData other = (OrderData) obj;
		return Objects.equals(this.order, other.order);
	}
}
