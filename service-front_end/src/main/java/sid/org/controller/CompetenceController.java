package sid.org.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Competence;
import sid.org.dto.CompetenceDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.service.CompetenceService;
import sid.org.service.HttpService;

@Controller
public class CompetenceController {
	@Autowired
	CompetenceService competenceService;
	@Autowired
	HttpService httpService;

	@GetMapping("/competences")
	public String getCompetencesSearch(Model model, @RequestParam(required = false) Optional<String> recherche,
			@RequestParam(required = false) Optional<Integer> size,
			@RequestParam(required = false) Optional<Integer> page) {

		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);
		try {
			Page<Competence> competencePage = competenceService.searchCompetence(recherche, pageSize, currentPage);
			model.addAttribute("competencePage", competencePage);
			int totalPages = competencePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "competences";

		} catch (HttpStatusCodeException e) {

			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";
		}

	}

	@GetMapping("/")
	public String getCompetencesUser(Model model, @RequestParam(required = false) Optional<Integer> size,
			@RequestParam(required = false) Optional<Integer> page) {
		Long idUser = 1L;
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);
		try {
			Page<Competence> competencePage = competenceService.getCompetencesUser(idUser, pageSize, currentPage);
			model.addAttribute("competencePage", competencePage);
			int totalPages = competencePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "home";

		} catch (HttpStatusCodeException e) {

			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";
		}

	}

	@GetMapping("/competence/{id}")
	public String getCompetence(Model model, @PathVariable Long id) {

		try {
			Competence competence = competenceService.getCompetence(id);
			model.addAttribute("competence", competence);

			return "competence";

		} catch (

		HttpStatusCodeException e) {

			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";
		}

	}

	@PostMapping("/competence")
	public String createcompetence(CompetenceDto competenceDto, BindingResult result, Model model)
			throws APiUSerAndCompetenceException {
		Long idUser = 1L;

		try {
			competenceService.createComp(competenceDto, idUser);
			getCompetencesUser(model, Optional.of(2), Optional.of(0));
			;
			String succes = "Votre competence a ete ajoute";
			model.addAttribute("succes", succes);
			return "home";
		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);
			model.addAttribute("error", error);
			return "formulaireCompetence";
		}

	}

	@GetMapping(value = "/competenceForm")
	public String competenceForm(CompetenceDto competenceDto, Model model) {

		return "formulaireCompetence";
	}

}