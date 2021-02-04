package sid.org.api;

import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Chat;
import sid.org.dto.ChatDto;
import sid.org.exception.APiUSerAndCompetenceException;

public interface ChatConnect {

	public Chat getChat(Long idUser, Long idUser1) throws APiUSerAndCompetenceException;

	public void createChat(ChatDto chatDto, Long idRequete)
			throws HttpStatusCodeException, APiUSerAndCompetenceException;

}