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
import org.springframework.web.client.RestTemplate;

import sid.org.classe.TokenString;
import sid.org.classe.Users;
import sid.org.config.RecupToken;
import sid.org.service.RequeteServiceImpl;

@Service
public class UserConnectImpl implements UserConnect {
	@Value("${api.url}")
	private String url;
	private static final Logger logger = LoggerFactory.getLogger(RequeteServiceImpl.class);
	@Autowired
	HeadersService headersService;

	@Override
	public Users getUser(Long idUser) {
		RecupToken token = new RecupToken();
		TokenString tok = token.tokenString();

		logger.info(tok.getValue());
		String uri = url + "/service-user_competence/user/" + idUser;

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(tok.getValue());
		ResponseEntity<Users> user = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Users.class);
		return user.getBody();

	}

}
