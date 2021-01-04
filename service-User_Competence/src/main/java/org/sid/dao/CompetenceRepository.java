package org.sid.dao;

import java.util.List;
import java.util.Optional;

import org.sid.classe.Competence;
import org.sid.classe.User;
import org.sid.specification.CompetenceSpecificationImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompetenceRepository extends JpaRepository<Competence, Long>,JpaSpecificationExecutor<Competence> {

	@Query("select u from Competence u where u.user = :user")
	public List<Competence> finByUser(@Param("user") User user);

	/*
	 * @Modifying
	 * 
	 * @Query("delete u from  Competence u where u.user = :user") public
	 * List<Competence> deleteUSer(@Param("user") User user);
	 */

	public Optional<Competence> findByUserAndNom(User user, String nom);


}
