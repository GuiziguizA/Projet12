package sid.org.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Chat;
import sid.org.dto.ChatDto;

@Service
public class ChatConnectImpl implements ChatConnect {

	@Override
	public Chat getChat(Long idUser, Long idUser1) {
		final String uri = "http://localhost:8089/compagny-creneaux_requetes/creneau";

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Chat> user = rt.getForEntity(uri, Chat.class);
			return user.getBody();
		} catch (HttpStatusCodeException e) {
			return null;
		}

	}

	@Override
	public void createChat(ChatDto chatDto) throws HttpStatusCodeException {

		RestTemplate rt = new RestTemplate();
		final String uri = "http://localhost:8089/compagny-creneaux_requetes/creneau";

		ResponseEntity<Chat> chat = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(chatDto), Chat.class);

	}
}
