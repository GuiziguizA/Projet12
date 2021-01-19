package sid.org.service;

import sid.org.classe.Chat;
import sid.org.dto.ChatDto;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ResultNotFoundException;

public interface ChatService {

	public Chat creerUnChat(ChatDto chatDto) throws EntityAlreadyExistException;

	public Chat getUnChat(Long idUser, Long idUser1) throws ResultNotFoundException;

	public void deleteUnChat(Long id) throws ResultNotFoundException;

}
