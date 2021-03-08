package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Message;
import sid.org.dto.MessageDto;

public interface MessageService {

	public Page<Message> getMessagesChats(Long idChat, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

	public void postMessagesChats(Long idChat, Long idUser, HttpServletRequest request, MessageDto message)
			throws HttpStatusCodeException;

}
