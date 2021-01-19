package sid.org.classe;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Requete {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeCreneau;
	private Date date;
	private Long idUser;
	private Long idComp;
	private String statut;

	public Requete() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Requete(Long codeCreneau, Date date, Long idUser, Long idComp, String statut) {
		super();
		this.codeCreneau = codeCreneau;
		this.date = date;
		this.idUser = idUser;
		this.idComp = idComp;
		this.statut = statut;
	}

	public Long getCodeCreneau() {
		return codeCreneau;
	}

	public void setCodeCreneau(Long codeCreneau) {
		this.codeCreneau = codeCreneau;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
