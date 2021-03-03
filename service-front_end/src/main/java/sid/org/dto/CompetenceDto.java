package sid.org.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class CompetenceDto {
	@Valid
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String nom;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String type;
	@NotBlank(message = "le champ nom ne peut pas etre vide")
	private String description;

	private String nomUser;

	public CompetenceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompetenceDto(@Valid @NotBlank(message = "le champ nom ne peut pas etre vide") String nom,
			@NotBlank(message = "le champ nom ne peut pas etre vide") String type,
			@NotBlank(message = "le champ nom ne peut pas etre vide") String description, String nomUser) {
		super();
		this.nom = nom;
		this.type = type;
		this.description = description;
		this.nomUser = nomUser;
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

	public String getNomUser() {
		return nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

}