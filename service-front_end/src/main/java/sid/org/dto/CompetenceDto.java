package sid.org.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import sid.org.classe.Users;

public class CompetenceDto {
	@Valid
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String nom;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String type;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String description;

	private Users user;

	public CompetenceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompetenceDto(@NotBlank(message = "le champ nom ne peut pas etre vide") String nom,
			@NotBlank(message = "le champ nom ne peut pas etre vide") String type,
			@NotBlank(message = "le champ nom ne peut pas etre vide") String description, Users user) {
		super();
		this.nom = nom;
		this.type = type;
		this.description = description;
		this.user = user;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

}
