package sid.org.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sid.org.classe.Message;
import sid.org.dto.DateDto;
import sid.org.service.MessageService;

@Controller
public class MessageController {

	@Autowired
	MessageService messageService;

	@GetMapping("/messages")
	public String getChatsUser(Model model, @RequestParam Long idChat,
			@RequestParam(required = false) Optional<Integer> page,
			@RequestParam(required = false) Optional<Integer> size, DateDto dateDto) {
		Long idUser = 1L;
		int currentPage = page.orElse(0);
		int pageSize = size.orElse(2);

		Page<Message> pageMessage = messageService.getMessagesChats(idChat, currentPage, pageSize);
		model.addAttribute("pageMessage", pageMessage);
		model.addAttribute("idChat", idChat);
		int totalPages = pageMessage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "messages";

	}

}
