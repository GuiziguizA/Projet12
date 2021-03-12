package sid.org.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Competence;
import sid.org.dto.CompetenceDto;

public interface CompetenceService {

	public Competence getCompetence(Long id, HttpServletRequest request) throws HttpStatusCodeException;

	public Page<Competence> searchCompetence(Optional<String> recherche, int size, int page, HttpServletRequest request)
			throws HttpStatusCodeException;

	public void createComp(CompetenceDto competenceDto, Long idUser, HttpServletRequest request)
			throws HttpStatusCodeException;

	public Page<Competence> getCompetencesUser(String nom, int size, int page, HttpServletRequest request)
			throws HttpStatusCodeException;

	public void deleteComp(Long id, HttpServletRequest request) throws HttpStatusCodeException;

}
