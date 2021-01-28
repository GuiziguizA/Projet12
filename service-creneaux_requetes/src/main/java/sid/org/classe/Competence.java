package sid.org.classe;

import lombok.Data;

@Data
public class Competence {

	private Long codeCompetence;
	private String nom;
	private String type;
	private String description;

	private Users user;

}
