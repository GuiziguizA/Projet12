package sid.org.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sid.org.api.RequeteConnect;
import sid.org.classe.Chat;
import sid.org.classe.Requete;
import sid.org.dao.ChatRepository;
import sid.org.dto.ChatDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	ChatRepository chatRepository;
	@Autowired
	RequeteConnect requeteConnect;

	@Override
	public Chat creerUnChat(ChatDto chatDto, Long idRequete, Long CodeMicroservice, Long idUser)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException {
		Requete requete = requeteConnect.getRequete(idRequete, idUser);

		Optional<Chat> chat = chatRepository.findByUserAndUser(chatDto.getIdUser(), chatDto.getIdUser1());
		Optional<Chat> chat2 = chatRepository.findByUserAndUser(chatDto.getIdUser1(), chatDto.getIdUser());

		if (chat.isPresent() || chat2.isPresent()) {
			throw new EntityAlreadyExistException("chat existe deja");
		}

		Chat chat1 = new Chat();
		chat1.setIdUser(chatDto.getIdUser());
		chat1.setIdUser1(chatDto.getIdUser1());
		chat1.setStatut("enMarche");
		chat1.setIdRequete(idRequete);
		chat1.setCompetenceName(requete.getCompetenceNom());
		chat1.setUsername(requete.getUsername());
		chat1.setIdComp(requete.getIdComp());
		chatRepository.saveAndFlush(chat1);
		return chat1;

	}

	@Override
	public Chat getUnChat(Long idUser, Long idUser1) throws ResultNotFoundException {
		Optional<Chat> chat = chatRepository.findByUserAndUser(idUser, idUser1);
		Optional<Chat> chat1 = chatRepository.findByUserAndUser(idUser1, idUser);

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
	public Page<Chat> getChatsUser(Long idUser, int page, int size) throws ResultNotFoundException {
		List<Chat> chats = chatRepository.findByidUser(idUser);
		List<Chat> chats1 = chatRepository.findByidUser1(idUser);
		chats.addAll(chats1);
		Pageable pageable = PageRequest.of(page, size);
		Page<Chat> pageChat = new PageImpl<>(chats);
		return pageChat;

	}

	@Override
	public void deleteUnChat(Long id) throws ResultNotFoundException {
		Optional<Chat> chat = chatRepository.findById(id);
		if (!chat.isPresent()) {
			throw new ResultNotFoundException("chat introuvable");
		}
		chatRepository.delete(chat.get());

	}

}
