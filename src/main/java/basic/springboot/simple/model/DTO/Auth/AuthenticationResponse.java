package basic.springboot.simple.model.DTO.Auth;

public class AuthenticationResponse {
	private final String accessToken;

	 private final String refreshToken;

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}

	/*public AuthenticationResponse(String accessToken){
		super();
		this.accessToken = accessToken;
	}*/

	public AuthenticationResponse(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
