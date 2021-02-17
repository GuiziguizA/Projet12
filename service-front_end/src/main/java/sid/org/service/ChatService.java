package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Chat;

public interface ChatService {

	public Page<Chat> getChatUser(Long idUser, int page, int size);

}
