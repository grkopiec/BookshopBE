package pl.bookshop.domains.mongo;

import java.util.Objects;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
//TODO validation for orderId and productId
@Document(collection = "ordersItems")
public class OrderItems {
	@Id
	private String id;
	@NotNull(message = "{???}")
	@PositiveOrZero(message = "{???}")
	@Digits(integer = 6, fraction = 2, message = "{???}")
	private Double price;
	@NotNull(message = "{???}")
	@Range(min = 1, max = 1000, message = "{???}")
	private Long quantity;
	private Long orderId;
	private Long productId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "orderItem [id=" + id + ", price=" + price + ", quantity=" + quantity + ", orderId=" + orderId + ", productId=" + productId + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, price, quantity, orderId, productId);
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
		
		OrderItems other = (OrderItems) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.price, other.price) && Objects.equals(this.quantity, other.quantity)
				&& Objects.equals(this.orderId, other.orderId) && Objects.equals(this.productId, other.productId);
	}
}
