package sid.org.classe;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue
	String id;
	Date date;
	String content;

	Long idUser;
	@ManyToOne
	@JoinColumn(name = "ID_CHAT")
	private Chat chat;
	private String statut;

}