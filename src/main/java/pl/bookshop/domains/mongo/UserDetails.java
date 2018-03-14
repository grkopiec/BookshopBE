package pl.bookshop.domains.mongo;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usersDetails")
public class UserDetails {
	@Id
	private String id;
	private Long userId;
	private String name;
	private String surname;
	private String email;
	private String phone;
	private String city;
	private String street;
	private String state;
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
