package sid.org.classe;

import lombok.Data;

@Data
public class Users {

	private Long codeUtilisateur;
	private String username;

	private String mail;
	private String adresse;
	private String password;

	private String codePostal;

}
