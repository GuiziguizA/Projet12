package sid.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sid.org.classe.Chat;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.ChatService;

@RestController
public class ChatController {
	@Autowired
	ChatService chatService;

	@GetMapping("/chat")
	public Chat getChat(@RequestParam Long idUser, @RequestParam Long idUser1, @RequestParam Long idRequete)
			throws ResultNotFoundException {

		Chat chat = chatService.getUnChat(idUser, idUser1, idRequete);

		return chat;

	}

	@GetMapping("/chats")
	public Page<Chat> getChatsUser(@RequestParam Long idUser, @RequestParam int page, @RequestParam int size)
			throws ResultNotFoundException {

		Page<Chat> chatPage = chatService.getChatsUser(idUser, page, size);

		return chatPage;

	}

	/*
	 * @PostMapping("/chat") public Chat creerChat(@RequestBody ChatDto
	 * chatDto, @RequestParam Long idRequete,
	 * 
	 * @RequestParam Long codeMicroservice, @RequestParam Long idUser) throws
	 * EntityAlreadyExistException, APiUSerAndCompetenceException,
	 * ForbiddenException { Chat chat = chatService.creerUnChat(chatDto, idRequete,
	 * codeMicroservice, idUser); return chat;
	 * 
	 * }
	 */
	@DeleteMapping("/chat")
	public void supprimerChat(@RequestParam Long id, @RequestParam Long codeMicroservice)
			throws ResultNotFoundException {
		chatService.deleteUnChat(id);

	}
}
