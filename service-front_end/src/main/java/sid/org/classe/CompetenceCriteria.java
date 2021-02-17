package sid.org.classe;

public class CompetenceCriteria {

	private String nom;
	private String type;
	private String description;

	public CompetenceCriteria(String nom, String type, String description) {
		super();
		this.nom = nom;
		this.type = type;
		this.description = description;
	}

	public CompetenceCriteria() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}