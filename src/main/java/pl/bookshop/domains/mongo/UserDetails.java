package pl.bookshop.domains.mongo;

import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import pl.bookshop.mvc.validation.AdminUser;
import pl.bookshop.mvc.validation.NormalUser;

@Document(collection = "usersDetails")
public class UserDetails {
	@Id
	private String id;
	private Long userId;
	@NotNull(groups = {AdminUser.class, NormalUser.class}, message = "{userDetails.name.notNull}")
	@Size(min = 2, max = 50, groups = {AdminUser.class, NormalUser.class}, message = "{userDetails.name.size}")
	private String name;
	@NotNull(groups = {AdminUser.class, NormalUser.class}, message = "{userDetails.surname.notNull}")
	@Size(min = 2, max = 50, groups = {AdminUser.class, NormalUser.class}, message = "{userDetails.surname.size}")
	private String surname;
	@NotNull(groups = NormalUser.class, message = "{userDetails.email.notNull}")
	@Size(min = 6, max = 70, groups = NormalUser.class, message = "{userDetails.email.size}")
	@Email(regexp = "^[_a-z0-9]+(\\.[_a-z0-9]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,5})$",
			groups = NormalUser.class, message = "{userDetails.email.email}")
	private String email;
	@NotNull(groups = NormalUser.class, message = "{userDetails.phone.notNull}")
	@Size(min = 9, max = 15, groups = NormalUser.class, message = "{userDetails.phone.size}")
	@Pattern(regexp = "^([\\+]?\\d{2}[- ]?)?\\d{3}[- ]?\\d{3}[- ]?\\d{3}$", groups = NormalUser.class, message = "{userDetails.phone.phone}")
	private String phone;
	@NotNull(groups = NormalUser.class, message = "{userDetails.city.notNull}")
	@Size(min = 2, max = 40, groups = NormalUser.class, message = "{userDetails.city.size}")
	private String city;
	@NotNull(groups = NormalUser.class, message = "{userDetails.street.notNull}")
	@Size(min = 5, max = 50, groups = NormalUser.class, message = "{userDetails.street.size}")
	private String street;
	@Size(min = 2, max = 40, groups = NormalUser.class, message = "{userDetails.state.size}")
	private String state;
	@NotNull(groups = NormalUser.class, message = "{userDetails.zipCode.notNull}")
	@Size(min = 5, max = 7, groups = NormalUser.class, message = "{userDetails.zipCode.size}")
	private String zipCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return "UserDetails [id=" + id + ", userId=" + userId + ", name=" + name + ", surname=" + surname + ", email=" + email + ", phone=" + phone
				+ ", city=" + city + ", street=" + street + ", state=" + state + ", zipCode=" + zipCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userId, name, surname, email, phone, city, street, state, zipCode);
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
		
		UserDetails other = (UserDetails) obj;
		return Objects.equals(this.id, other.id) && Objects.equals(this.userId, other.userId) && Objects.equals(this.name, other.name)
				&& Objects.equals(this.surname, other.surname) && Objects.equals(this.email, other.email) && Objects.equals(this.phone, other.phone)
				&& Objects.equals(this.city, other.city) && Objects.equals(this.street, other.street) && Objects.equals(this.state, other.state)
				&& Objects.equals(this.zipCode, other.zipCode);
	}
}
