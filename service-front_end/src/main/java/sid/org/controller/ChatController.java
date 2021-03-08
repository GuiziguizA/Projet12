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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Chat;
import sid.org.classe.Users;
import sid.org.dto.ChatDateDtoObject;
import sid.org.service.ChatService;
import sid.org.service.HttpService;
import sid.org.service.UserSession;

@Controller
public class ChatController {
	@Autowired
	ChatService chatService;
	@Autowired
	UserSession userSession;
	@Autowired
	HttpService httpService;

	@GetMapping("/chats")
	public String getChatsUser(Model model, ChatDateDtoObject chatDateDtoObject,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false) Optional<Integer> size, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("username");
		String password = (String) session.getAttribute("password");
		userSession.loadUserInSession(request, name, password);
		Users user = (Users) session.getAttribute("user");
		Long idUser = user.getCodeUtilisateur();
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);

		try {
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
			if (error == "error") {
				return "redirect:/logout";
			} else {
				model.addAttribute("error", error);
				return "error";
			}
		}

	}

}
