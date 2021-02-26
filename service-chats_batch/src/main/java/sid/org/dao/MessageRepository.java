package sid.org.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Chat;
import sid.org.classe.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	Page<Message> findByChat(Chat chat, Pageable pageable);

}
