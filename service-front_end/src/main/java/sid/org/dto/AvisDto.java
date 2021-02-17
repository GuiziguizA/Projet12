package sid.org.dto;

import sid.org.classe.Competence;

public class AvisDto {

	private Long idAvis;
	private String description;
	private Competence competence;

	public AvisDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AvisDto(Long idAvis, String description, Competence competence) {
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
