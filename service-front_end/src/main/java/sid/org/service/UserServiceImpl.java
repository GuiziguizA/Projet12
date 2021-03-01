package sid.org.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.Page.RestResponsePage;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.dto.UserDto;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class UserServiceImpl implements UserService {
	@Value("${api.url}")
	private String url;
	@Autowired
	HeadersService headersService;

	@Override
	public void createUser(UserDto userDto, String username, String password) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-user_competence/user";
		HttpHeaders headers = headersService.createTokenHeaders(username, password);
		RestTemplate rt = new RestTemplate();

		try {
			rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(userDto), Users.class);

		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(e.getMessage());
		}
	}

	@Override
	public List<Users> getUsers(String username, String password) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-user_competence/users";

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(username, password);
		ParameterizedTypeReference<RestResponsePage<Requete>> responseType = new ParameterizedTypeReference<RestResponsePage<Requete>>() {
		};
		try {
			ResponseEntity<List> users = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), List.class);
			return users.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(
					"l'api getUsers ne marche pas et l'erreure est : " + e.getMessage());
		}

	}

	@Override
	public Users getUser(String username, String password) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-user_competence/user";

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(username, password);
		ParameterizedTypeReference<RestResponsePage<Requete>> responseType = new ParameterizedTypeReference<RestResponsePage<Requete>>() {
		};
		try {
			ResponseEntity<Users> users = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Users.class);
			return users.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(
					"l'api getUsers ne marche pas et l'erreure est : " + e.getMessage());
		}

	}

}
