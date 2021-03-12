package sid.org.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Chat;
import sid.org.classe.Creneau;
import sid.org.classe.Users;
import sid.org.dto.ChatDateDtoObject;
import sid.org.service.ChatService;
import sid.org.service.CreneauService;
import sid.org.service.HttpService;
import sid.org.service.UserSession;

@Controller
public class CreneauController {

	@Autowired
	CreneauService creneauService;
	@Autowired
	ChatService chatService;
	@Autowired
	HttpService httpService;
	@Autowired
	UserSession userSession;

	private static final Logger logger = LoggerFactory.getLogger(CreneauController.class);

	@PostMapping("/creneau")
	public String createCreneau(Model model, @RequestParam Long idUser1, @RequestParam Long idUser,
			ChatDateDtoObject chatDateDtoObject, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");
		Long idUser2 = user.getCodeUtilisateur();
		try {
			logger.info("show the date " + chatDateDtoObject.getDateDto().toString());
			Chat chat = chatService.getChat(request, idUser, idUser1);
			chatDateDtoObject.setChat(chat);
			logger.info("show the chat " + chatDateDtoObject.getChat().toString());

			creneauService.createCreneau(chatDateDtoObject, idUser2, request);

			int currentPage = 0;
			int pageSize = 2;
			Page<Chat> pageChat = chatService.getChatUser(idUser, currentPage, pageSize, request);
			model.addAttribute("pageChat", pageChat);
			int totalPages = pageChat.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "chats";
		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);

			model.addAttribute("error", error);
			return "error";
		}

	}

	@GetMapping("/creneaux")
	public String getlistCreneau(Model model, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");

		Long idUser = user.getCodeUtilisateur();
		int currentPage = 0;
		int pageSize = 2;
		try {
			Page<Creneau> pageCreneau = creneauService.getCreneauxUser(idUser, currentPage, pageSize, request);
			Page<Creneau> pageCreneau1 = creneauService.getCreneauxUser1(idUser, currentPage, pageSize, request);

			model.addAttribute("pageCreneau", pageCreneau);
			model.addAttribute("pageCreneau1", pageCreneau1);
			int totalPages = pageCreneau.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			if (totalPages > 0) {
				List<Integer> pageNumbers1 = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers1", pageNumbers1);
			}
			return "creneaux";
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

	@GetMapping(value = "/creneau")
	public String User(Model model, @RequestParam Long idUser, @RequestParam Long idUser1,
			ChatDateDtoObject chatDateDtoObject) {

		model.addAttribute("idUser", idUser);
		model.addAttribute("idUser1", idUser1);

		return "creneau";
	}

	@PostMapping("/validateCreneau")
	public String valideCreneau(Model model, @RequestParam Long idCreneau, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");
		Long idUser = user.getCodeUtilisateur();
		try {

			creneauService.validerCreneau(idCreneau, idUser, request);

			return "redirect:/creneaux?page=0&size=2";

		} catch (HttpStatusCodeException e) {
			String error = e.getMessage();
			model.addAttribute("error", error);
			return "error";

		}
	}

}
