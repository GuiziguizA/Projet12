package sid.org.classe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

	private Long codeUtilisateur;
	private String username;

	private String mail;
	private String adresse;
	private String password;

	private String codePostal;

}
