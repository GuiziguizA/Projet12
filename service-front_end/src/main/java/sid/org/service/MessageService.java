package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Message;

public interface MessageService {

	public Page<Message> getMessagesChats(Long idChat, int page, int size);

}
