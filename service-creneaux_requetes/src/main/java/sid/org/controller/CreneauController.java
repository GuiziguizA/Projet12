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

import sid.org.classe.Creneau;
import sid.org.dto.CreneauDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.CreneauService;

@RestController
public class CreneauController {

	@Autowired
	CreneauService creneauService;

	@PostMapping("/creneau")
	public Creneau createCreneau(@RequestBody CreneauDto CreneauDto) throws EntityAlreadyExistException {

		Creneau creneau = creneauService.createCreneau(CreneauDto);
		return creneau;

	}

	@GetMapping("/creneau/{id}")
	public Creneau getCreneau(@PathVariable Long id) throws ResultNotFoundException {

		Creneau creneau = creneauService.getCreneau(id);
		return creneau;

	}

	@GetMapping("/creneaux")
	public Page<Creneau> getCreneaux(@RequestParam Long idUser) throws ResultNotFoundException {

		Page<Creneau> creneaux = creneauService.getCreneaux(idUser);
		return creneaux;

	}

	@DeleteMapping("/creneau")
	public void deleteCreneaux(@RequestParam Long id) throws ResultNotFoundException {

		creneauService.deleteCreneau(id);

	}

}
