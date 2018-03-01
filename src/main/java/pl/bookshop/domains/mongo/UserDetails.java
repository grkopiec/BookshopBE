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

	@Override
	public String toString() {
		return "UserDetail [id=" + id + ", userId=" + userId + ", name=" + name + ", surname=" + surname + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, userId, name, surname);
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
		return Objects.equals(this.id, other.id) && Objects.equals(this.userId, other.userId)
				&& Objects.equals(this.name, other.name) && Objects.equals(this.surname, other.surname);
	}
}
