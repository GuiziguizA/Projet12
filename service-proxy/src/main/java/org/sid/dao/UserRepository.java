package org.sid.dao;

import java.util.Optional;

import org.sid.classe.User;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u where u.mail = :mail")
	public Optional<User> findByMail(@Param("mail") String mail);

}
