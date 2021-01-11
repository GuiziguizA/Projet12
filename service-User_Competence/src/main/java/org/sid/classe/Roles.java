package org.sid.classe;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Roles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeRole;

	private String nom;
	@OneToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Collection<Users> users;

	public Roles() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Roles(String nom) {
		super();
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getCodeRole() {
		return codeRole;
	}

}