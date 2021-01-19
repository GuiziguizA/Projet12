package sid.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import sid.org.classe.Chat;
import sid.org.dto.ChatDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.ChatService;

public class ChatController {
	@Autowired
	ChatService chatService;

	@GetMapping("/chat")
	public Chat getChat(@RequestParam Long idUser, @RequestParam Long idUser1) throws ResultNotFoundException {

		Chat chat = chatService.getUnChat(idUser, idUser1);

		return chat;

	}

	@PostMapping("/chat")
	public Chat creerChat(@RequestBody ChatDto chatDto) throws EntityAlreadyExistException {
		Chat chat = chatService.creerUnChat(chatDto);
		return chat;

	}

	@DeleteMapping("/chat")
	public void supprimerChat(@RequestParam Long id) throws ResultNotFoundException {
		chatService.deleteUnChat(id);

	}
}
