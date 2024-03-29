package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.Page.RestResponsePage;
import sid.org.classe.Chat;
import sid.org.config.RequestFactory;

@Service
public class ChatServiceImpl implements ChatService {
	@Value("${api.url}")
	private String url;
	private static final Logger logger = LoggerFactory.getLogger(ChatServiceImpl.class);

	private final RequestFactory requestFactory;
	@Autowired
	private HeadersService headersService;

	@Autowired
	public ChatServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public Page<Chat> getChatUser(Long idUser, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);
		ParameterizedTypeReference<RestResponsePage<Chat>> responseType = new ParameterizedTypeReference<RestResponsePage<Chat>>() {
		};
		String uri = url + "/service-chat_batch/chats?idUser=" + idUser + "&page=" + page + "&size=" + size;

		ResponseEntity<RestResponsePage<Chat>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Chat> chatPage = result.getBody();
		return chatPage;

	}

	@Override
	public Chat getChat(HttpServletRequest request, Long idUser, Long idUser1, Long idRequete)
			throws HttpStatusCodeException {
		String uri = url + "/service-chat_batch/chat?idUser1=" + idUser1 + "&idUser=" + idUser + "&idRequete="
				+ idRequete;

		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = headersService.createTokenHeaders(request);

		ResponseEntity<Chat> chat = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Chat.class);
		return chat.getBody();

	}

}
