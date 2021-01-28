package sid.org.classe;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Creneau {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeCreneau;
	private Date date;
	private Long idUser;
	private Long idUser1;
	private Long idComp;
	private String statut;
	@OneToMany(mappedBy = "creneau", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private Collection<Avis> avis;

	public Creneau(Date date, Long idUser, Long idUser1, Long idComp) {
		super();
		this.date = date;
		this.idUser = idUser;
		this.idUser1 = idUser1;
		this.idComp = idComp;
	}

	public Creneau() {
		super();
		// TODO Auto-generated constructor stub
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

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

}
