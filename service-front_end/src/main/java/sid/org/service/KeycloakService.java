package sid.org.service;

public interface KeycloakService {

	public String RecupTokenAdmin(String username, String password, String clientId);

	public void createUserKeycloak(String name, String mail, String password);

	public String UserGetId(String mail);

	String RecupTokenClient(String username, String password, String clientId);

}
