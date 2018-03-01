package pl.bookshop.domains.jpa;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pl.bookshop.utils.StringUtils;

@Entity
@Table(name = "categories")
public class Category {
	@Id
	@GenericGenerator(name = StringUtils.CATEGORIES_SEQUENCE_GENERATOR, strategy = "sequence", parameters = {
			@Parameter(name = "sequence_name", value = StringUtils.CATEGORIES_SEQUENCE)
	})
	@GeneratedValue(generator = StringUtils.CATEGORIES_SEQUENCE_GENERATOR)
	private Long id;
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
