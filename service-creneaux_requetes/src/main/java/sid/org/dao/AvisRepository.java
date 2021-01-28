package sid.org.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sid.org.classe.Avis;
import sid.org.classe.Creneau;

public interface AvisRepository extends JpaRepository<Avis, Long> {

	List<Avis> findByCreneau(Creneau creneau);

}
