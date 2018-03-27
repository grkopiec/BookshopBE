package pl.bookshop.domains.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.bookshop.utils.Constants;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GenericGenerator(name = Constants.CATEGORIES_SEQUENCE_GENERATOR, strategy = "sequence", parameters = {
			@Parameter(name = "sequence_name", value = Constants.CATEGORIES_SEQUENCE)
	})
	@GeneratedValue(generator = Constants.CATEGORIES_SEQUENCE_GENERATOR)
	private Long id;
	@NotNull
	@Size(min = 2, max = 100)
	private String name;
	@OneToMany(mappedBy = "category")
	@JsonIgnore
	private List<Product> products;
	
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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
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
		
		Category other = (Category) obj;
		return Objects.equals(this.name, other.name);
	}
}
