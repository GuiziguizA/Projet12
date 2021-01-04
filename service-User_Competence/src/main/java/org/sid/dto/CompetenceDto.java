package org.sid.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.sid.classe.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class CompetenceDto {

	private String nom;
	private String type;
	private String description;
	
	private User user;
	
	
	public CompetenceDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public CompetenceDto(String nom, String type, String description, User user) {
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


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	
}
