package org.sid.specification;

public class CompetenceCriteria {

	private String nom;
	private String type;
	private String description;
	private int note;

	public CompetenceCriteria(String nom, String description, String type) {
		super();
		this.type = type;
		this.nom = nom;
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

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}
}