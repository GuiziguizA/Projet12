package sid.org.dto;

public class ChatDto {

	private Long idUser;
	private Long idUser1;
	private Long idComp;
	private Long idRequete;
	private String username;
	private String competenceNom;

	public ChatDto(Long idUser, Long idUser1, Long idComp, Long idRequete) {
		super();
		this.idUser = idUser;
		this.idUser1 = idUser1;
		this.idComp = idComp;
		this.idRequete = idRequete;
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

	public Long getIdRequete() {
		return idRequete;
	}

	public void setIdRequete(Long idRequete) {
		this.idRequete = idRequete;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCompetenceNom() {
		return competenceNom;
	}

	public void setCompetenceNom(String competenceNom) {
		this.competenceNom = competenceNom;
	}

}
