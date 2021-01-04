package org.sid.classe;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeUtilisateur;
	private String nom;
	@Column(unique = true)
	private String mail;
	private String adresse;
	private String motDePasse;
	@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres")
	private String codePostal;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
	private Collection<Competence> competences;
	@ManyToOne
	@JoinColumn(name = "ID_ROLES")
	private Roles roles;
	
	public User(Long codeUtilisateur, String nom, String mail, String adresse, String motDePasse,
			@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres") String codePostal,
			Collection<Competence> competences, Roles roles) {
		super();
		this.codeUtilisateur = codeUtilisateur;
		this.nom = nom;
		this.mail = mail;
		this.adresse = adresse;
		this.motDePasse = motDePasse;
		this.codePostal = codePostal;
		this.competences = competences;
		this.roles = roles;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getCodeUtilisateur() {
		return codeUtilisateur;
	}

	public void setCodeUtilisateur(Long codeUtilisateur) {
		this.codeUtilisateur = codeUtilisateur;
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
