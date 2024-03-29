package sid.org.classe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Avis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long aviId;
	private Long idUser;
	private String username;
	private Long idComp;
	private int note;
	private String commentaire;
	@OneToOne
	@JoinColumn(name = "ID_CRENEAU")
	private Creneau creneau;

	public Avis() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Avis(int note, String commentaire, Creneau creneau) {
		super();

		this.note = note;
		this.commentaire = commentaire;
		this.creneau = creneau;
	}

	public Long getAviId() {
		return aviId;
	}

	public void setAviId(Long aviId) {
		this.aviId = aviId;
	}

	public int getNote() {
		return note;
	}

	public void setNote(int note) {
		this.note = note;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
