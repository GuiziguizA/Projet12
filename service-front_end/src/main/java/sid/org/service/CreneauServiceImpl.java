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
import sid.org.classe.Creneau;
import sid.org.config.RequestFactory;
import sid.org.dto.ChatDateDtoObject;
import sid.org.dto.CreneauDto;

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
	public void createCreneau(ChatDateDtoObject chatDateDtoObject, Long idUser, HttpServletRequest request)
			throws HttpStatusCodeException {

		String uri = url + "/compagny-creneaux_requetes/creneau?&idRequete="
				+ chatDateDtoObject.getChat().getIdRequete();
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		CreneauDto creneauDto = new CreneauDto();
		creneauDto.setIdUser(chatDateDtoObject.getChat().getIdUser());
		creneauDto.setIdUser1(chatDateDtoObject.getChat().getIdUser1());
		creneauDto.setIdUserDemande(idUser);

		creneauDto.setIdComp(chatDateDtoObject.getChat().getIdComp());
		creneauDto.setDate(chatDateDtoObject.getDateDto());
		creneauDto.setIdChat(chatDateDtoObject.getChat().getId());
		rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(creneauDto, headers), Creneau.class);

	}

	@Override
	public Page<Creneau> getCreneauxUser(Long idUser, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		ParameterizedTypeReference<RestResponsePage<Creneau>> responseType = new ParameterizedTypeReference<RestResponsePage<Creneau>>() {
		};
		String uri = url + "/compagny-creneaux_requetes/creneaux?idUser=" + idUser + "&page=" + page + "&size=" + size;

		ResponseEntity<RestResponsePage<Creneau>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Creneau> creneauUserPage = result.getBody();
		return creneauUserPage;
	}

	@Override
	public Page<Creneau> getCreneauxUser1(Long idUser1, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		ParameterizedTypeReference<RestResponsePage<Creneau>> responseType = new ParameterizedTypeReference<RestResponsePage<Creneau>>() {
		};
		String uri = url + "/compagny-creneaux_requetes/creneaux1?idUser1=" + idUser1 + "&page=" + page + "&size="
				+ size;

		ResponseEntity<RestResponsePage<Creneau>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Creneau> creneauUserPage = result.getBody();
		return creneauUserPage;
	}

	@Override
	public Creneau getCreneau(Long id, HttpServletRequest request) throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		String uri = url + "/compagny-creneaux_requetes/creneau1?id=" + id;

		ResponseEntity<Creneau> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Creneau.class);
		Creneau creneau = result.getBody();
		return creneau;
	}

	@Override
	public void validerCreneau(Long idCreneau, Long idUser, HttpServletRequest request) throws HttpStatusCodeException {

		String uri = url + "/compagny-creneaux_requetes/creneau?id=" + idCreneau + "&idUser=" + idUser;
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		rt.exchange(uri, HttpMethod.PUT, new HttpEntity<>(headers), Long.class);

	}

}
