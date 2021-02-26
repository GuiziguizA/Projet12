package sid.org.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Users;
import sid.org.service.HeadersService;
import sid.org.service.KeycloakService;

@Service
public class UserDetailsImpl implements UserDetailsService {

	@Value("${api.url}")
	private String apiUrl;
	@Autowired
	KeycloakService keycloakService;
	@Autowired
	HeadersService headersService;
	/**
	 * Modifie le User details de spring data security en identifiant l'utilisateur
	 * par son mail dans BD
	 * 
	 * @param mail
	 * @return User.withUsername(utilisateur.get().getMail()) .password(
	 *         utilisateur.get().getPassword())
	 *         .roles(utilisateur.get().getRole().getNom()).build();
	 */

	@Value("${keycloak.resource}")
	private String client;
	private static final Logger logger = LoggerFactory.getLogger(UserDetailsImpl.class);

	@Override
	public UserDetails loadUserByUsername(String mailAndPassword) {

		RestTemplate rt = new RestTemplate();

		String[] split = mailAndPassword.split(":");
		String username = split[0];
		final String uri = apiUrl + "/compagny-user_competence/user/identity?name=" + username;
		logger.info(username);
		String motDePasse = split[1];
		logger.info(motDePasse);
		String token = keycloakService.RecupTokenClient(username, motDePasse, client);
		HttpHeaders headers = headersService.createTokenHeaders(username, motDePasse);

		ResponseEntity<Users> user = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(headers), Users.class);
		Users utilisateur = user.getBody();

		if (utilisateur == null) {
			throw new UsernameNotFoundException("l'utilisateur n'existe pas");
		}

		return User.withUsername(utilisateur.getUsername()).password(utilisateur.getPassword())
				.roles(utilisateur.getRoles().getNom()).build();
	}

}
