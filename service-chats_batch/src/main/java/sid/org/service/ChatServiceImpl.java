package sid.org.service;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sid.org.api.RequeteConnect;
import sid.org.classe.Chat;
import sid.org.config.MessagingConfig;
import sid.org.dao.ChatRepository;
import sid.org.dto.ChatDto;
import sid.org.exception.ResultNotFoundException;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	RequeteConnect requeteConnect;

	@RabbitListener(queues = MessagingConfig.QUEUE1)
	public void creerUnChat(ChatDto chatDto) {

		Optional<Chat> chat = chatRepository.findByUserAndUser1AndidRequeteAndStatut(chatDto.getIdUser(),
				chatDto.getIdUser1(), chatDto.getIdRequete(), "enMarche");
		if (chat.isPresent()) {

		} else {

			Chat chat1 = new Chat();
			chat1.setIdUser(chatDto.getIdUser());
			chat1.setIdUser1(chatDto.getIdUser1());
			chat1.setStatut("enMarche");
			chat1.setIdRequete(chatDto.getIdRequete());
			chat1.setCompetenceName(chatDto.getCompetenceNom());
			chat1.setUsername(chatDto.getUsername());
			chat1.setUsername1(chatDto.getUsername1());
			chat1.setIdComp(chatDto.getIdComp());
			chatRepository.saveAndFlush(chat1);
		}
	}

	@Override
	public Chat getUnChat(Long idUser, Long idUser1, Long idRequete) throws ResultNotFoundException {
		Optional<Chat> chat = chatRepository.findByUserAndUser1AndidRequeteAndStatut(idUser, idUser1, idRequete,
				"enMarche");
		Optional<Chat> chat1 = chatRepository.findByUserAndUser1AndidRequeteAndStatut(idUser1, idUser, idRequete,
				"enMarche");

		if (!chat.isPresent() && !chat1.isPresent()) {
			throw new ResultNotFoundException("chat introuvable");
		}
		if (chat.isPresent() && !chat1.isPresent()) {
			return chat.get();
		} else {
			return chat1.get();
		}

	}

	@Override
	public Page<Chat> getChatsUser(Long idUser, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id");
		Page<Chat> pageChat = chatRepository.findByIdUserOrIdUser1(idUser, idUser, pageable);

		return pageChat;

	}

	@RabbitListener(queues = MessagingConfig.QUEUE2)
	public void updateChat(Long idChat) throws ResultNotFoundException {

		Optional<Chat> chat1 = chatRepository.findById(idChat);
		chat1.get().setStatut("cloturé");
		if (!chat1.isPresent()) {
			throw new ResultNotFoundException("le chat n'existe pas");
		}
		chatRepository.saveAndFlush(chat1.get());
	}

}
