package sid.org.api;

import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Competence;
import sid.org.exception.APiUSerAndCompetenceException;

public interface CompetenceApi {

	public Competence getCompetence(Long idcompetence) throws APiUSerAndCompetenceException, HttpStatusCodeException;

}
