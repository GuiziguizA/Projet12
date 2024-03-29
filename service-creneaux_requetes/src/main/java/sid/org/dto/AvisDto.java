package sid.org.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sid.org.classe.Creneau;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class AvisDto {

	private int note;
	private Long idUser;
	private Long idComp;
	private String commentaire;
	private Creneau creneau;

}
