package sid.org.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Avis;
import sid.org.classe.Competence;
import sid.org.classe.Users;
import sid.org.dto.CompetenceDto;
import sid.org.enumeration.Types;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.service.AvisService;
import sid.org.service.CompetenceService;
import sid.org.service.HttpService;

@Controller
public class CompetenceController {
	@Autowired
	CompetenceService competenceService;
	@Autowired
	HttpService httpService;
	@Autowired
	AvisService avisService;

	@GetMapping("/competences")
	public String getCompetencesSearch(Model model, @RequestParam(required = false) Optional<String> recherche,
			@RequestParam(required = false) Optional<Integer> size,
			@RequestParam(required = false) Optional<Integer> page, HttpServletRequest request)
			throws APiUSerAndCompetenceException {

		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");

		try {
			Page<Competence> competencePage = competenceService.searchCompetence(recherche, pageSize, currentPage,
					request);

			model.addAttribute("competencePage", competencePage);
			model.addAttribute("name", name);
			int totalPages = competencePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "competences";

		} catch (HttpStatusCodeException e) {

			String error = httpService.traiterLesExceptionsApi(e);
			if (error == "error") {
				return "redirect:/logout";
			} else {
				model.addAttribute("error", error);
				return "error";
			}
		}

	}

	@GetMapping("/home")
	public String getCompetencesUser(Model model, @RequestParam(required = false) Optional<Integer> size,
			@RequestParam(required = false) Optional<Integer> page, HttpServletRequest request) {

		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");

		try {

			Page<Competence> competencePage = competenceService.getCompetencesUser(name, pageSize, currentPage,
					request);
			model.addAttribute("competencePage", competencePage);
			int totalPages = competencePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "home";

		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);
			if (error == "error") {
				return "redirect:/logout";
			} else {
				model.addAttribute("error", error);
				return "error";
			}
		}

	}

	@GetMapping("/competence")
	public String getCompetence(Model model, @RequestParam Long id, HttpServletRequest request) {
		int currentPage = 0;
		int pagesize = 2;
		try {
			Competence competence = competenceService.getCompetence(id, request);
			model.addAttribute("competence", competence);

			Page<Avis> avisPage = avisService.getAvis(id, currentPage, pagesize, request);
			model.addAttribute("avisPage", avisPage);
			int totalPages = avisPage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);

			}
			return "competence";
		} catch (HttpStatusCodeException e) {

			String error = httpService.traiterLesExceptionsApi(e);
			if (error == "error") {
				return "redirect:/logout";
			} else {
				model.addAttribute("error", error);
				return "error";
			}
		}

	}

	@PostMapping("/competence")
	public String createcompetence(@Valid CompetenceDto competenceDto, BindingResult result, Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Users user = (Users) session.getAttribute("user");
		Long idUser = user.getCodeUtilisateur();
		if (result.hasErrors()) {
			return "formulaireCompetence";

		}

		try {
			competenceService.createComp(competenceDto, idUser, request);

			getCompetencesUser(model, Optional.of(2), Optional.of(0), request);

			String succes = "Votre competence a ete ajoute";
			model.addAttribute("succes", succes);
			return "home";
		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);
			if (error == "error") {
				return "redirect:/logout";
			} else {
				model.addAttribute("error", error);
				return "error";
			}
		}

	}

	@GetMapping("/deletecompetence")
	public String deletecompetence(Model model, @RequestParam Long id, HttpServletRequest request) {

		try {
			competenceService.deleteComp(id, request);

			return "redirect:/home";
		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);
			if (error == "error") {
				return "redirect:/logout";
			} else {
				model.addAttribute("error", error);
				return "redirect:/home";
			}
		}

	}

	@GetMapping(value = "/competenceForm")
	public String competenceForm(Model model, CompetenceDto competenceDto) {
		List<Types> listTypes = competenceService.chargerLesTypesDeRecherches();
		model.addAttribute("listTypes", listTypes);
		return "formulaireCompetence";
	}

}