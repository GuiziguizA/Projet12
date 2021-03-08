package sid.org.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Chat;
import sid.org.classe.Creneau;
import sid.org.dto.ChatDateDtoObject;
import sid.org.service.ChatService;
import sid.org.service.CreneauService;
import sid.org.service.HttpService;

@Controller
public class CreneauController {

	@Autowired
	CreneauService creneauService;
	@Autowired
	ChatService chatService;
	@Autowired
	HttpService httpService;
	private static final Logger logger = LoggerFactory.getLogger(CreneauController.class);

	@PostMapping("/creneau")
	public String createCreneau(Model model, ChatDateDtoObject chatDateDtoObject, HttpServletRequest request) {
		try {
			logger.info("show the date " + chatDateDtoObject.getDateDto().toString());
			logger.info("show the chat " + chatDateDtoObject.getChat().toString());
			creneauService.createCreneau(chatDateDtoObject, request);
			Long idUser = 1L;
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

		Long idUser = 1L;
		int currentPage = 0;
		int pageSize = 2;
		try {
			Page<Creneau> pageCreneau = creneauService.getCreneauxUser(idUser, currentPage, pageSize, request);

			model.addAttribute("pageCreneau", pageCreneau);
			int totalPages = pageCreneau.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
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

}
