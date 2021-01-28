package sid.org.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import sid.org.api.UserConnect;
import sid.org.classe.Message;
import sid.org.classe.Users;
import sid.org.config.MessagingConfig;
import sid.org.dao.ChatRepository;
import sid.org.dao.MessageRepository;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@Component
public class MessageServiceImpl implements MessageService {
	@Autowired
	public RabbitTemplate template;
	@Autowired
	public MessageRepository messageRepository;
	@Autowired
	UserConnect userConnect;
	@Autowired
	ChatRepository chatRepository;

	private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);

	@Override
	public String createMessage(Message message, Long idUser, Long idUser1) throws APiUSerAndCompetenceException {
		userConnect.getUser(idUser);
		chatRepository.findByUserAndUser(idUser, idUser1);

		message.setId(UUID.randomUUID().toString());
		message.setDate(new Date());
		template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTIN_KEY, message);
		return "sucess";

	}

	@RabbitListener(queues = MessagingConfig.QUEUE)
	public void getMessage(Message message) {
		Message message1 = new Message();
		message.setStatut("non lu");
		messageRepository.saveAndFlush(message);
		logger.info(message.getContent());

	}

	@Override
	public Message getMessage(Long id, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {

		Optional<Message> message = messageRepository.findById(id);
		Users user = userConnect.getUser(idUser);

		if (user.getCodeUtilisateur() != message.get().getChat().getIdUser()
				|| user.getCodeUtilisateur() != message.get().getChat().getIdUser1()) {
			throw new ForbiddenException("interdiction de lire se message");
		}
		if (!message.isPresent()) {
			throw new ResultNotFoundException("message introuvable");
		}
		if (message.get().getStatut().equals("non lu")) {
			message.get().setStatut("lu");
		}
		messageRepository.saveAndFlush(message.get());

		return message.get();

	}

	@Override
	public Page<Message> getMessages(Long idUser) throws APiUSerAndCompetenceException {
		userConnect.getUser(idUser);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Message> messages = messageRepository.findByIdUser(idUser, pageable);

		return messages;

	}
}