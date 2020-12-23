package org.sid.dao;

import java.util.Optional;

import org.sid.classe.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {

	public Optional<Roles> findByNom(String nom);

}
