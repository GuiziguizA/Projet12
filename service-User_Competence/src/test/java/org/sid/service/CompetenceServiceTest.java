package org.sid.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.sid.classe.Competence;
import org.sid.classe.Roles;
import org.sid.classe.User;
import org.sid.dao.CompetenceRepository;
import org.sid.dto.CompetenceDto;
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
class CompetenceServiceTest {
	@MockBean
	CompetenceRepository competenceRepository;
	@Autowired
	CompetenceService competenceService;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void createCompetenceTest() throws EntityAlreadyExistException {
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findByUserAndNom(Mockito.any(User.class), Mockito.anyString()))
				.thenReturn(Optional.empty());

		Competence competence1 = competenceService.createCompetence(competenceDto);
		assertEquals(competence1.getNom(), competence.getNom());
	}

	@Test
	void createCompetenceTestEntityAlreadyExist() throws EntityAlreadyExistException {
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(competenceRepository.findByUserAndNom(Mockito.any(User.class), Mockito.anyString()))
				.thenReturn(Optional.of(competence));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			competenceService.createCompetence(competenceDto);

		});

		String expectedMessage = "la competence existe deja pour cette utilisateur";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}




	@Test
	void deleteCompetence() throws ResultNotFoundException{
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		
		Mockito.when(competenceRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(competence));
		Mockito.doNothing().when(competenceRepository).delete(Mockito.any(Competence.class));
		
		competenceService.deleteCompetence(1L);

	}
	
	@Test
	void deleteCompetenceException() throws ResultNotFoundException{
		Roles role = new Roles("user");
		User user = new User(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);
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