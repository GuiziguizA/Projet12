package sid.org.api;

import sid.org.dto.ChatDto;

public interface ChatConnect {

	public int getChat(Long idUser, Long idUser1, Long idRequete);

	public void createChat(ChatDto chatDto, Long idRequete);

}