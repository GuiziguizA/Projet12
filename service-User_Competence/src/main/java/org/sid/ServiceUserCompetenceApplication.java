package org.sid;

import org.sid.service.UserService;
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

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserCompetenceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * Roles role = new Roles("user"); List<Competence> listComp = new
		 * ArrayList<Competence>(); UserDto userDto = new UserDto( "boba",
		 * "boba@gmail.com", "3 rue du cerisier", "boba", "45125"); User user=
		 * userService.createUser(userDto); Competence comp = new Competence(1L,
		 * "gateau chocolat", "cuisine", "gateau", user); Competence comp1 = new
		 * Competence(2L, "gateau chocolat", "cuisine", "gateau", user);
		 * listComp.add(comp); listComp.add(comp1);
		 */
	}

}
