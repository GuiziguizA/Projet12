package sid.org.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sid.org.classe.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	@Query("select u from Chat u where u.user = :user and u.user1 = :user1")
	Optional<Chat> findByUserAndUser(@Param("user") Long idUser, @Param("user1") Long idUser1);

}
