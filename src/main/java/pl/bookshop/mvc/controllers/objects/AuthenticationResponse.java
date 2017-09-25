package pl.bookshop.mvc.controllers.objects;

public class AuthenticationResponse {
	private String token;
	
	public AuthenticationResponse() {}

	public AuthenticationResponse(String token) {
		this.setToken(token);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
