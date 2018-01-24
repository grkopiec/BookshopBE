package pl.bookshop.domains;

import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity	
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "productsSequence")
	@SequenceGenerator(name = "productsSequence", sequenceName = "products_sequence")
	private Long id;
	private String name;
	private String producer;
	private String description;
	private Double price;
	private Double discount;
	@Column(name = "image_path")
	private String imagePath;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "category_id	")
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
