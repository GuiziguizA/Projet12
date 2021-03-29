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

import sid.org.api.ChatConnect;
import sid.org.classe.Competence;
import sid.org.classe.Creneau;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.dao.CreneauRepository;
import sid.org.dao.RequeteRepository;
import sid.org.dto.CreneauDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class CreneauTest {
	@InjectMocks
	CreneauServiceImpl creneauService;
	@Mock
	CreneauRepository creneauRepository;
	@Mock
	RequeteRepository requeteRepository;

	@Test
	public void createCreneauTest() throws EntityAlreadyExistException, APiUSerAndCompetenceException,
			ForbiddenException, ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		List<Creneau> listCreneau = null;
		CreneauDto creneauDto = new CreneauDto(1L, 2L, 1L, "demande", new Date());
		Requete requete = new Requete(1L, new Date(), 1L, 2L, "valide");
		Mockito.when(creneauRepository.findByIdUserAndIdUser1AndIdCompAndStatut(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong(), Mockito.anyString())).thenReturn(listCreneau);
		Mockito.when(requeteRepository.findById(1L)).thenReturn(Optional.of(requete));
		Mockito.mock(ChatConnect.class);

		Creneau creneau1 = creneauService.createCreneau(creneauDto, 2L, 1L);

		assertEquals(creneau.getIdComp(), creneau1.getIdComp());

		assertEquals(creneau.getIdUser(), creneau1.getIdUser());
	}

	@Test
	public void createCreneauTestEntityAlreadyExistException() throws EntityAlreadyExistException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		CreneauDto creneauDto = new CreneauDto(1L, 2L, 1L, "demande", new Date());
		Mockito.when(creneauRepository.findByIdUserAndIdUser1AndIdComp(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(Optional.of(creneau));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class, () -> {
			creneauService.createCreneau(creneauDto, 3L, 1L);

		});

		String expectedMessage = "le creneau existe deja";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void getCreneauTest() throws ResultNotFoundException, APiUSerAndCompetenceException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);

		Mockito.when(creneauRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(creneau));

		Creneau creneau1 = creneauService.getCreneau(1L, 2L, 3L);

		assertEquals(creneau.getIdComp(), creneau1.getIdComp());

		assertEquals(creneau.getIdUser(), creneau1.getIdUser());
	}

	@Test
	public void getCreneauTestResultNotFoundException() throws ResultNotFoundException {

		Mockito.when(creneauRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			creneauService.getCreneau(1L, 2L, 3L);

		});

		String expectedMessage = "creneau introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getCreneauxUserTest() throws APiUSerAndCompetenceException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		Creneau creneau1 = new Creneau(new Date(), 2L, 3L, 2L);

		List<Creneau> creneaux = new ArrayList<>();
		creneaux.add(creneau);
		creneaux.add(creneau1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> pageCreneaux = new PageImpl<Creneau>(creneaux);
		Mockito.when(creneauRepository.findByIdUserDemande(1L, pageable)).thenReturn(pageCreneaux);

		Page<Creneau> pageCreneaux1 = creneauService.getCreneauxUser(1L, 0, 2);

		assertEquals(pageCreneaux.getSize(), pageCreneaux1.getSize());

	}

	@Test
	public void getCreneauxUser1Test() throws APiUSerAndCompetenceException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		Creneau creneau1 = new Creneau(new Date(), 2L, 3L, 2L);

		List<Creneau> creneaux = new ArrayList<>();
		creneaux.add(creneau);
		creneaux.add(creneau1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> pageCreneaux = new PageImpl<Creneau>(creneaux);
		Mockito.when(creneauRepository.findByIdUserDemande(1L, pageable)).thenReturn(pageCreneaux);

		Page<Creneau> pageCreneaux1 = creneauService.getCreneauxUser1(1L, 0, 2);

		assertTrue(pageCreneaux.getSize() == pageCreneaux1.getSize());

	}

	@Test
	public void getlistCreneauxCompTest() throws APiUSerAndCompetenceException, ForbiddenException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		Creneau creneau1 = new Creneau(new Date(), 2L, 3L, 2L);
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		List<Creneau> creneaux = new ArrayList<>();
		creneaux.add(creneau);
		creneaux.add(creneau1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> pageCreneaux = new PageImpl<Creneau>(creneaux);
		Mockito.when(creneauRepository.findByIdComp(1L, pageable)).thenReturn(pageCreneaux);

		Page<Creneau> pageCreneaux1 = creneauService.getlistCreneauxComp(competence, 1L, 0, 2);

		assertEquals(2, pageCreneaux1.getSize());

	}

	@Test
	public void getlistCreneauxCompTestForbiddenException() throws APiUSerAndCompetenceException, ForbiddenException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		Creneau creneau1 = new Creneau(new Date(), 2L, 3L, 2L);
		Users user = new Users(2L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125");

		Competence competence = new Competence(1L, "changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);

		List<Creneau> creneaux = new ArrayList<>();
		creneaux.add(creneau);
		creneaux.add(creneau1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> pageCreneaux = new PageImpl<Creneau>(creneaux);
		Mockito.when(creneauRepository.findByIdComp(1L, pageable)).thenReturn(pageCreneaux);

		ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
			creneauService.getlistCreneauxComp(competence, 1L, 0, 2);

		});

		String expectedMessage = "Vous n'estes pas autorisé a consulter ces avis";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}
}
