package sid.org.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sid.org.classe.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {
	@Query("select u from Chat u where u.idUser = :idUser and u.idUser1 = :idUser1 and u.idRequete = :idRequete and u.statut = :statut")
	Optional<Chat> findByUserAndUser1AndidRequeteAndStatut(@Param("idUser") Long idUser, @Param("idUser1") Long idUser1,
			@Param("idRequete") Long idRequete, @Param("statut") String statut);

	Page<Chat> findByIdUserOrIdUser1(Long idUser, Long idUser1, Pageable pageable);

	List<Chat> findByidUser1(Long idUser1);

}
