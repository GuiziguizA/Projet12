package sid.org;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.classe.Creneau;
import sid.org.dao.CreneauRepository;
import sid.org.dto.CreneauDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.CreneauService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
class CreneauTest {
	@Autowired
	CreneauService creneauService;
	@MockBean
	CreneauRepository creneauRepository;

	@Test
	public void createCreneauTest() throws EntityAlreadyExistException, APiUSerAndCompetenceException,
			ForbiddenException, ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		CreneauDto creneauDto = new CreneauDto(1L, 2L, 1L, "demande", new Date());
		Mockito.when(creneauRepository.findByIdUserAndIdUser1AndIdComp(Mockito.anyLong(), Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(Optional.empty());

		Creneau creneau1 = creneauService.createCreneau(creneauDto, 2L);

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
			creneauService.createCreneau(creneauDto, 3L);

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
	public void getCreneauxTest() throws APiUSerAndCompetenceException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);
		Creneau creneau1 = new Creneau(new Date(), 2L, 3L, 2L);

		List<Creneau> creneaux = new ArrayList<>();
		creneaux.add(creneau);
		creneaux.add(creneau1);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> pageCreneaux = new PageImpl<Creneau>(creneaux);
		Mockito.when(creneauRepository.findByIdUser(1L, pageable)).thenReturn(pageCreneaux);

		Page<Creneau> pageCreneaux1 = creneauService.getCreneauxUser(1L);

		assertEquals(2, pageCreneaux1.getSize());

	}

	@Test
	public void deleteCreneauxTest() throws ResultNotFoundException, APiUSerAndCompetenceException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 1L);

		Mockito.when(creneauRepository.findById(1L)).thenReturn(Optional.of(creneau));
		Mockito.doNothing().when(creneauRepository).delete(creneau);
		creneauService.deleteCreneau(1L, 2L, 3L);

	}

	@Test
	public void deleteCreneauxTestResultNotFoundException() throws ResultNotFoundException {

		Mockito.when(creneauRepository.findById(1L)).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			creneauService.deleteCreneau(1L, 2L, 3L);

		});

		String expectedMessage = "creneau introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}
