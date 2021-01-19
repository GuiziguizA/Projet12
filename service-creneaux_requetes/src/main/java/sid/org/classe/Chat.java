package sid.org.classe;

public class Chat {

	private String statut;
	private Long idUser;
	private Long idUser1;

	public Chat(String statut, Long idUser, Long idUser1) {
		super();
		this.statut = statut;
		this.idUser = idUser;
		this.idUser1 = idUser1;
	}

	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
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
