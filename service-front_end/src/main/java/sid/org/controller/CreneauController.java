package sid.org.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public String createCreneau(Model model, @RequestBody DateDto dateDto, @RequestParam Chat chat)
			throws ForbiddenException {

		creneauService.createCreneau(chat, dateDto);
		Long idUser = 1L;
		int currentPage = 0;
		int pageSize = 2;

		Page<Chat> pageChat = chatService.getChatUser(idUser, currentPage, pageSize);
		model.addAttribute("pageChat", pageChat);
		int totalPages = pageChat.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "chats";

	}

	@GetMapping("/creneaux")
	public String getlistCreneau(Model model) throws ForbiddenException, APiUSerAndCompetenceException {

		Long idUser = 1L;
		int currentPage = 0;
		int pageSize = 2;
		Page<Creneau> pageCreneau = creneauService.getCreneauxUser(idUser, currentPage, pageSize);

		model.addAttribute("pageCreneau", pageCreneau);
		int totalPages = pageCreneau.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "creneaux";

	}

}
