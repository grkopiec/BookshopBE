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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.bookshop.enums.OrderStatus;
import pl.bookshop.enums.PaymentMethod;
import pl.bookshop.enums.ShippingMethod;
import pl.bookshop.utils.Constants;

@Entity	
@Table(name = "orders")
public class Order {
	@Id
	@GenericGenerator(name = Constants.ORDERS_SEQUENCE_GENERATOR, strategy = "sequence", parameters = {
			@Parameter(name = "sequence_name", value = Constants.ORDERS_SEQUENCE)
	})
	@GeneratedValue(generator = Constants.PRODUCTS_SEQUENCE_GENERATOR)
	private Long id;
	@Column(name = "total_price")
	@NotNull(message = "{???}")
	@PositiveOrZero(message = "{???}")
	@Digits(integer = 6, fraction = 2, message = "{???}")
	private Double totalPrice;
	@Enumerated(EnumType.STRING)
	@NotNull(message = "{???}")
	private OrderStatus status;
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_method")
	@NotNull(message = "{???}")
	private PaymentMethod paymentMethod;
	@NotNull(message = "{???}")
	private ShippingMethod shippingMethod;
	@Size(min = 1, max = 1000, message = "{???}")
	private String additionalMessage;
	@NotNull(message = "{???}")
	private Boolean paid;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	@NotNull(message = "{???}")
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
		return "Order [id=" + id + ", totalrice=" + totalPrice + ", status=" + status + ", paymentMethod=" + paymentMethod + ", paid=" + paid + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(totalPrice, status, paymentMethod, paid);
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
				&& Objects.equals(this.paymentMethod, other.paymentMethod) && Objects.equals(this.paid, other.paid);
	}
}