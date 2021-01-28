package sid.org.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Users;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class UserConnectImpl implements UserConnect {
	@Value("${api.url}")
	private String url;

	@Override
	public Users getUser(Long idUser) throws APiUSerAndCompetenceException {

		String uri = url + "/compagny-user_competence/user";

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Users> user = rt.getForEntity(uri, Users.class);
			return user.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("Impossible d'effectuer cette action");
		}

	}
}
