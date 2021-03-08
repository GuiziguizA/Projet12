package sid.org.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Users;
import sid.org.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {
	@Value("${api.url}")
	private String url;
	@Autowired
	HeadersService headersService;

	@Override
	public void createUser(UserDto userDto, HttpServletRequest request) throws HttpStatusCodeException {
		String uri = url + "/compagny-user_competence/user";

		RestTemplate rt = new RestTemplate();

		rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(userDto), Users.class);

	}

	@Override
	public List<Users> getUsers(HttpServletRequest request) throws HttpStatusCodeException {
		String uri = url + "/compagny-user_competence/users";

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		ResponseEntity<List> users = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), List.class);
		return users.getBody();

	}

	@Override
	public Users getUser(HttpServletRequest request) throws HttpStatusCodeException {
		String uri = url + "/compagny-user_competence/user";

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		ResponseEntity<Users> users = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Users.class);
		return users.getBody();

	}

}
