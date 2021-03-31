package sid.org.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.classe.Chat;
import sid.org.dao.ChatRepository;
import sid.org.exception.ResultNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class ChatServiceTest {
	@Mock
	ChatRepository chatRepository;
	@InjectMocks
	ChatServiceImpl chatService;

	/*
	 * @Test public void creerUnChattest() throws EntityAlreadyExistException,
	 * APiUSerAndCompetenceException, ForbiddenException {
	 * 
	 * Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L); ChatDto chatDto = new
	 * ChatDto(1L,2L,3L,4L);
	 * 
	 * Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(),
	 * Mockito.anyLong())) .thenReturn(Optional.empty());
	 * 
	 * Chat chat1 = chatService.creerUnChat(chatDto, 1L, 2L, 3L);
	 * 
	 * assertEquals(chat.getIdUser1(), chat1.getIdUser1());
	 * 
	 * }
	 */

	/*
	 * @Test public void creerUnChattestEntityAlreadyExistException() throws
	 * EntityAlreadyExistException {
	 * 
	 * Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L); ChatDto chatDto = new
	 * ChatDto(1L,2L,3L,4L);
	 * 
	 * Mockito.when(chatRepository.findByUserAndUser(Mockito.anyLong(),
	 * Mockito.anyLong())) .thenReturn(Optional.of(chat));
	 * 
	 * EntityAlreadyExistException exception =
	 * assertThrows(EntityAlreadyExistException.class, () -> {
	 * chatService.creerUnChat(chatDto);
	 * 
	 * });
	 * 
	 * String expectedMessage = "chat existe deja"; String actualMessage =
	 * exception.getMessage();
	 * 
	 * assertTrue(actualMessage.contains(expectedMessage));
	 * 
	 * }
	 */

	@Test
	public void getUnChatTestResultNotFoundException() {

		Mockito.when(chatRepository.findByUserAndUser1AndidRequeteAndStatut(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			chatService.getUnChat(1L, 2L, 1L);
		});

		String expectedMessage = "chat introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getUnChatTest() throws ResultNotFoundException {
		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		Mockito.when(chatRepository.findByUserAndUser1AndidRequeteAndStatut(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())).thenReturn(Optional.of(chat));

		Chat chat1 = chatService.getUnChat(1L, 2L, 1L);

		assertEquals(chat.getIdUser(), chat1.getIdUser());

	}

	@Test
	public void getChatsUserTest() throws ResultNotFoundException {
		Chat chat = new Chat("ouvert", 1L, 2L, 1L, 1L);
		Chat chat1 = new Chat("ouvert", 1L, 2L, 1L, 1L);
		List<Chat> chats = new ArrayList<>();
		chats.add(chat);
		chats.add(chat1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Chat> pageChat = new PageImpl<Chat>(chats);

		Mockito.when(chatRepository.findByIdUserOrIdUser1(1L, 1L, pageable)).thenReturn(pageChat);

		Page<Chat> chatsPage = chatService.getChatsUser(1L, 0, 2);

	}
}
