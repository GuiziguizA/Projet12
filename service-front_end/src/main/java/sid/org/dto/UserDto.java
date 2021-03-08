package sid.org.dto;

import javax.validation.constraints.NotBlank;

import sid.org.classe.Roles;

public class UserDto {

	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String nom;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String mail;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String adresse;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String motDePasse;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String codePostal;
	private Roles roles;

	public UserDto(String nom, String mail, String adresse, String motDePasse, String codePostal) {
		super();
		this.nom = nom;
		this.mail = mail;
		this.adresse = adresse;
		this.motDePasse = motDePasse;
		this.codePostal = codePostal;

	}

	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
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

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

}
