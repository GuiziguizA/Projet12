package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Avis;
import sid.org.dto.AvisDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

public interface AvisService {

	public Avis createAvis(AvisDto avisDto, Long idUser)
			throws ResultNotFoundException, ForbiddenException, EntityAlreadyExistException;

	public Page<Avis> getAvis(Long idComp, int page, int size) throws ResultNotFoundException, ForbiddenException;

}
