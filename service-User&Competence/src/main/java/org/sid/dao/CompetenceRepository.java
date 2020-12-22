package org.sid.dao;

import java.util.List;

import org.sid.classe.Competence;
import org.sid.classe.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {

	public List<Competence> finByUser(User user);

}
