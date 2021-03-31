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

import sid.org.classe.TokenString;
import sid.org.classe.Users;
import sid.org.config.RecupToken;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class UserConnectImpl implements UserConnect {
	@Value("${api.url}")
	private String url;
	@Autowired
	HeadersService headersService;

	@Override
	public Users getUser(Long idUser) throws APiUSerAndCompetenceException {

		String uri = url + "/compagny-user_competence/user/" + idUser;
		RecupToken token = new RecupToken();
		TokenString tok = token.tokenString();
		HttpHeaders headers = headersService.createTokenHeaders(tok.getValue());
		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Users> user = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Users.class);
			return user.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("l'api getUser ne marche pas et l'erreure est : " + e.getMessage());
		}

	}
}
