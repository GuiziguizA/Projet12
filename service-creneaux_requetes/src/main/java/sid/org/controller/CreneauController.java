package sid.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sid.org.classe.Creneau;
import sid.org.dto.CreneauDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.CreneauService;

@RestController
public class CreneauController {

	@Autowired
	CreneauService creneauService;

	@PostMapping("/creneau")
	public Creneau createCreneau(@RequestBody CreneauDto CreneauDto, @RequestParam Long idUser,
			@RequestParam Long idRequete) throws EntityAlreadyExistException, APiUSerAndCompetenceException,
			ForbiddenException, ResultNotFoundException {

		Creneau creneau = creneauService.createCreneau(CreneauDto, idUser, idRequete);
		return creneau;

	}

	@GetMapping("/creneau")
	public Creneau getCreneau(@RequestParam Long id, @RequestParam Long idUser, @RequestParam Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException {

		Creneau creneau = creneauService.getCreneau(id, idUser, idUser1);
		return creneau;

	}

	@GetMapping("/creneaux")
	public Page<Creneau> getCreneaux(@RequestParam Long idUser, @RequestParam Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException {

		Page<Creneau> creneaux = creneauService.getCreneaux(idUser, idUser1);
		return creneaux;

	}

	@DeleteMapping("/creneau")
	public void deleteCreneaux(@RequestParam Long id, @RequestParam Long idUser, @RequestParam Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException {

		creneauService.deleteCreneau(id, idUser, idUser1);

	}

}
