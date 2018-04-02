package pl.bookshop.mvc.objects;

import java.util.Objects;

import javax.validation.Valid;

import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;

public class UserData {
	@Valid
	private User user;
	@Valid
	private UserDetails userDetails;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserDetails getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	@Override
	public String toString() {
		return "UserData [user=" + user + ", userDetails=" + userDetails + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(user, userDetails);
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
		
		UserData other = (UserData) obj;
		return Objects.equals(this.user, other.user) && Objects.equals(this.userDetails, other.userDetails);
	}
	
	
}
