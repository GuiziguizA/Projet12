package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Chat;
import sid.org.exception.ResultNotFoundException;

public interface ChatService {

	public Chat getUnChat(Long idUser, Long idUser1, Long idRequete) throws ResultNotFoundException;

	public Page<Chat> getChatsUser(Long idUser, int page, int size);

}
