package org.sid;

import java.util.ArrayList;
import java.util.List;

import org.sid.classe.Competence;
import org.sid.classe.Roles;
import org.sid.classe.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceUserCompetenceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserCompetenceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Roles role = new Roles("user");
		List<Competence> listComp = new ArrayList<Competence>();
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence comp = new Competence(1L, "gateau chocolat", "cuisine", "gateau", user);
		Competence comp1 = new Competence(2L, "gateau chocolat", "cuisine", "gateau", user);
		listComp.add(comp);
		listComp.add(comp1);
	}

}
