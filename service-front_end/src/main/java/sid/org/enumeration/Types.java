package sid.org.enumeration;

public enum Types {

	Types(""), Types1("mécanique"), Types2("cuisine"), Types3("sport"), Types4("activité intellectuelle"),
	Types5("langues vivantes"), Types6("relaxation"), Types7("activité manuel"), Types8("autres");

	private final String nom;

	Types(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

}