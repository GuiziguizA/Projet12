package sid.org.dto;

import java.util.Date;

public class CreneauDto {

	private Long idUser;
	private Long idUser1;
	private Long idComp;
	private String statut;
	private Date date;

	public CreneauDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreneauDto(Long idUser, Long idUser1, Long idComp, String statut, Date date) {
		super();
		this.idUser = idUser;
		this.idUser1 = idUser1;
		this.idComp = idComp;
		this.statut = statut;
		this.date = date;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Long getIdUser1() {
		return idUser1;
	}

	public void setIdUser1(Long idUser1) {
		this.idUser1 = idUser1;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
