package sid.org;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import sid.org.api.CompetenceApi;
import sid.org.service.CreneauServiceImpl;
import sid.org.service.RequeteService;

@EnableDiscoveryClient
@SpringBootApplication
public class ServiceCreneauxRequetesApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(ServiceCreneauxRequetesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceCreneauxRequetesApplication.class, args);
		logger.info("service-Creneaux_Requetes............");
	}

	@Autowired
	RequeteService requeteService;
	@Autowired
	CompetenceApi competenceApi;
	@Autowired
	CreneauServiceImpl creneauServiceImpl;

	@Override
	public void run(String... args) throws Exception {
		/*
		 * CreneauDto creneauDto = new CreneauDto(1L, 2L, 1L, "demande"); CreneauDto
		 * creneauDto1 = new CreneauDto(1L, 2L, 2L, "demande"); CreneauDto creneauDto2 =
		 * new CreneauDto(1L, 3L, 3L, "demande"); CreneauDto creneauDto3 = new
		 * CreneauDto(1L, 4L, 4L, "demande");
		 * 
		 * RequeteDto requeteDto = new RequeteDto(new Date(), 1L, 2L, "demande");
		 * RequeteDto requeteDto1 = new RequeteDto(new Date(), 1L, 3L, "demande");
		 * RequeteDto requeteDto2 = new RequeteDto(new Date(), 1L, 4L, "demande");
		 * RequeteDto requeteDto3 = new RequeteDto(new Date(), 1L, 5L, "demande");
		 * creneauService.createCreneau(creneauDto, 1L);
		 * creneauService.createCreneau(creneauDto1, 1L);
		 * creneauService.createCreneau(creneauDto2, 2L);
		 * creneauService.createCreneau(creneauDto3, 3L);
		 * 
		 * requeteService.createRequete(requeteDto);
		 * requeteService.createRequete(requeteDto1);
		 * requeteService.createRequete(requeteDto2);
		 * requeteService.createRequete(requeteDto3);
		 */
		/*
		 * RequeteDto requeteDto = new RequeteDto(1L, 2L, "demande");
		 * requeteService.createRequete(requeteDto); CreneauDto creneau = new
		 * CreneauDto(1L, 2L, 1L, "demande", new Date());
		 * creneauServiceImpl.createCreneau(creneau, 1L);
		 */

	}

}
