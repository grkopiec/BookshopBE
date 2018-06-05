package pl.bookshop.mvc.objects;

import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.validation.AdminUser;
import pl.bookshop.mvc.validation.NormalUser;

public class UserData {
	@Valid
	@NotNull(groups = {AdminUser.class, NormalUser.class}, message = "{userData.user.notNull}")
	private User user;
	@Valid
	@NotNull(groups = {AdminUser.class, NormalUser.class}, message = "{userData.userDetails.notNull}")
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
