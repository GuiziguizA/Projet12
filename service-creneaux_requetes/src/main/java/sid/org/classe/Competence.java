package sid.org.classe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Competence {

	private Long codeCompetence;
	private String nom;
	private String type;
	private String description;

	private Users user;

}
