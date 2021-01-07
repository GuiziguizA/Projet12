package org.sid.config;

import java.util.Optional;

import org.sid.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Modification du UserDetails de spring Security
	 * 
	 * 
	 * @param mail
	 * @return User.withUsername(utilisateur.get().getMail()) .password(
	 *         utilisateur.get().getPassword())
	 *         .roles(utilisateur.get().getRole().getNom()).build();
	 */
	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Optional<org.sid.classe.User> user = userRepository.findByMail(mail);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException("l'utilisateurn'existe pas ");
		}

		return User.withUsername(user.get().getMail()).password(user.get().getPassword())
				.roles(user.get().getRoles().getNom()).build();
	}

}
