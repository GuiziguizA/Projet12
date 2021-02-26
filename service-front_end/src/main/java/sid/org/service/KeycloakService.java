package sid.org.service;

import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;

public interface KeycloakService {

	public String RecupTokenAdmin(String username, String password, String clientId);

	public void createUserKeycloak(String name, String mail, String password);

	public String UserGetId(String mail);

	void createUserKeycloak(String name, String mail, String password,
			KeycloakClientRequestFactory keycloakClientRequestFactory);

	String RecupTokenClient(String username, String password, String clientId);

}
