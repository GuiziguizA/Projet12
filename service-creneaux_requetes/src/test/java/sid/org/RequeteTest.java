package sid.org;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.classe.Requete;
import sid.org.dao.RequeteRepository;
import sid.org.dto.RequeteDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.RequeteService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class RequeteTest {
	@Autowired
	RequeteService requeteService;
	@MockBean
	RequeteRepository requeteRepository;

	@Test
	public void createRequeteTest()
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException {
		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		RequeteDto requeteDto = new RequeteDto(1L, 2L, "demande");

		Mockito.when(requeteRepository.findByIdUserAndIdCompAndStatut(Mockito.anyLong(), Mockito.anyLong(), "demande"))
				.thenReturn(Optional.empty());

		Requete requete1 = requeteService.createRequete(requeteDto);

		assertEquals(requete1.getIdComp(), requete.getIdComp());
		assertEquals(requete1.getIdUser(), requete.getIdUser());

	}

	@Test
	public void createRequeteTestEntityAlreadyExistException() throws EntityAlreadyExistException {
		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");
		RequeteDto requeteDto = new RequeteDto(1L, 1L, "demande");

		Mockito.when(requeteRepository.findByIdUserAndIdCompAndStatut(Mockito.anyLong(), Mockito.anyLong(), "demande"))
				.thenReturn(Optional.of(requete));

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

		Mockito.when(requeteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(requete));

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

	/*
	 * @Test public void getRequetesTest() throws APiUSerAndCompetenceException {
	 * 
	 * Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande"); Requete
	 * requete1 = new Requete(1L, new Date(), 1L, 2L, "demande");
	 * 
	 * List<Requete> listRequetes = new ArrayList<Requete>();
	 * listRequetes.add(requete); listRequetes.add(requete1); Pageable pageable =
	 * PageRequest.of(0, 2); Page<Requete> pageRequetes = new
	 * PageImpl<Requete>(listRequetes);
	 * 
	 * Mockito.when(requeteRepository.findByIdUser(1L,
	 * pageable)).thenReturn(pageRequetes);
	 * 
	 * requeteService.getRequetes(1L);
	 * 
	 * }
	 */

	@Test
	public void deleteRequetesTest() throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {
		Requete requete = new Requete(1L, new Date(), 1L, 2L, "demande");

		Mockito.when(requeteRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(requete));
		Mockito.doNothing().when(requeteRepository).delete(requete);

		requeteService.deleteRequete(1L, 2L);

	}

	@Test
	public void deleteRequetesTestResultNotFoundException() throws ResultNotFoundException {

		Mockito.when(requeteRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			requeteService.deleteRequete(1L, 3L);

		});

		String expectedMessage = "le requete est introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}