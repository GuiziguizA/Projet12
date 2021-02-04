package org.sid.dto;

import org.sid.classe.Users;

public class CompetenceDto {

	private String nom;
	private String type;
	private String description;

	private Users user;

	public CompetenceDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CompetenceDto(String nom, String type, String description, Users user) {
		super();
		this.nom = nom;
		this.type = type;
		this.description = description;

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
