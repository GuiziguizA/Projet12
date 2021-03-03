package org.sid.dto;

public class CompetenceDto {

	private String nom;

	private String type;

	private String description;

	private String nomUser;

	public CompetenceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompetenceDto(String nom, String type, String description, String nomUser) {
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