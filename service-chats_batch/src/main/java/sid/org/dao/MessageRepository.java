package sid.org.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
