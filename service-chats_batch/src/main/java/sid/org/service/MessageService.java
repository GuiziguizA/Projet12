package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Message;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

public interface MessageService {

	public String createMessage(Message message, Long idUser, Long idUser1)
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException;

	public void getMessage(Message message);

	public Message getMessage(Long id, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException;

	public Page<Message> getMessages(Long idUser) throws APiUSerAndCompetenceException;

}
