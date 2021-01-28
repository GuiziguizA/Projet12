package sid.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.api.CompetenceApi;
import sid.org.api.UserConnect;
import sid.org.classe.Requete;
import sid.org.dto.RequeteDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.RequeteService;

@RestController
public class RequeteController {
	@Autowired
	RequeteService requeteService;
	@Autowired
	UserConnect userConnect;
	@Autowired
	CompetenceApi compApi;

	@PostMapping("/requete")
	public Requete createRequete(@RequestBody RequeteDto requeteDto)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException {
		userConnect.getUser(requeteDto.getIdUser());
		compApi.getCompetence(requeteDto.getIdComp());

		Requete requete = requeteService.createRequete(requeteDto);
		return requete;

	}

	@GetMapping("/requete/{id}")
	public Requete getRequete(@PathVariable Long id, @RequestParam Long idUser)
			throws ResultNotFoundException, ForbiddenException, APiUSerAndCompetenceException {

		Requete requete = requeteService.getRequete(id, idUser);

		return requete;
	}

	@GetMapping("/requetes")
	public Page<Requete> getRequetes(@RequestParam Long idUser) throws APiUSerAndCompetenceException {

		Page<Requete> requetes = requeteService.getRequetes(idUser);

		return requetes;
	}

	@DeleteMapping("/requete")
	public void deleteRequetes(@RequestParam Long id, @RequestParam Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {

		requeteService.deleteRequete(id, idUser);

	}

	@PostMapping("/validateRequete")
	public void validateRequete(@RequestParam Long idRequete, @RequestParam Long idUser1)
			throws HttpStatusCodeException, ResultNotFoundException, APiUSerAndCompetenceException,
			EntityAlreadyExistException {

		requeteService.validerUneRequete(idRequete, idUser1);

	}

}
