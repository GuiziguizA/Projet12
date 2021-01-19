package sid.org.dto;

import java.util.Date;

public class RequeteDto {
	private Date date;
	private Long idUser;
	private Long idComp;
	private String statut;

	public RequeteDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RequeteDto(Date date, Long idUser, Long idComp, String statut) {
		super();

		this.date = date;
		this.idUser = idUser;
		this.idComp = idComp;
		this.statut = statut;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdComp() {
		return idComp;
	}

	public void setIdComp(Long idComp) {
		this.idComp = idComp;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}
