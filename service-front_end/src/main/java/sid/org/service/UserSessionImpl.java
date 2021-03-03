package sid.org.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Users;

@Service
public class UserSessionImpl implements UserSession {

	@Value("${api.url}")
	private String apiUrl;
	@Autowired
	HeadersService headersService;

	@Override
	public void loadUserInSession(HttpServletRequest request, String username, String password) {
		HttpSession session = request.getSession();

		Users user1 = (Users) session.getAttribute("user");

		if (user1 == null) {

			RestTemplate rt = new RestTemplate();
			final String uri = apiUrl + "/compagny-user_competence/user/identity?name=" + username;

			HttpHeaders headers = headersService.createTokenHeaders(username, password);

			ResponseEntity<Users> user = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), Users.class);
			Users utilisateur = user.getBody();

			session.setAttribute("user", utilisateur);
		}

	}

}
