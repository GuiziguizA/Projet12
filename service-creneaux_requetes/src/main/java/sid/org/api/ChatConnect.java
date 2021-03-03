package sid.org.api;

import sid.org.classe.Chat;
import sid.org.dto.ChatDto;

public interface ChatConnect {

	public Chat getChat(Long idUser, Long idUser1);

	public void createChat(ChatDto chatDto, Long idRequete);

}