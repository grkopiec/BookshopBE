package pl.bookshop.domains.jpa;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;

@Entity	
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue
	private Long id;
	@Column(name = "total_price")
	@NotNull(message = "{order.totalPrice.notNull}")
	@PositiveOrZero(message = "{order.totalPrice.positiveOrZero}")
	@Digits(integer = 6, fraction = 2, message = "{order.totalPrice.digits}")
	private Double totalPrice;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	@NotNull(message = "{order.status.notNull}")
	private OrderStatus status;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	@NotNull(message = "{order.paymentMethod.notNull}")
	private PaymentMethod paymentMethod;
	@Enumerated(EnumType.STRING)
	@Column(name = "shipping_method")
	@NotNull(message = "{order.shippingMethod.notNull}")
	private ShippingMethod shippingMethod;
	@Column(name = "additional_message")
	@Size(min = 1, max = 1000, message = "{order.additionalMessage.size}")
	private String additionalMessage;
	@Column(name = "paid")
	@NotNull(message = "{order.paid.notNull}")
	private Boolean paid;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id")
	@NotNull(message = "{order.user.notNull}")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public ShippingMethod getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(ShippingMethod shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}

	public void setAdditionalMessage(String additionalMessage) {
		this.additionalMessage = additionalMessage;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", totalPrice=" + totalPrice + ", status=" + status + ", paymentMethod=" + paymentMethod + ", shippingMethod="
				+ shippingMethod + ", additionalMessage=" + additionalMessage + ", paid=" + paid + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(totalPrice, status, paymentMethod, shippingMethod, additionalMessage, paid);
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
		
		Order other = (Order) obj;
		return Objects.equals(this.totalPrice, other.totalPrice) && Objects.equals(this.status, other.status)
				&& Objects.equals(this.paymentMethod, other.paymentMethod) && Objects.equals(this.shippingMethod, other.shippingMethod)
				&& Objects.equals(this.additionalMessage, other.additionalMessage) && Objects.equals(this.paid, other.paid);
	}
}