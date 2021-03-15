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

	public Creneau createCreneau(CreneauDto creneauDto, Long idRequete, Long idUserDemande)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException,
			ResultNotFoundException;

	public void updateCreneau(Long id, Long idUser) throws ResultNotFoundException, ForbiddenException;

	public Creneau getCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException;

	public Page<Creneau> getCreneauxUser(Long idUser);

	public Page<Creneau> getCreneauxUser1(Long idUser1);

	public Creneau getCreneau(Long id) throws ResultNotFoundException;

	public void deleteCreneau(Long id, Long idUser, Long idUser1) throws ResultNotFoundException;

	public Page<Creneau> getlistCreneauxComp(Competence comp, Long idUser) throws ForbiddenException;

}
