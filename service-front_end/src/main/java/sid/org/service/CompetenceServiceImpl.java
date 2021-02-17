package sid.org.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.ServiceFrontEndApplication;
import sid.org.Page.RestResponsePage;
import sid.org.classe.Competence;
import sid.org.classe.CompetenceCriteria;
import sid.org.classe.Users;
import sid.org.config.RequestFactory;
import sid.org.dto.CompetenceDto;
import sid.org.exception.APiUSerAndCompetenceException;

@Component
public class CompetenceServiceImpl implements CompetenceService {
	@Value("${api.url}")
	private String url;

	private static final Logger logger = LoggerFactory.getLogger(ServiceFrontEndApplication.class);

	private final RequestFactory requestFactory;

	@Autowired
	public CompetenceServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public Page<Competence> searchCompetence(Optional<String> recherche, int size, int page) {

		RestTemplate rt = requestFactory.getRestTemplate();

		ParameterizedTypeReference<RestResponsePage<Competence>> responseType = new ParameterizedTypeReference<RestResponsePage<Competence>>() {
		};
		final String uri = url + "/compagny-user_competence/search?page=" + page + "&size=" + size;
		CompetenceCriteria critere = new CompetenceCriteria(null, recherche.get(), null);
		logger.info(critere.getType());
		ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(uri, HttpMethod.GET,
				new HttpEntity<>(critere), responseType);
		Page<Competence> competencePage = result.getBody();
		return competencePage;

	}

	@Override
	public Page<Competence> getCompetencesUser(Long idUser, int size, int page) {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate rt = requestFactory.getRestTemplate();

		ParameterizedTypeReference<RestResponsePage<Competence>> responseType = new ParameterizedTypeReference<RestResponsePage<Competence>>() {
		};
		final String uri = url + "/compagny-user_competence/competences?idUser=" + idUser + "&page=" + page + "&size="
				+ size;

		ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(uri, HttpMethod.GET,
				new HttpEntity<>(headers), responseType);
		Page<Competence> competencePage = result.getBody();
		return competencePage;

	}

	public CompetenceCriteria critereImpl(Optional<String> type, Optional<String> recherche) {
		CompetenceCriteria critere = new CompetenceCriteria();

		if (type.get().equals("nom") && recherche != null) {
			critere.setNom(recherche.get());
		}
		if (type.get().equals("type") && recherche != null) {
			critere.setType(recherche.get());
		}

		return critere;

	}

	@Override
	public Competence getCompetence(Long id) {

		RestTemplate rt = requestFactory.getRestTemplate();

		final String uri = url + "/compagny-user_competence/competence/" + id;

		ResponseEntity<Competence> competence = rt.getForEntity(uri, Competence.class);

		Competence comp = competence.getBody();
		return comp;

	}

	@Override
	public void createComp(CompetenceDto competenceDto, Long idUser) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-user_competence/competence";

		RestTemplate rt = new RestTemplate();

		try {

			Users user = new Users();
			user.setCodeUtilisateur(idUser);
			competenceDto.setUser(user);
			ResponseEntity<Competence> comp = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(competenceDto),
					Competence.class);

		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(
					"l'api createUser ne marche pas et l'erreure est : " + e.getMessage());
		}

	}

}
