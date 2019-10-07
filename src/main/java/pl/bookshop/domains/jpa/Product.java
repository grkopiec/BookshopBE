package pl.bookshop.domains.jpa;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity	
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull(message = "{product.name.notNull}")
	@Size(min = 1, max = 100, message = "{product.name.size}")
	private String name;
	@NotNull(message = "{product.producer.notNull}")
	@Size(min = 1, max = 100, message = "{product.producer.size}")
	private String producer;
	@Size(min = 1, max = 1000, message = "{product.description.size}")
	private String description;
	@NotNull(message = "{product.price.notNull}")
	@PositiveOrZero(message = "{product.price.positiveOrZero}")
	@Digits(integer = 6, fraction = 2, message = "{product.price.digits}")
	private Double price;
	@PositiveOrZero(message = "{product.discount.positiveOrZero}")
	@Digits(integer = 6, fraction = 2, message = "{product.discount.digits}")
	private Double discount;
	@Column(name = "image_path")
	private String imagePath;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "category_id	")
	@Valid
	@NotNull(message = "{product.category.notNull}")
	private Category category;

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", producer=" + producer + ", description=" + description +
				", price=" + price + ", discount=" + discount + ", imagePath=" + imagePath + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, producer, description, price, discount, imagePath);
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
		return Objects.equals(this.name, other.name) && Objects.equals(this.producer, other.producer)
				&& Objects.equals(this.description, other.description) && Objects.equals(this.price, other.price)
				&& Objects.equals(this.discount, other.discount) && Objects.equals(this.imagePath, other.imagePath);
	}
}
