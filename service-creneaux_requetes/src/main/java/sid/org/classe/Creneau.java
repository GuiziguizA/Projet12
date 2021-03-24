package sid.org.classe;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Creneau {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeCreneau;
	private Date date;
	private Long idUser;
	private Long idUser1;
	private Long idUserDemande;
	private String UserDemandeName;
	private Long idUserRecoit;
	private String UserRecoitName;
	private Long idRequete;
	private Long idComp;
	private String competenceName;
	private Long idChat;
	private String statut;

	@OneToOne
	@JoinColumn(name = "id_avis")
	private Avis avis;

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

	public Long getIdUserDemande() {
		return idUserDemande;
	}

	public void setIdUserDemande(Long idUserDemande) {
		this.idUserDemande = idUserDemande;
	}

	public Long getIdChat() {
		return idChat;
	}

	public void setIdChat(Long idChat) {
		this.idChat = idChat;
	}

	public Long getIdUserRecoit() {
		return idUserRecoit;
	}

	public void setIdUserRecoit(Long idUserRecoit) {
		this.idUserRecoit = idUserRecoit;
	}

	public Long getIdRequete() {
		return idRequete;
	}

	public void setIdRequete(Long idRequete) {
		this.idRequete = idRequete;
	}

	public String getUserDemandeName() {
		return UserDemandeName;
	}

	public void setUserDemandeName(String userDemandeName) {
		UserDemandeName = userDemandeName;
	}

	public String getUserRecoitName() {
		return UserRecoitName;
	}

	public void setUserRecoitName(String userRecoitName) {
		UserRecoitName = userRecoitName;
	}

	public String getCompetenceName() {
		return competenceName;
	}

	public void setCompetenceName(String competenceName) {
		this.competenceName = competenceName;
	}

}
