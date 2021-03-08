package sid.org.service;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.ServiceFrontEndApplication;
import sid.org.Page.RestResponsePage;
import sid.org.classe.Requete;
import sid.org.config.RequestFactory;
import sid.org.dto.RequeteDto;

@Service
public class RequeteServiceimpl implements RequeteService {
	@Value("${api.url}")
	private String url;
	private static final Logger logger = LoggerFactory.getLogger(ServiceFrontEndApplication.class);

	private final RequestFactory requestFactory;

	@Autowired
	public RequeteServiceimpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Autowired
	HeadersService headersService;

	@Override
	public void createRequete(Long idComp, Long idUser, HttpServletRequest request) throws HttpStatusCodeException {

		String uri = url + "/compagny-creneaux_requetes/requete/";

		HttpHeaders headers = headersService.createTokenHeaders(request);

		RestTemplate rt = new RestTemplate();

		RequeteDto requeteDto = new RequeteDto();
		requeteDto.setIdComp(idComp);
		logger.info(requeteDto.getIdComp().toString());

		requeteDto.setIdUser(idUser);
		logger.info(requeteDto.getIdUser().toString());
		requeteDto.setStatut("demande");
		rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(requeteDto, headers), Requete.class);

	}

	@Override
	public void validerRequete(Long idRequete, Long idUser, HttpServletRequest request) throws HttpStatusCodeException {
		logger.info(idRequete.toString() + idUser.toString());
		String uri = url + "/compagny-creneaux_requetes/validateRequete?idRequete=" + idRequete + "&idUser1=" + idUser;
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		ResponseEntity<Long> requete = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), Long.class);

	}

	@Override
	public Page<Requete> getRequetes(Long idUserComp, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		ParameterizedTypeReference<RestResponsePage<Requete>> responseType = new ParameterizedTypeReference<RestResponsePage<Requete>>() {
		};
		String uri = url + "/compagny-creneaux_requetes/requetes?idUserComp=" + idUserComp + "&page=" + page + "&size="
				+ size;

		ResponseEntity<RestResponsePage<Requete>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Requete> requetecompetencePage = result.getBody();
		return requetecompetencePage;
	}

	@Override
	public Page<Requete> getRequetesU(Long idUser, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		ParameterizedTypeReference<RestResponsePage<Requete>> responseType = new ParameterizedTypeReference<RestResponsePage<Requete>>() {
		};
		String uri = url + "/compagny-creneaux_requetes/requetesU?idUser=" + idUser + "&page=" + page + "&size=" + size;

		ResponseEntity<RestResponsePage<Requete>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Requete> requetecompetencePage = result.getBody();
		return requetecompetencePage;
	}

}
