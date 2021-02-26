package sid.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sid.org.classe.Message;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.MessageService;

@RestController
public class MesageController {

	@Autowired
	MessageService messageService;

	@PostMapping("/message")
	public String createMessage(@RequestBody Message message, @RequestParam Long idUser, @RequestParam Long idChat)
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {
		String sucess = messageService.createMessage(message, idUser, idChat);

		return sucess;

	}

	@GetMapping("/message")
	public Message getMessage(@RequestParam Long id, @RequestParam Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {
		Message message = messageService.getMessage(id, idUser);
		return message;

	}

	@GetMapping("/messages")
	public Page<Message> getMessages(@RequestParam Long idChat, @RequestParam int page, @RequestParam int size)
			throws ResultNotFoundException, APiUSerAndCompetenceException {
		Page<Message> messages = messageService.getMessages(idChat, page, size);
		return messages;

	}

}
