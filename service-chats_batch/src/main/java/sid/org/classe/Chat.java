package sid.org.classe;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Chat {
	@Id
	@GeneratedValue
	private Long id;
	private String statut;
	private Long idUser;
	private Long idUser1;
	private Collection<Message> messages;

	public Chat(Long id, String statut, Long idUser, Long idUser1) {
		super();
		this.id = id;
		this.statut = statut;
		this.idUser = idUser;
		this.idUser1 = idUser1;
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
