package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Requete;
import sid.org.exception.APiUSerAndCompetenceException;

public interface RequeteService {

	public Page<Requete> getRequetes(Long idUserComp, int page, int size, String username, String password)
			throws APiUSerAndCompetenceException;

	public void validerRequete(Long idRequete, Long idUser, String username, String password)
			throws APiUSerAndCompetenceException;

	public void createRequete(Long idComp, Long idUser, String username, String password)
			throws APiUSerAndCompetenceException;

}
