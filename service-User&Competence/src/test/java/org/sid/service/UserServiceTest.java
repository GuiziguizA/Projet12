package org.sid.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

@ContextConfiguration
class UserServiceTest {

	@MockBean
	UserRepository userRepository;
	@MockBean
	RolesRepository rolesRepository;
	@Autowired
	UserService userService;
	@MockBean
	CompetenceRepository competenceRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void createUserTest() throws ResultNotFoundException, EntityAlreadyExistException {
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

		User user1 = userService.createUser(userDto);

		assertEquals(user.getMail(), user1.getMail());
	}

	@Test
	public void createUserTestResultNotFounfRole() throws ResultNotFoundException, EntityAlreadyExistException {
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			User user1 = userService.createUser(userDto);

		});

		String expectedMessage = "role doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void createUserTestEntityAlreadyExistException()
			throws ResultNotFoundException, EntityAlreadyExistException {
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.of(user));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			User user1 = userService.createUser(userDto);

		});

		String expectedMessage = "mail use by an other user";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void deleteUserTest() throws ResultNotFoundException {
		Roles role = new Roles("user");
		List<Competence> listComp = new ArrayList<Competence>();
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence comp = new Competence(1L, "gateau chocolat", "cuisine", "gateau", user);
		Competence comp1 = new Competence(2L, "gateau chocolat", "cuisine", "gateau", user);
		listComp.add(comp);
		listComp.add(comp1);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(userRepository).delete(Mockito.any(User.class));
		Mockito.when(competenceRepository.finByUser(user)).thenReturn(listComp);
		Mockito.doNothing().when(competenceRepository).deleteAll(listComp);

		userService.deleteUser(1L);

	}

	@Test
	public void deleteUserTestResultNotFoundException() throws ResultNotFoundException {
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			userService.deleteUser(1L);

		});

		String expectedMessage = "user doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}
