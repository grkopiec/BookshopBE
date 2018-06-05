package pl.bookshop.mvc.objects;

import java.util.Objects;

public class NewPassword {
	private String oldPassword;
	private String newPassword;
	private String newPasswordRepeat;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordRepeat() {
		return newPasswordRepeat;
	}

	public void setNewPasswordRepeat(String newPasswordRepeat) {
		this.newPasswordRepeat = newPasswordRepeat;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(oldPassword, newPassword, newPasswordRepeat);
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
		return Objects.equals(this.oldPassword, other.oldPassword) && Objects.equals(this.newPassword, other.newPassword)
				&& Objects.equals(this.newPasswordRepeat, newPasswordRepeat);
	}
}
