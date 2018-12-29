package pl.bookshop.mvc.objects;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import pl.bookshop.mvc.validation.validators.DifferentFields;
import pl.bookshop.mvc.validation.validators.ValidPassword;

@DifferentFields(firstField = "currentPassword", secondField = "newPassword", message = "{newPassword.differentFields}")
public class NewPassword {
	@NotNull(message = "{newPassword.currentPassword.notNull}")
	@Size(min = 4, max = 30, message = "{newPassword.currentPassword.size}")
	@ValidPassword(message = "{newPassword.currentPassword.validPassword}")
	private String currentPassword;
	@NotNull(message = "{newPassword.newPassword.notNull}")
	@Size(min = 4, max = 30, message = "{newPassword.newPassword.size}")
	private String newPassword;

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

	@Override
	public String toString() {
		return "NewPassword [currentPassword=" + currentPassword + ", newPassword=" + newPassword + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(currentPassword, newPassword);
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
		return Objects.equals(this.currentPassword, other.currentPassword) && Objects.equals(this.newPassword, other.newPassword);
	}
}
