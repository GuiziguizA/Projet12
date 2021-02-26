package sid.org.classe;

import java.util.Collection;

import org.apache.logging.log4j.message.Message;

public class Chat {
	private Long id;
	private String statut;
	private Long idRequete;
	private Long idUser;
	private Long idUser1;
	private Long idComp;
	private Collection<Message> messages;
	private String username;
	private String username1;
	private String competenceName;

	public Chat(Long id, String statut, Long idRequete, Long idUser, Long idUser1, Long idComp) {
		super();
		this.id = id;
		this.statut = statut;
		this.idRequete = idRequete;
		this.idUser = idUser;
		this.idUser1 = idUser1;
		this.idComp = idComp;

	}

	public Chat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Long getIdRequete() {
		return idRequete;
	}

	public void setIdRequete(Long idRequete) {
		this.idRequete = idRequete;
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

	public Collection<Message> getMessages() {
		return messages;
	}

	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername1() {
		return username1;
	}

	public void setUsername1(String username1) {
		this.username1 = username1;
	}

	public String getCompetenceName() {
		return competenceName;
	}

	public void setCompetenceName(String competenceName) {
		this.competenceName = competenceName;
	}

}
