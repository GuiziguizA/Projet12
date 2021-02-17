package sid.org.service;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Requete;
import sid.org.dto.RequeteDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

public interface RequeteService {

	public void deleteRequete(Long id, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException;

	public Requete createRequete(RequeteDto requeteDto)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException;

	public Requete getRequete(Long id, Long idUser)
			throws ResultNotFoundException, ForbiddenException, APiUSerAndCompetenceException;

	Page<Requete> getRequetes(Long idUserComp, int page, int size) throws APiUSerAndCompetenceException;

	public void validerUneRequete(Long id, Long idUser1) throws ResultNotFoundException, EntityAlreadyExistException,
			HttpStatusCodeException, APiUSerAndCompetenceException, ForbiddenException;

}
