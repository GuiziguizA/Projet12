package sid.org.service;

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
	public ChatServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public Page<Chat> getChatUser(Long idUser, int page, int size) {

		RestTemplate rt = requestFactory.getRestTemplate();
		HttpHeaders headers = new HttpHeaders();
		ParameterizedTypeReference<RestResponsePage<Chat>> responseType = new ParameterizedTypeReference<RestResponsePage<Chat>>() {
		};
		String uri = url + "/compagny-chat_batch/chats?idUser=" + idUser + "&page=" + page + "&size=" + size;
		String uri1 = "http://localhost:8089/compagny-chat_batch/chats?idUser=1&page=0&size=2";
		ResponseEntity<RestResponsePage<Chat>> result = rt.exchange(uri1, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Chat> chatPage = result.getBody();
		return chatPage;

	}

}