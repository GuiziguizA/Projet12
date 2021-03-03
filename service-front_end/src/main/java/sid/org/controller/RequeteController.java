package sid.org.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sid.org.classe.Competence;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.service.CompetenceService;
import sid.org.service.RequeteService;
import sid.org.service.UserService;
import sid.org.service.UserSession;

@Controller
public class RequeteController {
	@Autowired
	RequeteService requeteService;
	@Autowired
	CompetenceService competenceService;
	@Autowired
	UserService userService;
	@Autowired
	UserSession userSession;

	@PostMapping("/requete")
	public String addRequete(Model model, @RequestParam Long idComp, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");
		Long idUser = user.getCodeUtilisateur();
		try {
			requeteService.createRequete(idComp, idUser, name, password);
			Competence comp = competenceService.getCompetence(idComp, name, password);
			model.addAttribute("competence", comp);
			return "competence";

		} catch (APiUSerAndCompetenceException e) {
			// TODO Auto-generated catch block

			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";
		}
	}

	@PostMapping("/valideRequete")
	public String valideRequete(Model model, @RequestParam Long idRequete, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");
		Long idUserComp = user.getCodeUtilisateur();
		try {
			requeteService.validerRequete(idRequete, idUserComp, name, password);
			int currentPage = 0;
			int pageSize = 2;

			Page<Requete> requetePage = requeteService.getRequetes(idUserComp, currentPage, pageSize, name, password);

			model.addAttribute("requetePage", requetePage);

			int totalPages = requetePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "requetes";

		} catch (APiUSerAndCompetenceException e) {
			// TODO Auto-generated catch block

			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";
		}
	}

	@GetMapping("/requetes")
	public String getRequetes(Model model, @RequestParam(required = false) Optional<Integer> size,
			@RequestParam(required = false) Optional<Integer> page, HttpServletRequest request) {

		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");
		Long idUserComp = user.getCodeUtilisateur();
		try {
			Page<Requete> requetePage = requeteService.getRequetes(idUserComp, currentPage, pageSize, name, password);
			List<Users> users = userService.getUsers(name, password);
			model.addAttribute("users", users);
			model.addAttribute("requetePage", requetePage);

			int totalPages = requetePage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "requetes";

		} catch (APiUSerAndCompetenceException e) {

			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";
		}
	}

}
