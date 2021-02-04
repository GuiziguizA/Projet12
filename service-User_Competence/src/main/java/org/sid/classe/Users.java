package org.sid.classe;

import java.util.Collection;

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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeUtilisateur;
	private String username;
	@Column(unique = true)
	private String mail;
	private String adresse;
	private String password;
	@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres")
	private String codePostal;
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Collection<Competence> competences;
	@ManyToOne
	@JoinColumn(name = "ID_ROLES")
	private Roles roles;

	public Users(Long codeUtilisateur, String username, String mail, String adresse, String password,
			@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres") String codePostal,
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
