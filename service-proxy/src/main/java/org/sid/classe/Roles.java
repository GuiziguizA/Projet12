package org.sid.classe;

public class Roles {

	private Long codeRole;

	private String nom;

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