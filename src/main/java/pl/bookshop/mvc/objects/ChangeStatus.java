package pl.bookshop.mvc.objects;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import pl.bookshop.enums.OrderStatus;

public class ChangeStatus {
	@NotNull(message = "{changeStatus.orderStatus.notNull}")
	private OrderStatus orderStatus;

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "ChangeStatus [orderStatus=" + orderStatus + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(orderStatus);
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

		ChangeStatus other = (ChangeStatus) obj;
		return Objects.equals(this.orderStatus, other.orderStatus);
	}
}
