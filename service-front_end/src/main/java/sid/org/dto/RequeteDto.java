package sid.org.dto;

public class RequeteDto {

	private Long idUser;
	private Long idComp;
	private String statut;

	public RequeteDto(Long idUser, Long idComp) {
		super();
		this.idUser = idUser;
		this.idComp = idComp;
	}

	public RequeteDto() {
		super();
		// TODO Auto-generated constructor stub
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
