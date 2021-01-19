package org.sid.classe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Avis {
	@Id
	@GeneratedValue
	private Long idAvis;
	private String description;
	@ManyToOne

	@JoinColumn(name = "ID_COMPETENCE")
	private Competence competence;

	public Avis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Avis(Long idAvis, String description, Competence competence) {
		super();
		this.idAvis = idAvis;
		this.description = description;
		this.competence = competence;
	}

	public Long getIdAvis() {
		return idAvis;
	}

	public void setIdAvis(Long idAvis) {
		this.idAvis = idAvis;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Competence getCompetence() {
		return competence;
	}

	public void setCompetence(Competence competence) {
		this.competence = competence;
	}

}
