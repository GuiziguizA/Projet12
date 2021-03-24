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
import sid.org.classe.Message;
import sid.org.config.RequestFactory;
import sid.org.dto.MessageDto;

@Service
public class MessageServiceImpl implements MessageService {

	@Value("${api.url}")
	private String url;
	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	@Autowired
	HeadersService headersService;

	private final RequestFactory requestFactory;

	@Autowired
	public MessageServiceImpl(RequestFactory requestFactory) {
		this.requestFactory = requestFactory;
	}

	@Override
	public Page<Message> getMessagesChats(Long idChat, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException {
		HttpHeaders headers = headersService.createTokenHeaders(request);
		RestTemplate rt = requestFactory.getRestTemplate();

		ParameterizedTypeReference<RestResponsePage<Message>> responseType = new ParameterizedTypeReference<RestResponsePage<Message>>() {
		};
		String uri = url + "/compagny-chat_batch/messages?idChat=" + idChat + "&page=" + page + "&size=" + size;

		ResponseEntity<RestResponsePage<Message>> result = rt.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers),
				responseType);
		Page<Message> messagePage = result.getBody();
		return messagePage;

	}

	@Override
	public void postMessagesChats(Long idChat, Long idUser, HttpServletRequest request, MessageDto message)
			throws HttpStatusCodeException {

		logger.info("idChat" + idChat + "idUser" + idUser + "message" + message.getContent());
		HttpHeaders headers = headersService.createTokenHeaders(request);
		RestTemplate rt = requestFactory.getRestTemplate();

		String uri = url + "/compagny-chat_batch/message?idUser=" + idUser + "&idChat=" + idChat;

		rt.exchange(uri, HttpMethod.POST, new HttpEntity<>(message, headers), String.class);

	}

}
