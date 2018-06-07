package pl.bookshop.mvc.objects;

import java.util.Objects;

public class NewPassword {
	private String currentPassword;
	private String newPassword;
	private String repeatNewPassword;

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return repeatNewPassword;
	}

	public void setRepeatNewPassword(String repeatnewPassword) {
		this.repeatNewPassword = repeatnewPassword;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(currentPassword, newPassword, repeatNewPassword);
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
		
		NewPassword other = (NewPassword) obj;
		return Objects.equals(this.currentPassword, other.currentPassword) && Objects.equals(this.newPassword, other.newPassword)
				&& Objects.equals(this.repeatNewPassword, repeatNewPassword);
	}
}
