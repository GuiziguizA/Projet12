package sid.org.dto;

public class NotesDto {

	private int moyenne;
	private int nombreDAvis;

	public NotesDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NotesDto(int moyenne, int nombreDAvis) {
		super();
		this.moyenne = moyenne;
		this.nombreDAvis = nombreDAvis;
	}

	public int getMoyenne() {
		return moyenne;
	}

	public void setMoyenne(int moyenne) {
		this.moyenne = moyenne;
	}

	public int getNombreDAvis() {
		return nombreDAvis;
	}

	public void setNombreDAvis(int nombreDAvis) {
		this.nombreDAvis = nombreDAvis;
	}

}
