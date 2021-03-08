package sid.org.service;

import org.springframework.web.client.HttpStatusCodeException;

public interface KeycloakService {

	public String RecupTokenAdmin(String username, String password, String clientId) throws HttpStatusCodeException;

	public void createUserKeycloak(String name, String mail, String password) throws HttpStatusCodeException;

	public String UserGetId(String mail) throws HttpStatusCodeException;

	String RecupTokenClient(String username, String password, String clientId) throws HttpStatusCodeException;

}
