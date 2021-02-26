package org.sid.service;

import java.util.List;
import java.util.Optional;

import org.sid.classe.Role;
import org.sid.classe.Users;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.RolesRepository;
import org.sid.dao.UsersRepository;
import org.sid.dto.UserDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	UsersRepository userRepository;
	@Autowired
	CompetenceRepository competenceRepository;
	@Autowired
	KeycloakService keycloakService;

	@Override
	public Users createUser(UserDto userDto) throws ResultNotFoundException, EntityAlreadyExistException {
		Optional<Role> role = rolesRepository.findByNom("user");
		if (!role.isPresent()) {
			throw new ResultNotFoundException("role doesn't exist");
		}

		Optional<Users> userE = userRepository.findByMail(userDto.getMail());
		if (userE.isPresent()) {
			throw new EntityAlreadyExistException("mail use by an other user");
		}

		Users user = new Users();

		user.setAdresse(userDto.getAdresse());
		user.setCodePostal(userDto.getCodePostal());
		user.setMail(userDto.getMail());
		user.setPassword(passwordcryptage(userDto.getMotDePasse()));
		user.setUsername(userDto.getNom());
		user.setRoles(role.get());
		keycloakService.createUserKeycloak(userDto.getNom(), userDto.getMail(), userDto.getMotDePasse());
		userRepository.saveAndFlush(user);
		return user;
	}

	@Override
	public void deleteUser(@RequestParam Long id) throws ResultNotFoundException {
		Optional<Users> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}
		/*
		 * if (!user.get().getCompetences().isEmpty()) {
		 * 
		 * competenceRepository.deleteUSer(user.get());
		 * 
		 * }
		 */
		userRepository.delete(user.get());

	}

	@Override
	public Users getUser(Long id) throws ResultNotFoundException {
		Optional<Users> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}
		return user.get();
	}

	@Override
	public List<Users> getUsers() throws ResultNotFoundException {
		List<Users> users = userRepository.findAll();

		return users;
	}

	@Override
	public UserDto getUserDto(Long id) throws ResultNotFoundException {
		Optional<Users> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}

		UserDto userDto = new UserDto();

		userDto.setAdresse(user.get().getAdresse());
		userDto.setCodePostal(user.get().getCodePostal());
		userDto.setMail(user.get().getMail());
		userDto.setMotDePasse(user.get().getPassword());
		userDto.setNom(user.get().getUsername());

		return userDto;
	}

	@Override
	public void updateUser(UserDto userDto, Optional<String> rol) throws ResultNotFoundException {
		if (!rol.isPresent()) {
			rol = Optional.of("user");
		}
		Optional<Role> role = rolesRepository.findByNom(rol.get());
		if (!role.isPresent()) {
			throw new ResultNotFoundException("role doesn't exist");

		}
		Optional<Users> userE = userRepository.findByMail(userDto.getMail());
		if (!userE.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}

		Users user = new Users();

		user.setAdresse(userDto.getAdresse());
		user.setCodePostal(userDto.getCodePostal());
		user.setMail(userDto.getMail());

		user.setUsername(userDto.getNom());
		user.setRoles(role.get());
		userRepository.saveAndFlush(user);

	}

	private String passwordcryptage(String password) {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password1 = passwordEncoder.encode(password);
		return password1;
	}

}