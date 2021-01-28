package sid.org.api;

import sid.org.classe.Competence;
import sid.org.exception.APiUSerAndCompetenceException;

public interface CompetenceApi {

	public Competence getCompetence(Long idUser) throws APiUSerAndCompetenceException;

}
