package sid.org.classe;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	Long id;
	Date date;
	String content;
	Long idUser;

	private Chat chat;
	private String statut;

}