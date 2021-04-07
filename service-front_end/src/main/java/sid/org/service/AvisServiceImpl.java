package sid.org.service;

import javax.servlet.http.HttpServletRequest;

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

import sid.org.Page.RestResponsePage;
import sid.org.classe.Avis;
import sid.org.config.RequestFactory;
import sid.org.dto.AvisDto;

@Service
public class AvisServiceImpl implements AvisService {

	@Value("${api.url}")
	private String url;

	private final RequestFactory requestFactory;

	@Autowired
	public AvisServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Autowired
	private HeadersService headersService;

	@Override
	public void createAvis(AvisDto avisDto, HttpServletRequest request, Long idUser) throws HttpStatusCodeException {

		String uri = url + "/service-creneaux_requetes/avis?&idUser=" + idUser;
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		AvisDto avisDto1 = new AvisDto();

		avisDto1.setCreneau(avisDto.getCreneau());
		avisDto1.setCommentaire(avisDto.getCommentaire());
		avisDto1.setNote(avisDto.getNote());
		avisDto1.setIdUser(idUser);

		rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(avisDto1, headers), Long.class);

	}

	@Override
	public Page<Avis> getAvis(Long idComp, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		ParameterizedTypeReference<RestResponsePage<Avis>> responseType = new ParameterizedTypeReference<RestResponsePage<Avis>>() {
		};
		String uri = url + "/service-creneaux_requetes/avis?idComp=" + idComp + "&page=" + page + "&size=" + size;

		ResponseEntity<RestResponsePage<Avis>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Avis> avisPage = result.getBody();
		return avisPage;
	}

}
