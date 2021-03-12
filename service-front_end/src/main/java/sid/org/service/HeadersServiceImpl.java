package sid.org.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.config.RecupToken;

@Service
public class HeadersServiceImpl implements HeadersService {

	@Autowired
	KeycloakService kc;
	@Value("${keycloak.resource}")
	private String clientid;
	@Autowired
	UserSession userSession;

	private static final Logger logger = LoggerFactory.getLogger(HeadersServiceImpl.class);

	@Override
	public HttpHeaders createTokenHeaders(HttpServletRequest request) throws HttpStatusCodeException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, username, password);
		HttpHeaders headers = new HttpHeaders();
		String token;
		if (session.getAttribute("token") == null) {
			token = kc.RecupTokenClient(username, password, clientid);
			session.setAttribute("token", token);
			logger.info("cest le token : " + token);

		} else {
			token = (String) session.getAttribute("token");

		}
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		return headers;
	}

	@Override
	public HttpHeaders giveTokenHeaders() throws HttpStatusCodeException {
		RecupToken recuptoken = new RecupToken();
		HttpHeaders headers = new HttpHeaders();
		String token = recuptoken.tokenString().getValue();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		return headers;
	}

	@Override
	public HttpHeaders createTokenHeaders1(String username, String password) throws HttpStatusCodeException {
		String token = kc.RecupTokenClient(username, password, clientid);

		logger.info("cest le token : " + token);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		return headers;

	}

}
