package org.sid.controller;

import javax.validation.Valid;

import org.sid.classe.Competence;
import org.sid.dto.CompetenceDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.sid.service.CompetenceService;
import org.sid.specification.CompetenceCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompetenceController {
	@Autowired
	CompetenceService competenceService;

	@GetMapping("/search")
	public Page<Competence> searchCompetence(@RequestBody CompetenceCriteria comp, @RequestParam int page,
			@RequestParam int size) throws ResultNotFoundException {

		Page<Competence> search = competenceService.searchCompetence(comp, page, size);

		return search;

	}

	@PostMapping("/competence")
	public Competence createCompetence(@Valid @RequestBody CompetenceDto competenceDto)
			throws EntityAlreadyExistException, ResultNotFoundException {

		Competence competence = competenceService.createCompetence(competenceDto);
		return competence;
	}

	@DeleteMapping("/competence")
	public void deleteCompetence(@RequestParam Long id) throws ResultNotFoundException {
		competenceService.deleteCompetence(id);

	}

	@GetMapping("/competence/{id}")
	public Competence getcompetence(@PathVariable Long id) throws ResultNotFoundException {
		Competence competence = competenceService.getCompetence(id);
		return competence;
	}

	@GetMapping("/competences")
	public Page<Competence> getcompetencesUser(@RequestParam String nom, @RequestParam int page, @RequestParam int size)
			throws ResultNotFoundException {
		Page<Competence> competences = competenceService.getCompetencesUser(nom, page, size);
		return competences;
	}

}
