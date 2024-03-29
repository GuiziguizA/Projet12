package sid.org.controller;

import java.util.List;
import java.util.Optional;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sid.org.classe.Message;
import sid.org.classe.Users;
import sid.org.dto.DateDto;
import sid.org.dto.MessageDto;
import sid.org.service.HttpService;
import sid.org.service.MessageService;

@Controller
public class MessageController {

	@Autowired
	MessageService messageService;
	@Autowired
	HttpService httpService;

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@GetMapping("/messages")
	public String getChatsUser(Model model, @RequestParam Long idChat,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false) Optional<Integer> size, DateDto dateDto, HttpServletRequest request,
			BindingResult result, MessageDto messageDto) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		Users user = (Users) session.getAttribute("user");
		Long idUser = user.getCodeUtilisateur();
		model.addAttribute("idUser", idUser);
		model.addAttribute("idChat", idChat);
		logger.info("l'idChat des messages du service front" + idChat);

		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);
		try {
			Page<Message> pageMessage = messageService.getMessagesChats(idChat, currentPage, pageSize, request);
			model.addAttribute("pageMessage", pageMessage);
			model.addAttribute("idChat", idChat);
			int totalPages = pageMessage.getTotalPages();
			if (totalPages > 0) {
				List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
			}
			return "messages";
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

	@PostMapping("/messages")
	public String postChatsUser(@RequestParam Long idChat, Model model, HttpServletRequest request,
			MessageDto messageDto, RedirectAttributes redirectAttrs) {
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		Users user = (Users) session.getAttribute("user");
		Long idUser = user.getCodeUtilisateur();
		/* Long idChat = (Long) model.getAttribute("idChat"); */
		logger.info("l'idChat des messages du service front post" + idChat);

		try {
			messageService.postMessagesChats(idChat, idUser, request, messageDto);
			String succes = "le message a été envoyé";
			redirectAttrs.addFlashAttribute("succes", succes);
			return "redirect:/messages?idChat=" + idChat + "&idUser=" + idUser;

		} catch (HttpStatusCodeException e) {
			String error = httpService.traiterLesExceptionsApi(e);

			model.addAttribute("error", error);
			return "error";

		}
	}

}
