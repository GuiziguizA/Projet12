package sid.org.dto;

public class DateDto {

	private String heure;
	private String jour;
	private String mois;
	private String annee;

	public DateDto(String heure, String jour, String mois, String annee) {
		super();
		this.heure = heure;
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}

	public DateDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String getAnnee() {
		return annee;
	}

	public void setAnnee(String annee) {
		this.annee = annee;
	}

}
