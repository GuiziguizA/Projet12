package sid.org;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import sid.org.dto.CreneauDto;
import sid.org.dto.RequeteDto;
import sid.org.service.CreneauService;
import sid.org.service.RequeteService;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceCreneauxRequetesApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ServiceCreneauxRequetesApplication.class, args);
	}

	@Autowired
	CreneauService creneauService;
	@Autowired
	RequeteService requeteService;

	@Override
	public void run(String... args) throws Exception {
		CreneauDto creneauDto = new CreneauDto(1L, 2L, 1L, "demande");
		CreneauDto creneauDto1 = new CreneauDto(1L, 2L, 2L, "demande");
		CreneauDto creneauDto2 = new CreneauDto(1L, 3L, 3L, "demande");
		CreneauDto creneauDto3 = new CreneauDto(1L, 4L, 4L, "demande");

		RequeteDto requeteDto = new RequeteDto(new Date(), 1L, 2L, "demande");
		RequeteDto requeteDto1 = new RequeteDto(new Date(), 1L, 3L, "demande");
		RequeteDto requeteDto2 = new RequeteDto(new Date(), 1L, 4L, "demande");
		RequeteDto requeteDto3 = new RequeteDto(new Date(), 1L, 5L, "demande");
		creneauService.createCreneau(creneauDto);
		creneauService.createCreneau(creneauDto1);
		creneauService.createCreneau(creneauDto2);
		creneauService.createCreneau(creneauDto3);

		requeteService.createRequete(requeteDto);
		requeteService.createRequete(requeteDto1);
		requeteService.createRequete(requeteDto2);
		requeteService.createRequete(requeteDto3);

	}

}
