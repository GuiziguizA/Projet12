package sid.org.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Chat;
import sid.org.dto.ChatDto;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class ChatConnectImpl implements ChatConnect {

	@Value("${api.url}")
	private String url;

	@Override
	public Chat getChat(Long idUser, Long idUser1) throws APiUSerAndCompetenceException {
		String uri = url + "compagny-chats_batch/creneau";

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Chat> chat = rt.getForEntity(uri, Chat.class);
			return chat.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("Impossible d'effectuer cette action");
		}

	}

	@Override
	public void createChat(ChatDto chatDto) throws HttpStatusCodeException, APiUSerAndCompetenceException {
		String uri = url + "compagny-chats_batch/creneau";

		RestTemplate rt = new RestTemplate();
		try {
			ResponseEntity<Chat> chat = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(chatDto), Chat.class);
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("Impossible d'effectuer cette action");
		}

	}
}
