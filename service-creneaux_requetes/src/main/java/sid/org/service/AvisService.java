package sid.org.service;

import java.util.List;

import sid.org.classe.Avis;
import sid.org.classe.Creneau;
import sid.org.dto.AvisDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

public interface AvisService {

	public Avis createAvis(AvisDto avisDto, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException;

	public List<Avis> getlistAvis(Creneau creneau, Long idUser) throws ResultNotFoundException, ForbiddenException;

	public Avis getAvis(Long id, Long idUser) throws ResultNotFoundException, ForbiddenException;

	public void deleteAvis(Long id, Long Userid) throws ResultNotFoundException, ForbiddenException;

}
