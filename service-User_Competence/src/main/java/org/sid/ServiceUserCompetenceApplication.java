package org.sid;

import org.sid.api.KeycloakService;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.RolesRepository;
import org.sid.dao.UsersRepository;
import org.sid.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceUserCompetenceApplication implements CommandLineRunner {

	@Autowired
	KeycloakService keycloakService;
	@Autowired
	CompetenceRepository competenceRepository;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	RolesRepository rolesRepository;
	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(ServiceUserCompetenceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserCompetenceApplication.class, args);
		logger.info("service-user_competence Started........");
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		 * Role role = new Role("user"); rolesRepository.save(role);
		 */

		/*
		 * List<Competence> listComp = new ArrayList<Competence>(); UserDto userDto =
		 * new UserDto("kamel", "kamel@gmail.com", "3 rue du cerisier", "frere",
		 * "45125"); Users user = userService.createUser(userDto);
		 * competenceRepository.save(new Competence(1L, "gateau chocolat", "cuisine",
		 * "gateau", user)); competenceRepository.save(new Competence(2L,
		 * "gateau chocolat", "cuisine", "gateau", user));
		 */
		/*
		 * keycloakService.RecupTokenAdmin("admin", "admin", "admin-cli");
		 * 
		 * keycloakService.createUserKeycloak("kamel", "kamel@gmail.com", "frere");
		 */
		/*
		 * String id = keycloakService.UserGetId("kamel@gmail.com");
		 * System.out.println(id);
		 */

	}

}
