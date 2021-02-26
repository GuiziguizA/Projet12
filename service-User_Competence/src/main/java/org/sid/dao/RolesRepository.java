package org.sid.dao;

import java.util.Optional;

import org.sid.classe.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role, Long> {

	public Optional<Role> findByNom(String nom);

}
