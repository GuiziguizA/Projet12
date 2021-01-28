package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Creneau;
import sid.org.dto.CreneauDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

public interface CreneauService {

	public Creneau createCreneau(CreneauDto creneauDto, Long idUser, Long idUser1)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException;

	public Creneau getCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException;

	public Page<Creneau> getCreneaux(Long idUser, Long idUser1) throws APiUSerAndCompetenceException;

	public void deleteCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException;

}
