package sid.org.classe;

import java.util.Collection;

public class Users {

	private Long codeUtilisateur;
	private String username;

	private String mail;
	private String adresse;
	private String password;

	private String codePostal;

	private Collection<Competence> competences;

	private Roles roles;

	public Users(Long codeUtilisateur, String username, String mail, String adresse, String password, String codePostal,
			Collection<Competence> competences, Roles roles) {
		super();
		this.codeUtilisateur = codeUtilisateur;
		this.username = username;
		this.mail = mail;
		this.adresse = adresse;
		this.password = password;
		this.codePostal = codePostal;
		this.competences = competences;
		this.roles = roles;
	}

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCodeUtilisateur() {
		return codeUtilisateur;
	}

	public void setCodeUtilisateur(Long codeUtilisateur) {
		this.codeUtilisateur = codeUtilisateur;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Collection<Competence> getCompetences() {
		return competences;
	}

	public void setCompetences(Collection<Competence> competences) {
		this.competences = competences;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

}
