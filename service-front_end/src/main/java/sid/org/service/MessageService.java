package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Message;
import sid.org.dto.MessageDto;

public interface MessageService {

	public Page<Message> getMessagesChats(Long idChat, int page, int size, String name, String password);

	public void postMessagesChats(Long idChat, Long idUser, String username, String password, MessageDto message);

}
