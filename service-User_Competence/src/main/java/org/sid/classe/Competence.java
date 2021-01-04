package org.sid.classe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
public class Competence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeCompetence;
	private String nom;
	private String type;
	private String description;
	@ManyToOne
	
	@JoinColumn(name = "ID_USER")
	private User user;
	public Competence(Long codeCompetence, String nom, String type, String description, User user) {
		super();
		this.codeCompetence = codeCompetence;
		this.nom = nom;
		this.type = type;
		this.description = description;
		this.user = user;
	}
	public Competence() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getCodeCompetence() {
		return codeCompetence;
	}
	public void setCodeCompetence(Long codeCompetence) {
		this.codeCompetence = codeCompetence;
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
