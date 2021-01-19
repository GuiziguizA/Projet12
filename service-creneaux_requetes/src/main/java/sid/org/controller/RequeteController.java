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

import sid.org.classe.Requete;
import sid.org.dto.RequeteDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.RequeteService;

@RestController
public class RequeteController {
	@Autowired
	RequeteService requeteService;

	@PostMapping("/requete")
	public Requete createRequete(@RequestBody RequeteDto requeteDto) throws EntityAlreadyExistException {

		Requete requete = requeteService.createRequete(requeteDto);
		return requete;

	}

	@GetMapping("/requete/{id}")
	public Requete getRequete(@PathVariable Long id) throws ResultNotFoundException {

		Requete requete = requeteService.getRequete(id);

		return requete;
	}

	@GetMapping("/requetes")
	public Page<Requete> getRequetes(@RequestParam Long idUser) {

		Page<Requete> requetes = requeteService.getRequetes(idUser);

		return requetes;
	}

	@DeleteMapping("/requete")
	public void deleteRequetes(@RequestParam Long id) throws ResultNotFoundException {

		requeteService.deleteRequete(id);

	}

}
