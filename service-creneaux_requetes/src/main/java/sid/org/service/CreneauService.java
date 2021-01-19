package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Creneau;
import sid.org.dto.CreneauDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;

public interface CreneauService {

	public Creneau createCreneau(CreneauDto creneauDto) throws EntityAlreadyExistException;

	public Creneau getCreneau(Long id) throws ResultNotFoundException;

	public Page<Creneau> getCreneaux(Long idUser);

	public void deleteCreneau(Long id) throws ResultNotFoundException;

}
