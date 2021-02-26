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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.sid.classe.Competence;
import org.sid.classe.Role;
import org.sid.classe.Users;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.RolesRepository;
import org.sid.dao.UsersRepository;
import org.sid.dto.UserDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

	@Mock
	UsersRepository userRepository;
	@Mock
	RolesRepository rolesRepository;
	@InjectMocks
	UserServiceImpl userService;
	@Mock
	CompetenceRepository competenceRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void createUserTest() throws ResultNotFoundException, EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(userRepository.save(Mockito.any(Users.class))).thenReturn(user);

		Users user1 = userService.createUser(userDto);

		assertEquals(user.getMail(), user1.getMail());
	}

	@Test
	public void createUserTestResultNotFounfRole() throws ResultNotFoundException, EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			Users user1 = userService.createUser(userDto);

		});

		String expectedMessage = "role doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void createUserTestEntityAlreadyExistException()
			throws ResultNotFoundException, EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.of(user));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			Users user1 = userService.createUser(userDto);

		});

		String expectedMessage = "mail use by an other user";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void deleteUserTest() throws ResultNotFoundException {
		Role role = new Role("user");
		List<Competence> listComp = new ArrayList<Competence>();
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", listComp, role);
		Competence comp = new Competence(1L, "gateau chocolat", "cuisine", "gateau", user);
		Competence comp1 = new Competence(2L, "gateau chocolat", "cuisine", "gateau", user);
		listComp.add(comp);
		listComp.add(comp1);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(userRepository).delete(Mockito.any(Users.class));

		/*
		 * Mockito.when(competenceRepository.deleteUSer(Mockito.any(User.class))).
		 * thenReturn(listComp);
		 */
		userService.deleteUser(1L);

	}

	@Test
	public void deleteUserTest1() throws ResultNotFoundException {
		Role role = new Role("user");
		List<Competence> listComp = new ArrayList<Competence>();
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", listComp, role);
		Competence comp = new Competence(1L, "gateau chocolat", "cuisine", "gateau", user);
		Competence comp1 = new Competence(2L, "gateau chocolat", "cuisine", "gateau", user);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));
		Mockito.doNothing().when(userRepository).delete(Mockito.any(Users.class));
		/*
		 * Mockito.when(competenceRepository.deleteUSer(Mockito.any(User.class))).
		 * thenReturn(listComp);
		 */
		userService.deleteUser(1L);

	}

	@Test
	public void deleteUserTestResultNotFoundException() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			userService.deleteUser(1L);

		});

		String expectedMessage = "user doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getUserTestResultNotFoundException() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			userService.getUser(1L);

		});

		String expectedMessage = "user doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void getUserTest() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

		Users user1 = userService.getUser(1L);

		assertEquals(user1, user);
	}

	@Test
	public void getUserDtoTest() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(user));

		UserDto userDto1 = userService.getUserDto(1L);

		assertEquals(userDto1.getMail(), userDto.getMail());
	}

	@Test
	public void getUserDtoTestResultNotFoundException() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			userService.getUserDto(1L);

		});

		String expectedMessage = "user doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void updateUserTestResultNotFOundUser() throws ResultNotFoundException, EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			userService.updateUser(userDto, Optional.empty());

		});

		String expectedMessage = "user doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void updateUserTestResultNotFound() throws ResultNotFoundException, EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.empty());
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.of(user));

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			userService.updateUser(userDto, Optional.empty());

		});

		String expectedMessage = "role doesn't exist";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void updateUserTest() throws ResultNotFoundException, EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		UserDto userDto = new UserDto("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(rolesRepository.findByNom("user")).thenReturn(Optional.of(role));
		Mockito.when(userRepository.findByMail(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.saveAndFlush(Mockito.any(Users.class))).thenReturn(user);

		userService.updateUser(userDto, Optional.empty());

	}

}
