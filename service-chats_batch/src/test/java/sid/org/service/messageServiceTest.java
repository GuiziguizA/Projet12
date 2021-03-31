package sid.org.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.api.UserConnect;
import sid.org.classe.Chat;
import sid.org.classe.Message;
import sid.org.classe.Users;
import sid.org.dao.ChatRepository;
import sid.org.dao.MessageRepository;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class messageServiceTest {
	@Mock
	MessageRepository messageRepository;
	@Mock
	UserConnect userconnect;
	@Mock
	ChatRepository chatRepository;
	@InjectMocks
	MessageServiceImpl messageService;
	@Mock
	public RabbitTemplate template;

	@Test
	public void createMessageTest() throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {

		Message message = new Message(1L, new Date(), "hello", 1L, "bob", new Chat("en cours", 1L, 2L, 1L, 1L),
				"non lu");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue des fleurs", "bob", "92330");

		Mockito.when(chatRepository.findById(1L)).thenReturn(Optional.of(new Chat("en cours", 1L, 2L, 1L, 1L)));
		Mockito.when(userconnect.getUser(Mockito.anyLong())).thenReturn(user);
		messageService.createMessage(message, 1L, 1L);
	}

	@Test
	public void createMessageTestResultNotFound()
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {
		Message message = new Message(1L, new Date(), "hello", 1L, "bob", new Chat("en cours", 1L, 2L, 1L, 1L),
				"non lu");
		Mockito.when(chatRepository.findById(1L)).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			messageService.createMessage(message, 1L, 1L);

		});

		String expectedMessage = "chat introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void createMessageTestForbiddenException()
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {
		Message message = new Message(1L, new Date(), "hello", 1L, "bob", new Chat("en cours", 1L, 2L, 1L, 1L),
				"non lu");
		Mockito.when(chatRepository.findById(1L)).thenReturn(Optional.of(new Chat("en cours", 1L, 2L, 1L, 1L)));

		ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
			messageService.createMessage(message, 3L, 1L);

		});

		String expectedMessage = "interdit d'envoyer ce message";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void getMessageTest() throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {
		Message message = new Message(1L, new Date(), "hello", 1L, "bob", new Chat("en cours", 1L, 2L, 1L, 1L),
				"non lu");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue des fleurs", "bob", "92330");

		Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.of(message));
		Mockito.when(userconnect.getUser(Mockito.anyLong())).thenReturn(user);
		Message message1 = messageService.getMessage(1L, 1L);

		assertEquals(message1, message);

	}

	@Test
	public void getMessageTestResultNotFound()
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue des fleurs", "bob", "92330");

		Mockito.when(userconnect.getUser(Mockito.anyLong())).thenReturn(user);
		Mockito.when(messageRepository.findById(1L)).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			messageService.getMessage(1L, 1L);
		});

		String expectedMessage = "message introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void getMessagesTest() throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue des fleurs", "bob", "92330");
		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		Message message = new Message(1L, new Date(), "hello", 1L, "bob", new Chat("en cours", 1L, 2L, 1L, 1L),
				"non lu");
		Message message1 = new Message(1L, new Date(), "ca va", 1L, "bob", new Chat("en cours", 1L, 2L, 1L, 1L),
				"non lu");

		List<Message> messages = new ArrayList<>();
		messages.add(message);
		messages.add(message1);
		Page<Message> pageMessage = new PageImpl<Message>(messages);
		Pageable pageable = PageRequest.of(0, 2);

		Mockito.when(chatRepository.findById(1L)).thenReturn(Optional.of(chat));
		Mockito.when(messageRepository.findByChat(chat, pageable)).thenReturn(pageMessage);
		messageService.getMessages(1L, 0, 2);

		assertEquals(2, pageMessage.getSize());
	}

	@Test
	public void getMessagesTestResultNotFound()
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {

		Mockito.when(chatRepository.findById(1L)).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			messageService.getMessages(1L, 0, 2);
		});

		String expectedMessage = "chat introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}
