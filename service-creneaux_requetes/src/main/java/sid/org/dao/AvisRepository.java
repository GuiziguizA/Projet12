package sid.org.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sid.org.classe.Avis;
import sid.org.classe.Creneau;

public interface AvisRepository extends JpaRepository<Avis, Long> {

	@Query("select u from Avis u where u.creneau = :creneau")
	Optional<Avis> findByCreneau(@Param("creneau") Creneau creneau);

	Page<Avis> findByIdComp(Long idComp, Pageable pageable);

	List<Avis> findByIdComp(Long idComp);
}
