package sid.org.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Creneau;
import sid.org.classe.Users;
import sid.org.dto.AvisDto;
import sid.org.service.AvisService;
import sid.org.service.CreneauService;
import sid.org.service.HttpService;
import sid.org.service.UserSession;

@Controller
public class AvisController {
	@Autowired
	AvisService avisService;
	@Autowired
	HttpService httpService;
	@Autowired
	UserSession userSession;
	@Autowired
	CreneauService creneauService;

	@PostMapping("/avis")
	public String createAvis(Model model, @RequestParam Long idCreneau, AvisDto avisDto, HttpServletRequest request) {
		try {

			HttpSession session = request.getSession();
			String name = (String) session.getAttribute("username");
			String password = (String) session.getAttribute("password");
			userSession.loadUserInSession(request, name, password);
			Users user = (Users) session.getAttribute("user");
			Long idUser = user.getCodeUtilisateur();
			Creneau creneau = creneauService.getCreneau(idCreneau, request);
			avisDto.setCreneau(creneau);
			avisService.createAvis(avisDto, request, idUser);

			String succes = "l'avis a été ajouté";
			model.addAttribute("succes", succes);
			return "redirect:/creneaux?page=0&size=2";
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

	@GetMapping("/avis")
	public String getAvis(@RequestParam Long id, AvisDto avisDto, Model model) {
		model.addAttribute("id", id);
		return "avis";
	}

}
