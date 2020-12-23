package org.sid.dao;

import java.util.List;

import org.sid.classe.Competence;
import org.sid.classe.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {

	@Query("select u from Competence u where u.user = :user")
	public List<Competence> finByUser(@Param("user") User user);

	@Query("delete u from Competence u where u.user = :user")
	public List<Competence> deleteUSer(@Param("user") User user);

}
