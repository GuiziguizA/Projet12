package sid.org.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import sid.org.classe.Competence;
import sid.org.dto.CompetenceDto;
import sid.org.exception.APiUSerAndCompetenceException;

public interface CompetenceService {

	public Competence getCompetence(Long id, String username, String password);

	public Page<Competence> searchCompetence(Optional<String> recherche, int size, int page, String username,
			String password);

	public void createComp(CompetenceDto competenceDto, Long idUser, String username, String password)
			throws APiUSerAndCompetenceException;

	public Page<Competence> getCompetencesUser(Long idUser, int size, int page, String username, String password);

}
