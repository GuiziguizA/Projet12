package org.sid.dao;

import java.util.Optional;

import org.sid.classe.Users;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public interface UsersRepository extends JpaRepository<Users, Long> {
	@Query("select u from Users u where u.mail = :mail")
	public Optional<Users> findByMail(@Param("mail") String mail);

	@Query("select u from Users u where u.username = :username")
	public Optional<Users> findByUsername(String username);

}