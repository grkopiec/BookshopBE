package pl.bookshop.domains;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCTS")
public class Product {
	@Id
	private Long id;
	private String name;
	private String producer;
	private String description;
	private Double price;
	private Double discount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", producer=" + producer + ", description=" + description +
				", price=" + price + ", discount=" + discount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, producer, description, price, discount);
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
		
		Product other = (Product) obj;
		return Objects.equals(this.name, other.name) && Objects.equals(this.producer, other.producer) &&
				Objects.equals(this.description, other.description) && Objects.equals(this.price, other.price) &&
				Objects.equals(this.discount, other.discount);
	}
}
