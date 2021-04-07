package sid.org;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import sid.org.service.ChatService;
import sid.org.service.CompetenceService;
import sid.org.service.CreneauService;
import sid.org.service.HeadersService;
import sid.org.service.KeycloakService;
import sid.org.service.RequeteService;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceFrontEndApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(ServiceFrontEndApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceFrontEndApplication.class, args);
		logger.info("service-front_end............");
	}

	@Autowired
	CompetenceService competenceService;
	@Autowired
	RequeteService requeteService;
	@Autowired
	ChatService chatService;
	@Autowired
	CreneauService creneauService;

	@Autowired
	KeycloakService keycloakService;
	@Autowired
	HeadersService headersService;

	@Override
	public void run(String... args) throws Exception {

		/* requeteService.createRequete(1L, 2L); */

		/* chatService.getChatUser(1L, 0, 2); */

		/* requeteService.validerRequete(4L, 1L); */
		/*
		 * Chat chat = new Chat(3L, "encours", 4L, 2L, 1L, 2L);
		 * 
		 * DateDto dateDto = new DateDto("10:00:00", "10", "10", "1994");
		 * dateService.createDate(dateDto); creneauService.createCreneau(chat, dateDto);
		 */
		/*
		 * RestTemplate rt = new RestTemplate(); String token =
		 * keycloakService.RecupTokenClient("kamel", "frere", "service-front_end");
		 * 
		 * Sessions sessions = new Sessions(); sessions.setToken(token); HttpHeaders
		 * headers = headersService.createTokenHeaders("kamel", "frere",
		 * "service-front_end");
		 * 
		 * final String uri =
		 * "http://localhost:8089/compagny-user_competence/user?name=kamel";
		 * keycloakService.RecupTokenClient("kamel", "frere", "service-front_end");
		 * ResponseEntity<Users> user = rt.exchange(uri, HttpMethod.GET, new
		 * HttpEntity<>(headers), Users.class); Users utilisateur = user.getBody();
		 */
		/*
		 * Page<Competence> page = competenceService.searchCompetence(null, 2, 0);
		 * System.out.println(page.getSize() + "ddddddddddddddddddddd");
		 */
	}

}
