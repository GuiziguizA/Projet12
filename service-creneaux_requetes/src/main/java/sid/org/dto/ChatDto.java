package sid.org.dto;

public class ChatDto {

	private Long idUser;
	private Long idUser1;
	private Long idComp;

	public ChatDto(Long idUser, Long idUser1, Long idComp) {
		super();
		this.idUser = idUser;
		this.idUser1 = idUser1;
		this.setIdComp(idComp);
	}

	public ChatDto() {
		super();
		// TODO Auto-generated constructor stub
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

}
