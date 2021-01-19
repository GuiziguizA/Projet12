package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Requete;
import sid.org.dto.RequeteDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;

public interface RequeteService {

	public void deleteRequete(Long id) throws ResultNotFoundException;

	public Requete createRequete(RequeteDto requeteDto) throws EntityAlreadyExistException;

	public Requete getRequete(Long id) throws ResultNotFoundException;

	public Page<Requete> getRequetes(Long idUser);

}
