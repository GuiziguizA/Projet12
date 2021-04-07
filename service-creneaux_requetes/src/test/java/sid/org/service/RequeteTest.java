package sid.org.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.api.CompetenceApi;
import sid.org.api.UserConnect;
import sid.org.classe.Competence;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.dao.RequeteRepository;
import sid.org.dto.RequeteDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest

public class RequeteTest {
	@InjectMocks
	RequeteServiceImpl requeteService;
	@Mock
	RequeteRepository requeteRepository;
	@Mock
	UserConnect userConnect;
	@Mock
	CompetenceApi competenceApi;

	@Test
	public void createRequeteTest()
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException {
		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		RequeteDto requeteDto = new RequeteDto(1L, 2L, "demande");
		Users user = new Users(2L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(userConnect.getUser(Mockito.anyLong())).thenReturn(user);
		Mockito.when(requeteRepository.findByIdUserAndIdCompAndStatut(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(competenceApi.getCompetence(Mockito.anyLong())).thenReturn(competence);
		Mockito.when(requeteRepository.saveAndFlush(Mockito.any(Requete.class))).thenReturn(requete);

		Requete requete1 = requeteService.createRequete(requeteDto);

		assertEquals(requete1.getIdComp(), requete.getIdComp());
		assertEquals(requete1.getIdUser(), requete.getIdUser());

	}

	@Test
	public void createRequeteTestEntityAlreadyExistException() throws EntityAlreadyExistException {
		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		RequeteDto requeteDto = new RequeteDto(1L, 1L, "demande");
		Users user = new Users(2L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Mockito.when(userConnect.getUser(Mockito.anyLong())).thenReturn(user);
		Mockito.when(requeteRepository.findByIdUserAndIdCompAndStatut(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyString())).thenReturn(Optional.of(requete));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			requeteService.createRequete(requeteDto);

		});

		String expectedMessage = "la requete existe deja";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getRequeteTest() throws ResultNotFoundException, ForbiddenException, APiUSerAndCompetenceException {

		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		Users user = new Users(2L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");
		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		Mockito.when(requeteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(requete));
		Mockito.when(competenceApi.getCompetence(Mockito.anyLong())).thenReturn(competence);
		Requete requete1 = requeteService.getRequete(1L, 2L);

		assertEquals(requete, requete1);

	}

	@Test
	public void getRequeteTestResultNotFoundException() throws ResultNotFoundException {

		Mockito.when(requeteRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			requeteService.getRequete(1L, 2L);

		});

		String expectedMessage = "le requete est introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getRequetesTest() throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {

		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		Requete requete1 = new Requete(1L, new Date(), 1L, 2L, "demande");

		List<Requete> listRequetes = new ArrayList<Requete>();
		listRequetes.add(requete);
		listRequetes.add(requete1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Requete> pageRequetes = new PageImpl<Requete>(listRequetes);

		Mockito.when(requeteRepository.findByIdUser(1L, pageable)).thenReturn(pageRequetes);

		requeteService.getRequetes(1L, 0, 2);

	}

	@Test
	public void getRequetesCompTest()
			throws APiUSerAndCompetenceException, ResultNotFoundException, ForbiddenException {

		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		Requete requete1 = new Requete(1L, new Date(), 1L, 2L, "demande");

		List<Requete> listRequetes = new ArrayList<Requete>();
		listRequetes.add(requete);
		listRequetes.add(requete1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Requete> pageRequetes = new PageImpl<Requete>(listRequetes);

		Mockito.when(requeteRepository.findByIdUser(1L, pageable)).thenReturn(pageRequetes);

		requeteService.getRequetesComp(1L, 0, 2);

	}

}