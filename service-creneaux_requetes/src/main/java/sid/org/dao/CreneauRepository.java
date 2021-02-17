package sid.org.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Creneau;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {

	Optional<Creneau> findByIdUserAndIdUser1AndIdComp(Long idUser, Long idUser1, Long idComp);

	Page<Creneau> findByIdUser(Long idUser, Pageable pageable);

	Page<Creneau> findByIdUser1(Long idUser1, Pageable pageable);

	List<Creneau> findByIdUser(Long idUser);

	Page<Creneau> findByIdComp(Long codeCompetence, Pageable pageable);

}
