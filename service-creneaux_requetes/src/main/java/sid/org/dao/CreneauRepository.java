package sid.org.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Creneau;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {

	Optional<Creneau> findByIdUserAndIdUser1AndIdComp(Long idUser, Long idUser1, Long idComp);

	List<Creneau> findByIdUserAndIdUser1AndIdCompAndStatut(Long idUser, Long idUser1, Long idComp, String statut);

	Page<Creneau> findByIdUserDemande(Long idUserDemande, Pageable pageable);

	Page<Creneau> findByIdUserRecoit(Long idUserRecoit, Pageable pageable);

	List<Creneau> findByIdUser(Long idUser);

	List<Creneau> findByIdChatAndStatut(Long idChat, String statut);

	Page<Creneau> findByIdComp(Long codeCompetence, Pageable pageable);

}
