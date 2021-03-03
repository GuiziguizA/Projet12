package sid.org.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import sid.org.classe.Competence;
import sid.org.dto.CompetenceDto;
import sid.org.exception.APiUSerAndCompetenceException;

public interface CompetenceService {

	public Competence getCompetence(Long id, String username, String password) throws APiUSerAndCompetenceException;

	public Page<Competence> searchCompetence(Optional<String> recherche, int size, int page, String username,
			String password) throws APiUSerAndCompetenceException;

	public void createComp(CompetenceDto competenceDto, Long idUser, String username, String password)
			throws APiUSerAndCompetenceException;

	public Page<Competence> getCompetencesUser(String nom, int size, int page, String username, String password);

}
