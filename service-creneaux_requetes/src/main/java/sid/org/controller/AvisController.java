package sid.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import sid.org.classe.Avis;
import sid.org.dto.AvisDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.AvisService;

public class AvisController {
	@Autowired
	AvisService avisService;

	@GetMapping("/avis")
	public Page<Avis> getAvis(@RequestParam Long idComp, @RequestParam Long idUser, @RequestParam int page,
			@RequestParam int size) throws ResultNotFoundException, ForbiddenException {

		Page<Avis> avis = avisService.getAvis(idComp, idUser, page, size);

		return avis;
	}

	@PostMapping("/avis")
	public void createAvis(@RequestBody AvisDto avisDto, @RequestParam Long idUser)
			throws EntityAlreadyExistException, ForbiddenException, ResultNotFoundException {
		avisService.createAvis(avisDto, idUser);

	}

}
