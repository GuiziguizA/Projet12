package sid.org.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Creneau;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {

	Optional<Creneau> findByIdUserAndIdComp(Long idUser, Long idComp);

	Page<Creneau> findByIdUser(Long idUser, Pageable pageable);

}
