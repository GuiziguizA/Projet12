package org.sid.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import org.sid.dto.CompetenceDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class CompetenceServiceTest {
	@Mock
	CompetenceRepository competenceRepository;
	@InjectMocks
	CompetenceServiceImpl competenceService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	void createCompetenceTest() throws EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findByUserAndNom(Mockito.any(Users.class), Mockito.anyString()))
				.thenReturn(Optional.empty());

		Competence competence1 = competenceService.createCompetence(competenceDto);
		assertEquals(competence1.getNom(), competence.getNom());
	}

	@Test
	void createCompetenceTestEntityAlreadyExist() throws EntityAlreadyExistException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

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
	void deleteCompetence() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(competence));
		Mockito.doNothing().when(competenceRepository).delete(Mockito.any(Competence.class));

		competenceService.deleteCompetence(1L);

	}

	@Test
	void deleteCompetenceException() throws ResultNotFoundException {
		Role role = new Role("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			competenceService.deleteCompetence(1L);

		});

		String expectedMessage = "la competence n'existe pas";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}