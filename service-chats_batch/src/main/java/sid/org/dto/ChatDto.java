package sid.org.dto;

public class ChatDto {

	private Long idUser;
	private Long idUser1;

	public ChatDto(Long idUser, Long idUser1) {
		super();
		this.idUser = idUser;
		this.idUser1 = idUser1;
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

}
