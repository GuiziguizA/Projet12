package org.sid.api;

import java.util.ArrayList;
import java.util.List;

import org.sid.classe.Credentials;
import org.sid.classe.TokenKeycloak;
import org.sid.classe.Userkeycloak;
import org.sid.config.RequestFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakServiceImpl implements KeycloakService {

	private static final Logger logger = LoggerFactory.getLogger(KeycloakServiceImpl.class);

	@Autowired
	RequestFactory requestFactory;

	@Override
	public String RecupTokenAdmin(String username, String password, String clientId) {

		MultiValueMap<String, String> token = null;
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://localhost:8080/auth/realms/master/protocol/openid-connect/token";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		ParameterizedTypeReference<MultiValueMap<String, String>> responseType = new ParameterizedTypeReference<MultiValueMap<String, String>>() {
		};
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("grant_type", "password");
		map.add("client_id", clientId);
		map.add("username", username);
		map.add("password", password);

		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);
		try {
			ResponseEntity<TokenKeycloak> response = restTemplate.exchange(url, HttpMethod.POST, entity,
					TokenKeycloak.class);
			TokenKeycloak token1 = response.getBody();
			logger.info(token1.getAccess_token());
			return token1.getAccess_token();
		} catch (HttpStatusCodeException e) {
			return e.getMessage();
		}

	}

	@Override
	public void createUserKeycloak(String name, String mail, String password) {
		RestTemplate restTemplate = requestFactory.getRestTemplate();
		String url = "http://localhost:8080/auth/admin/realms/SocialAppRealm/users";
		String accessToken = RecupTokenAdmin("admin", "admin", "admin-cli");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);

		Userkeycloak userkeycloak = new Userkeycloak();
		List<MultiValueMap> credentials = new ArrayList<MultiValueMap>();
		userkeycloak.setEmail(mail);
		userkeycloak.setFirstName(name);
		userkeycloak.setUsername(name);
		userkeycloak.setCredentials(createMapPasswortd(password));
		userkeycloak.setEnabled("true");
		HttpEntity<Userkeycloak> entity = new HttpEntity<>(userkeycloak, headers);

		restTemplate.postForEntity(url, new HttpEntity<>(userkeycloak, headers), Long.class);

	}

	@Override
	public String UserGetId(String mail) {
		RestTemplate restTemplate = requestFactory.getRestTemplate();
		String url = "http://localhost:8080/auth/admin/realms/SocialAppRealm/users";
		String accessToken = RecupTokenAdmin("admin", "admin", "admin-cli");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);
		ParameterizedTypeReference<ArrayList<Userkeycloak>> responseType = new ParameterizedTypeReference<ArrayList<Userkeycloak>>() {
		};

		ResponseEntity<ArrayList<Userkeycloak>> listUserKeycloak = restTemplate.exchange(url, HttpMethod.GET,
				new HttpEntity<>(null, headers), responseType);

		logger.info(listUserKeycloak.toString());

		Userkeycloak user = listUserKeycloak.getBody().stream()
				.filter(userKeycloak -> mail.equals(userKeycloak.getEmail())).findAny().orElse(null);

		return user.getId();

	}

	public List<Credentials> createMapPasswortd(String password) {

		Credentials cred = new Credentials();
		List<Credentials> listcred = new ArrayList<Credentials>();
		cred.setType("password");
		cred.setTemporary(false);
		cred.setValue(password);
		listcred.add(cred);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("type", "password");
		map.add("value", password);
		map.add("temporary", false);

		return listcred;
	}

}
