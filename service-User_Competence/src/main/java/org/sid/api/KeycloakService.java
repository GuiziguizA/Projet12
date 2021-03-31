package org.sid.api;

public interface KeycloakService {

	public String RecupTokenAdmin(String username, String password, String clientId);

	public void createUserKeycloak(String name, String mail, String password);

	public String UserGetId(String mail);

}
