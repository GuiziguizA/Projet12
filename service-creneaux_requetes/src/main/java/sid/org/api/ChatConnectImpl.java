package sid.org.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger logger = LoggerFactory.getLogger(ChatConnectImpl.class);

	@Value("${api.url}")
	private String url;

	@Override
	public Chat getChat(Long idUser, Long idUser1) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-chat_batch/chat?idUser1=" + idUser1 + "&idUser=" + idUser;

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Chat> chat = rt.getForEntity(uri, Chat.class);
			return chat.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("l'api getchat ne marche pas et l'erreure est : " + e.getMessage());
		}

	}

	@Override
	public void createChat(ChatDto chatDto, Long idRequete) throws APiUSerAndCompetenceException {
		logger.info("idUser" + chatDto.getIdUser(), "idRequete" + idRequete);

		String uri = url + "/compagny-chat_batch/chat?idRequete=" + idRequete + "&codeMicroservice=1&idUser="
				+ chatDto.getIdUser();

		RestTemplate rt = new RestTemplate();
		try {
			ResponseEntity<Chat> chat = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(chatDto), Chat.class);
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(
					"l'api createchat ne marche pas et l'erreure est : " + e.getMessage());
		}

	}
}
