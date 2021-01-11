package org.sid.dao;

import java.util.List;
import java.util.Optional;

import org.sid.classe.Competence;
import org.sid.classe.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetenceRepository extends JpaRepository<Competence, Long>, JpaSpecificationExecutor<Competence> {

	@Query("select u from Competence u where u.user = :user")
	public List<Competence> finByUser(@Param("user") Users user);

	/*
	 * @Modifying
	 * 
	 * @Query("delete u from  Competence u where u.user = :user") public
	 * List<Competence> deleteUSer(@Param("user") User user);
	 */

	public Optional<Competence> findByUserAndNom(Users user, String nom);

}
