package sid.org.service;

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
import sid.org.classe.Chat;
import sid.org.classe.Creneau;
import sid.org.config.RequestFactory;
import sid.org.dto.CreneauDto;
import sid.org.dto.DateDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;

@Service
public class CreneauServiceImpl implements CreneauService {
	@Value("${api.url}")
	private String url;
	private static final Logger logger = LoggerFactory.getLogger(ServiceFrontEndApplication.class);

	@Autowired
	private HeadersService headersService;
	private final RequestFactory requestFactory;

	@Autowired
	public CreneauServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public void createCreneau(Chat chat, DateDto dateDto, String username, String password) throws ForbiddenException {

		String uri = url + "/compagny-creneaux_requetes/creneau?&idRequete=" + chat.getIdRequete();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(username, password);

		try {
			CreneauDto creneauDto = new CreneauDto();
			creneauDto.setIdUser(chat.getIdUser());
			creneauDto.setIdUser1(chat.getIdUser1());
			creneauDto.setIdComp(chat.getIdComp());
			creneauDto.setDate(dateDto.getDate());
			rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(creneauDto, headers), Creneau.class);

		} catch (HttpStatusCodeException e) {
			e.getMessage();
		}

	}

	@Override
	public Page<Creneau> getCreneauxUser(Long idUser, int page, int size, String username, String password)
			throws APiUSerAndCompetenceException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(username, password);
		ParameterizedTypeReference<RestResponsePage<Creneau>> responseType = new ParameterizedTypeReference<RestResponsePage<Creneau>>() {
		};
		String uri = url + "/compagny-creneaux_requetes/creneaux?idUser=" + idUser + "&page=" + page + "&size=" + size;

		ResponseEntity<RestResponsePage<Creneau>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Creneau> creneauUserPage = result.getBody();
		return creneauUserPage;
	}

}
