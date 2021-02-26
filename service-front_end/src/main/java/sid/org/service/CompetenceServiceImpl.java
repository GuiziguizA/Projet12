package sid.org.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	KeycloakService kc;

	@Autowired
	public CompetenceServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Autowired
	KeycloakRestTemplate keycloakRestTemplate;

	@Override
	public Page<Competence> searchCompetence(Optional<String> recherche, int size, int page) {
		String tok = kc.RecupTokenClient("guiliom", "tarot1994", "service-front_end");
		RestTemplate rt = requestFactory.getRestTemplate();
		AccessToken accessToken = getAccessToken();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + tok);
		ParameterizedTypeReference<RestResponsePage<Competence>> responseType = new ParameterizedTypeReference<RestResponsePage<Competence>>() {
		};
		final String uri = url + "/compagny-user_competence/search?page=" + page + "&size=" + size;
		final String url1 = "http://localhost:8082/search?page=0&size=2";
		CompetenceCriteria critere = new CompetenceCriteria(null, recherche.get(), null);
		logger.info(critere.getType());
		/*
		 * ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(uri,
		 * HttpMethod.GET, new HttpEntity<>(critere), responseType);
		 */
		ObjectMapper mapper = new ObjectMapper();
		mapper.convertValue(critere, CompetenceCriteria.class);
		String registrationBody = mapper.toString();
		logger.info(registrationBody.toString());
		ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(url1, HttpMethod.GET,
				new HttpEntity<Object>(critere, headers), responseType);

		Page<Competence> competencePage = result.getBody();

		return competencePage;

	}

	@PreAuthorize("isAuthenticated ()")
	@Override
	public Page<Competence> getCompetencesUser(Long idUser, int size, int page) {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate rt = requestFactory.getRestTemplate();
		logger.info(getAccessToken().toString() + "yollllllllllllllllllllllllllllllllllllllllllllllllllll");

		ParameterizedTypeReference<RestResponsePage<Competence>> responseType = new ParameterizedTypeReference<RestResponsePage<Competence>>() {
		};
		final String uri = url + "/compagny-user_competence/competences?idUser=" + idUser + "&page=" + page + "&size="
				+ size;

		/*
		 * ResponseEntity<RestResponsePage<Competence>> result = rt.exchange(uri,
		 * HttpMethod.GET, new HttpEntity<>(headers), responseType);
		 */
		ResponseEntity<RestResponsePage<Competence>> result = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null,
				responseType);

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

		ResponseEntity<Competence> competence = keycloakRestTemplate.exchange(uri, HttpMethod.GET, null,
				Competence.class);

		Competence comp = competence.getBody();
		return comp;

	}

	@Override
	public void createComp(CompetenceDto competenceDto, Long idUser) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-user_competence/competence";
		RestTemplate rt = new RestTemplate();
		AccessToken accessToken = getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);

		try {

			Users user = new Users();
			user.setCodeUtilisateur(idUser);
			competenceDto.setUser(user);
			ObjectMapper mapper = new ObjectMapper();
			mapper.convertValue(competenceDto, CompetenceDto.class);
			String registrationBody = mapper.toString();
			logger.info(registrationBody);
			ResponseEntity<Competence> result = rt.exchange(uri, HttpMethod.POST,
					new HttpEntity<String>(registrationBody, headers), Competence.class);

		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(e.getMessage());
		}

	}

	@Bean
	@Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public AccessToken getAccessToken() {

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		@SuppressWarnings("rawtypes")
		KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
		logger.info(token.toString());
		KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
		logger.info(principal.toString());
		KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
		AccessToken accessToken = session.getToken();

		return accessToken;
	}

}