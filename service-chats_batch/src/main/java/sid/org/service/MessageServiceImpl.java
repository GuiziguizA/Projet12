package sid.org.service;

import java.util.Date;
import java.util.Optional;

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
import sid.org.classe.Chat;
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
	public String createMessage(Message message, Long idUser, Long idChat)
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {
		userConnect.getUser(idUser);
		Optional<Chat> chat = chatRepository.findById(idChat);

		if (!chat.isPresent()) {
			throw new ResultNotFoundException("chat introuvable");
		}
		if (idUser != chat.get().getIdUser() && idUser != chat.get().getIdUser1()) {
			throw new ForbiddenException("interdit d'envoyer ce message");
		}

		Message message1 = new Message();

		message1.setDate(new Date());
		message1.setIdUser(idUser);
		message1.setChat(chat.get());
		message1.setUsername(userConnect.getUser(idUser).getUsername());

		message1.setContent(message.getContent());
		template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTIN_KEY3, message1);
		return "sucess";

	}

	@RabbitListener(queues = MessagingConfig.QUEUE3)
	public void getMessage(Message message) {
		Message message1 = new Message();
		message1.setChat(message.getChat());
		message1.setContent(message.getContent());
		message1.setDate(new Date());
		message1.setIdUser(message.getIdUser());
		message1.setUsername(message.getUsername());
		message1.setStatut("non lu");
		messageRepository.saveAndFlush(message1);
		logger.info("Voici le message envoyé" + message1.toString());

	}

	@Override
	public Message getMessage(Long id, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {

		Optional<Message> message = messageRepository.findById(id);
		Users user = userConnect.getUser(idUser);
		if (!message.isPresent()) {
			throw new ResultNotFoundException("message introuvable");
		}
		if (user.getCodeUtilisateur() == message.get().getChat().getIdUser()
				|| user.getCodeUtilisateur() == message.get().getChat().getIdUser1()) {

			if (message.get().getStatut().equals("non lu")) {
				message.get().setStatut("lu");
			}
			messageRepository.saveAndFlush(message.get());

			return message.get();
		} else {

			throw new ForbiddenException("interdiction de lire se message");
		}
	}

	@Override
	public Page<Message> getMessages(Long idChat, int page, int size) throws ResultNotFoundException {
		Optional<Chat> chat = chatRepository.findById(idChat);
		if (!chat.isPresent()) {
			throw new ResultNotFoundException("chat introuvable");
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<Message> messages = messageRepository.findByChat(chat.get(), pageable);

		return messages;

	}
}