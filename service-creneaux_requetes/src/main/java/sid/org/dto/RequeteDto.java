package sid.org.dto;

import java.util.Date;

import lombok.Data;

@Data
public class RequeteDto {
	private Date date;
	private Long idUser;
	private Long idComp;
	private String statut;

	public RequeteDto(Long idUser, Long idComp, String statut) {
		super();
		this.idUser = idUser;
		this.idComp = idComp;
		this.statut = statut;
	}

}