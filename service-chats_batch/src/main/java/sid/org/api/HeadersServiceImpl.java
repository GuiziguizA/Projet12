package sid.org.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class HeadersServiceImpl implements HeadersService {

	@Autowired
	KeycloakService kc;
	@Value("${keycloak.resource}")
	private String clientid;

	private static final Logger logger = LoggerFactory.getLogger(HeadersServiceImpl.class);

	@Override
	public HttpHeaders createTokenHeaders(String token) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + token);
		return headers;

	}

}
