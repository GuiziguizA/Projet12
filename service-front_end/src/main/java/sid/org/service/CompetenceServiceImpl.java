package sid.org.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import sid.org.ServiceFrontEndApplication;
import sid.org.Page.RestResponsePage;
import sid.org.classe.Competence;
import sid.org.classe.CompetenceCriteria;
import sid.org.config.RequestFactory;
import sid.org.dto.CompetenceDto;

@Component

public class CompetenceServiceImpl implements CompetenceService {
	@Value("${api.url}")
	private String url;

	private static final Logger logger = LoggerFactory.getLogger(ServiceFrontEndApplication.class);

	private final RequestFactory requestFactory;

	@Autowired
	HeadersService headersService;

	@Autowired
	public CompetenceServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public Page<Competence> searchCompetence(Optional<String> recherche, int size, int page, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		ParameterizedTypeReference<RestResponsePage<Competence>> responseType = new ParameterizedTypeReference<RestResponsePage<Competence>>() {
		};
		final String uri = url + "/compagny-user_competence/search?page=" + page + "&size=" + size;

		CompetenceCriteria critere = new CompetenceCriteria(null, recherche.get(), null);
		logger.info(critere.getType());
		ObjectMapper mapper = new ObjectMapper();
		mapper.convertValue(critere, CompetenceCriteria.class);
		String registrationBody = mapper.toString();
		logger.info(registrationBody.toString());

		ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(uri, HttpMethod.GET,
				new HttpEntity<Object>(critere, headers), responseType);

		Page<Competence> competencePage = result.getBody();
		return competencePage;

	}

	@PreAuthorize("isAuthenticated ()")
	@Override
	public Page<Competence> getCompetencesUser(String nom, int size, int page, HttpServletRequest request)
			throws HttpStatusCodeException {
		HttpHeaders headers = headersService.createTokenHeaders(request);
		RestTemplate rt = requestFactory.getRestTemplate();

		ParameterizedTypeReference<RestResponsePage<Competence>> responseType = new ParameterizedTypeReference<RestResponsePage<Competence>>() {
		};
		final String uri = url + "/compagny-user_competence/competences?nom=" + nom + "&page=" + page + "&size=" + size;
		ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(uri, HttpMethod.GET,
				new HttpEntity<Object>(headers), responseType);

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
	public Competence getCompetence(Long id, HttpServletRequest request) throws HttpStatusCodeException {
		HttpHeaders headers = headersService.createTokenHeaders(request);
		RestTemplate rt = requestFactory.getRestTemplate();

		final String uri = url + "/compagny-user_competence/competence/" + id;

		ResponseEntity<Competence> competence = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				Competence.class);
		Competence comp = competence.getBody();
		return comp;

	}

	@Override
	public void createComp(CompetenceDto competenceDto, Long idUser, HttpServletRequest request)
			throws HttpStatusCodeException {
		String uri = url + "/compagny-user_competence/competence";
		RestTemplate rt = new RestTemplate();

		HttpHeaders headers = headersService.createTokenHeaders(request);
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");

		competenceDto.setNomUser(name);
		rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(competenceDto, headers), Competence.class);

	}

}