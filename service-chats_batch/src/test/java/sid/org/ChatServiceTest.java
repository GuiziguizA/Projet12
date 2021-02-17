package sid.org;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.classe.Chat;
import sid.org.dao.ChatRepository;
import sid.org.dto.ChatDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.ChatService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
class ChatServiceTest {
	@MockBean
	ChatRepository chatRepository;
	@Autowired
	ChatService chatService;

	@Test
	public void creerUnChattest()
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException {

		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		ChatDto chatDto = new ChatDto(1L, 2L);

		Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.empty());

		Chat chat1 = chatService.creerUnChat(chatDto, 1L, 2L, 3L);

		assertEquals(chat.getIdUser1(), chat1.getIdUser1());

	}

	@Test
	public void creerUnChattestEntityAlreadyExistException() throws EntityAlreadyExistException {

		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		ChatDto chatDto = new ChatDto(1L, 2L);

		Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.of(chat));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			chatService.creerUnChat(chatDto, 1L, 2L, 3L);

		});

		String expectedMessage = "chat existe deja";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getUnChatTestResultNotFoundException() {

		Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			chatService.getUnChat(1L, 2L);
		});

		String expectedMessage = "chat introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getUnChatTest() throws ResultNotFoundException {
		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.of(chat));

		Chat chat1 = chatService.getUnChat(1L, 2L);

		assertEquals(chat.getIdUser(), chat1.getIdUser());

	}

	@Test
	public void deleteChatTest() throws ResultNotFoundException {
		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		Mockito.when(chatRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(chat));
		Mockito.doNothing().when(chatRepository).delete(chat);
		chatService.deleteUnChat(1L);

	}

	@Test
	public void deleteChatTestResultNotFoundException() {

		Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(), Mockito.anyLong()))
				.thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			chatService.deleteUnChat(1L);
		});

		String expectedMessage = "chat introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}
}
