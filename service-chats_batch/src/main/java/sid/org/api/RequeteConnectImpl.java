package sid.org.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Requete;
import sid.org.classe.TokenString;
import sid.org.config.RecupToken;

@Service
public class RequeteConnectImpl implements RequeteConnect {
	private static final Logger logger = LoggerFactory.getLogger(RequeteConnectImpl.class);

	@Value("${api.url}")
	private String url;
	@Autowired
	HeadersService headersService;

	@Override
	public Requete getRequete(Long idRequete, Long idUser) throws HttpStatusCodeException {

		String uri = url + "/service-creneaux_requetes/requete?id=" + idRequete + "&idUser=" + idUser;
		RecupToken token = new RecupToken();
		TokenString tok = token.tokenString();
		HttpHeaders headers = headersService.createTokenHeaders(tok.getValue());
		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Requete> requete = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
					Requete.class);
			logger.info("connection api requete reussi");
			return requete.getBody();

		} catch (HttpStatusCodeException e) {
			logger.info("connection api requete échoué");
			e.getMessage();
			return null;
		}

	}
}
