package org.sid.service;

import java.util.Optional;

import org.sid.classe.Roles;
import org.sid.classe.User;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.RolesRepository;
import org.sid.dao.UserRepository;
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
		user.setPassword(passwordcryptage(userDto.getMotDePasse()));
		user.setUsername(userDto.getNom());
		user.setRoles(role.get());
		userRepository.saveAndFlush(user);
		return user;
	}

	
	
	
	@Override
	public void deleteUser(@RequestParam Long id) throws ResultNotFoundException {
		Optional<User> user = userRepository.findById(id);

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
	public User getUser(Long id) throws ResultNotFoundException {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}
		return user.get();
	}

	@Override
	public UserDto getUserDto(Long id) throws ResultNotFoundException {
		Optional<User> user = userRepository.findById(id);

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
	public void updateUser(UserDto userDto,Optional<String>rol) throws ResultNotFoundException {
		if(!rol.isPresent()) {
			rol=Optional.of("user");
		}
		Optional<Roles> role = rolesRepository.findByNom(rol.get());
		if (!role.isPresent()) {
			throw new ResultNotFoundException("role doesn't exist");
		
		}
		Optional<User> userE = userRepository.findByMail(userDto.getMail());
		if (!userE.isPresent()) {
			throw new ResultNotFoundException("user doesn't exist");
		}

		User user = new User();

		user.setAdresse(userDto.getAdresse());
		user.setCodePostal(userDto.getCodePostal());
		user.setMail(userDto.getMail());
		
		user.setUsername(userDto.getNom());
		user.setRoles(role.get());
		userRepository.saveAndFlush(user);

	}
	
	
	private String passwordcryptage(String password) {
	       
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String password1 =  passwordEncoder.encode(password);
        return password1;
}
	
	

}