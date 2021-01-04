package org.sid.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.sid.classe.Roles;




public class UserDto {

	private String nom;
	@Column(unique = true)
	private String mail;
	private String adresse;
	private String motDePasse;
	@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres")
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
