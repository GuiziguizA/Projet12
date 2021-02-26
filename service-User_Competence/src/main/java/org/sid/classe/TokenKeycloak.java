package org.sid.classe;

import lombok.Data;

@Data
public class TokenKeycloak {

	private String access_token;
	private String expire_in;
	private String refresh_expires_in;
	private String refresh_token;
	private String token_type;

	private String session_state;
	private String scope;

}
