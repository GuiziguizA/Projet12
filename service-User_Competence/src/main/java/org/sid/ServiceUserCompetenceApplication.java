package org.sid;

import org.sid.dao.RolesRepository;
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
	private UserService userService;
	@Autowired
	RolesRepository rolesRepository;

	private static final Logger logger = LoggerFactory.getLogger(ServiceUserCompetenceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserCompetenceApplication.class, args);
		logger.info("service-user_competence Started........");
	}

	@Override
	public void run(String... args) throws Exception {

		/*
		 * Roles role = new Roles("user"); rolesRepository.save(role); List<Competence>
		 * listComp = new ArrayList<Competence>(); UserDto userDto = new UserDto("boba",
		 * "boba@gmail.com", "3 rue du cerisier", "boba", "45125"); Users user =
		 * userService.createUser(userDto); Competence comp = new Competence(1L,
		 * "gateau chocolat", "cuisine", "gateau", user); Competence comp1 = new
		 * Competence(2L, "gateau chocolat", "cuisine", "gateau", user);
		 * listComp.add(comp); listComp.add(comp1);
		 */

	}

}
