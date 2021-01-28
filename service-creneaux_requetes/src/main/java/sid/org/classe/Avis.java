package sid.org.classe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Avis {
	@Id
	@GeneratedValue
	private Long avisId;
	private Long idUser;
	private int note;
	private String commentaire;
	@ManyToOne
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

	public Long getAvisId() {
		return avisId;
	}

	public void setAvisId(Long avisId) {
		this.avisId = avisId;
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

}
