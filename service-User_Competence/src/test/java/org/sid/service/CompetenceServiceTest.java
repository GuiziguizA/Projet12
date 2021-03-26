package org.sid.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.sid.classe.Competence;
import org.sid.classe.Role;
import org.sid.classe.Users;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.UsersRepository;
import org.sid.dto.CompetenceDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.sid.specification.CompetenceCriteria;
import org.sid.specification.CompetenceSpecificationImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CompetenceServiceTest {
	@Mock
	CompetenceRepository competenceRepository;
	@InjectMocks
	CompetenceServiceImpl competenceService;
	@Mock
	UsersRepository userRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	void createCompetenceTest() throws EntityAlreadyExistException, ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user.getUsername());
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(competenceRepository.findByUserAndNom(Mockito.any(Users.class), Mockito.anyString()))
				.thenReturn(Optional.empty());

		Competence competence1 = competenceService.createCompetence(competenceDto);
		assertEquals(competence1.getNom(), competence.getNom());
	}

	@Test
	void createCompetenceTestEntityAlreadyExist() throws EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user.getUsername());
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
		Mockito.when(competenceRepository.findByUserAndNom(Mockito.any(Users.class), Mockito.anyString()))
				.thenReturn(Optional.of(competence));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			competenceService.createCompetence(competenceDto);

		});

		String expectedMessage = "la competence existe deja pour cette utilisateur";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void createCompetenceUserNotFound() throws EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user.getUsername());
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			competenceService.createCompetence(competenceDto);

		});

		String expectedMessage = "l'utilisateur nexiste pas";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void deleteCompetence() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(competence));
		Mockito.doNothing().when(competenceRepository).delete(Mockito.any(Competence.class));

		competenceService.deleteCompetence(1L);

	}

	@Test
	void deleteCompetenceException() throws ResultNotFoundException {

		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			competenceService.deleteCompetence(1L);

		});

		String expectedMessage = "la competence n'existe pas";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void getCompetenceResultNotFoundException() throws ResultNotFoundException {

		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			competenceService.getCompetence(1L);

		});

		String expectedMessage = "la competence n'existe pas";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	void getCompetence() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(competence));
		Mockito.doNothing().when(competenceRepository).delete(Mockito.any(Competence.class));

		Competence comp = competenceService.getCompetence(1L);

		assertEquals(comp.getNom(), competence.getNom());

	}

	@Test
	void searchCompetenceTest() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		Competence competence1 = new Competence(1L, "changer un frein de vélo", "mecanique",
				"changer les freins d'un vélo", user);
		Pageable pageable = PageRequest.of(0, 2);
		CompetenceCriteria criteria = new CompetenceCriteria(null, null, "mecanique");
		CompetenceSpecificationImpl competenceSpecificationImpl = Mockito.mock(CompetenceSpecificationImpl.class);
		List<Competence> competences = new ArrayList<>();
		competences.add(competence);
		competences.add(competence1);
		Page<Competence> pageCompetence = new PageImpl<Competence>(competences);

		Mockito.when(competenceRepository.findAll(competenceSpecificationImpl, pageable)).thenReturn(pageCompetence);

		Page<Competence> pageCompetence1 = competenceService.searchCompetence(criteria, 0, 2);

		assertEquals(2, pageCompetence1.getSize());

	}

	@Test
	void getCompetenceUserTest() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users("bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		Competence competence1 = new Competence(1L, "changer un frein de vélo", "mecanique",
				"changer les freins d'un vélo", user);
		Pageable pageable = PageRequest.of(0, 2);
		List<Competence> competences = new ArrayList<>();
		competences.add(competence);
		competences.add(competence1);
		Page<Competence> pageCompetence = new PageImpl<Competence>(competences);

		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

		Mockito.when(competenceRepository.findByUser(user, pageable)).thenReturn(pageCompetence);

		Page<Competence> pageCompetence1 = competenceService.getCompetencesUser("bob", 0, 2);

		assertEquals(2, pageCompetence1.getSize());

	}

	@Test
	void getCompetenceUserTestUserNotFoundException() throws ResultNotFoundException {

		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			competenceService.getCompetencesUser("bob", 0, 2);

		});

		String expectedMessage = "user introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}