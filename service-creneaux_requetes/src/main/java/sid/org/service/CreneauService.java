package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Competence;
import sid.org.classe.Creneau;
import sid.org.dto.CreneauDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

public interface CreneauService {

	public Creneau createCreneau(CreneauDto creneauDto, Long idRequete) throws EntityAlreadyExistException,
			APiUSerAndCompetenceException, ForbiddenException, ResultNotFoundException;

	public Creneau getCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException;

	public Page<Creneau> getCreneauxUser(Long idUser) throws APiUSerAndCompetenceException;

	public void deleteCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException;

	public Page<Creneau> getlistCreneauxComp(Competence comp, Long idUser)
			throws ResultNotFoundException, ForbiddenException;

	public void updateCreneau(Long id, Long idUser) throws ResultNotFoundException, ForbiddenException;

	public Page<Creneau> getCreneauxUser1(Long idUser1) throws APiUSerAndCompetenceException;

}
