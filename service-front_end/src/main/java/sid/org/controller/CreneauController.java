package sid.org.controller;

import java.util.List;
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

import sid.org.classe.Chat;
import sid.org.classe.Creneau;
import sid.org.dto.DateDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.service.ChatService;
import sid.org.service.CreneauService;

@Controller
public class CreneauController {

	@Autowired
	CreneauService creneauService;
	@Autowired
	ChatService chatService;

	@PostMapping("/creneau")
	public String createCreneau(Model model, DateDto dateDto, @RequestParam Chat chat, HttpServletRequest request)
			throws ForbiddenException {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		creneauService.createCreneau(chat, dateDto, name, password);
		Long idUser = 1L;
		int currentPage = 0;
		int pageSize = 2;

		Page<Chat> pageChat = chatService.getChatUser(idUser, currentPage, pageSize, name, password);
		model.addAttribute("pageChat", pageChat);
		int totalPages = pageChat.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "chats";

	}

	@GetMapping("/creneaux")
	public String getlistCreneau(Model model, HttpServletRequest request)
			throws ForbiddenException, APiUSerAndCompetenceException {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		Long idUser = 1L;
		int currentPage = 0;
		int pageSize = 2;
		Page<Creneau> pageCreneau = creneauService.getCreneauxUser(idUser, currentPage, pageSize, name, password);

		model.addAttribute("pageCreneau", pageCreneau);
		int totalPages = pageCreneau.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "creneaux";

	}

}
