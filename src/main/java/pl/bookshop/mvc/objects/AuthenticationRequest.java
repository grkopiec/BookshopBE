package pl.bookshop.mvc.objects;

import java.util.Objects;

public class AuthenticationRequest {
    private String username;
    private String password;

    public AuthenticationRequest() {}
    
    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "AuthenticationRequest [username=" + username + ", password=" + password + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(username, password);
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
		
		AuthenticationRequest other = (AuthenticationRequest) obj;
		return Objects.equals(this.username, other.username) && Objects.equals(this.password, other.password);
	}
}
