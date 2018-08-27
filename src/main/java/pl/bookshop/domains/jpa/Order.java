package pl.bookshop.domains.jpa;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
	@NotNull(message = "{???}")
	@PositiveOrZero(message = "{???}")
	@Digits(integer = 6, fraction = 2, message = "{???}")
	private Double totalPrice;
	@OneToMany(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_id")
	@Valid
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", totalrice=" + totalPrice + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(totalPrice);
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
		return Objects.equals(this.totalPrice, other.totalPrice);
	}
}