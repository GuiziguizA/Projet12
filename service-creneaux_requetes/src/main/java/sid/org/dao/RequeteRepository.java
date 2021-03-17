package sid.org.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Requete;

public interface RequeteRepository extends JpaRepository<Requete, Long> {

	Optional<Requete> findByIdUserAndIdCompAndStatut(Long idUser, Long idComp, String statut);

	Page<Requete> findByIdUserComp(Long idUserComp, Pageable pageable);

	Page<Requete> findByIdUser(Long idUser, Pageable pageable);

}
