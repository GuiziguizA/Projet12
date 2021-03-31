package sid.org.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Chat;
import sid.org.classe.TokenString;
import sid.org.config.RecupToken;
import sid.org.dto.ChatDto;

@Service
public class ChatConnectImpl implements ChatConnect {
	private static final Logger logger = LoggerFactory.getLogger(ChatConnectImpl.class);

	@Value("${api.url}")
	private String url;
	@Autowired
	private HeadersService headersService;

	@Override
	public int getChat(Long idUser, Long idUser1, Long idRequete) throws HttpStatusCodeException {
		String uri = url + "/compagny-chat_batch/chat?idUser1=" + idUser1 + "&idUser=" + idUser + "&idRequete="
				+ idRequete;
		RecupToken token = new RecupToken();
		TokenString tok = token.tokenString();
		HttpHeaders headers = headersService.createTokenHeaders(tok.getValue());
		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Chat> chat = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Chat.class);

			return 1;
		} catch (HttpStatusCodeException e) {
			return 0;

		}

	}

	@Override
	public void createChat(ChatDto chatDto, Long idRequete) {

		RecupToken token = new RecupToken();
		TokenString tok = token.tokenString();

		logger.info("idUser" + chatDto.getIdUser(), "idRequete" + idRequete);

		String uri = url + "/compagny-chat_batch/chat?idRequete=" + idRequete + "&codeMicroservice=1&idUser="
				+ chatDto.getIdUser();
		HttpHeaders headers = headersService.createTokenHeaders(tok.getValue());
		RestTemplate rt = new RestTemplate();
		try {
			ResponseEntity<Chat> chat = rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(chatDto, headers),
					Chat.class);
		} catch (HttpStatusCodeException e) {

			e.getMessage();
		}

	}
}
