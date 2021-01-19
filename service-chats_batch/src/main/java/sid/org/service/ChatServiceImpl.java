package sid.org.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sid.org.classe.Chat;
import sid.org.dao.ChatRepository;
import sid.org.dto.ChatDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	ChatRepository chatRepository;

	@Override
	public Chat creerUnChat(ChatDto chatDto) throws EntityAlreadyExistException {

		Optional<Chat> chat = chatRepository.findByUserAndUser(chatDto.getIdUser(), chatDto.getIdUser1());

		if (chat.isPresent()) {
			throw new EntityAlreadyExistException("chat introuvable");
		}

		Chat chat1 = new Chat();
		chat1.setIdUser(chatDto.getIdUser());
		chat1.setIdUser1(chatDto.getIdUser1());
		chat1.setStatut("enMarche");

		chatRepository.saveAndFlush(chat1);
		return chat1;

	}

	@Override
	public Chat getUnChat(Long idUser, Long idUser1) throws ResultNotFoundException {
		Optional<Chat> chat = chatRepository.findByUserAndUser(idUser, idUser1);
		if (!chat.isPresent()) {
			throw new ResultNotFoundException("le chat est inexistant");
		}

		return chat.get();
	}

	@Override
	public void deleteUnChat(Long id) throws ResultNotFoundException {
		Optional<Chat> chat = chatRepository.findById(id);
		if (!chat.isPresent()) {
			throw new ResultNotFoundException("le chat est inexistant");
		}
		chatRepository.delete(chat.get());

	}
}
