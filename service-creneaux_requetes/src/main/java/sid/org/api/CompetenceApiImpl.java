package sid.org.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Competence;
import sid.org.classe.TokenString;
import sid.org.config.RecupToken;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class CompetenceApiImpl implements CompetenceApi {
	@Value("${api.url}")
	private String url;
	@Autowired
	private HeadersService headersService;

	@Override
	public Competence getCompetence(Long idcompetence) throws APiUSerAndCompetenceException, HttpStatusCodeException {
		System.out.println(idcompetence);
		String uri = url + "/service-user_competence/competence/" + idcompetence;
		RecupToken token = new RecupToken();
		TokenString tok = token.tokenString();
		HttpHeaders headers = headersService.createTokenHeaders(tok.getValue());
		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Competence> comp = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
					Competence.class);
			return comp.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(
					"l'api getCompetence ne marche pas et l'erreure est : " + e.getMessage());
		}

	}
}
