package org.sid.service;

import java.util.List;
import java.util.Optional;

import org.sid.classe.Competence;
import org.sid.classe.Roles;
import org.sid.classe.User;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.RolesRepository;
import org.sid.dao.UserRepository;
import org.sid.dto.UserDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	CompetenceRepository competenceRepository;

	@Override
	public User createUser(UserDto userDto) throws ResultNotFoundException, EntityAlreadyExistException {
		Optional<Roles> role = rolesRepository.findByNom("user");
		if (!role.isPresent()) {
			throw new ResultNotFoundException("role doesn't exist");
		}

		Optional<User> userE = userRepository.findByMail(userDto.getMail());
		if (userE.isPresent()) {
			throw new EntityAlreadyExistException("mail use by an other user");
		}

		User user = new User();

		user.setAdresse(userDto.getAdresse());
		user.setCodePostal(userDto.getCodePostal());
		user.setMail(userDto.getMail());
		user.setMotDePasse(userDto.getMotDePasse());
		user.setNom(userDto.getNom());
		user.setRoles(role.get());
		userRepository.saveAndFlush(user);
		return user;
	}

	@Override
	public void deleteUser(Long id) throws ResultNotFoundException {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}

		if (!user.get().getCompetences().isEmpty()) {

			List<Competence> listUserCompetence = competenceRepository.finByUser(user.get());

			competenceRepository.deleteAll(listUserCompetence);

		}

		userRepository.delete(user.get());

	}

}
