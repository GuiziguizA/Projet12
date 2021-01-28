package sid.org;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.classe.Avis;
import sid.org.classe.Creneau;
import sid.org.dao.AvisRepository;
import sid.org.dao.CreneauRepository;
import sid.org.dto.AvisDto;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;
import sid.org.service.AvisServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
class AvisTests {

	@Mock
	AvisRepository avisRepository;
	@InjectMocks
	AvisServiceImpl avisService;
	@Mock
	CreneauRepository creneauRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createAvisTest() throws ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		AvisDto avisDto = new AvisDto(3, "insufisant", creneau);

		Mockito.when(creneauRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(creneau));

		Avis avis1 = avisService.createAvis(avisDto);

		assertEquals(avis.getCommentaire(), avis1.getCommentaire());
	}

	@Test
	public void createAvisTestResultNotFoundException() {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		AvisDto avisDto = new AvisDto(3, "insufisant", creneau);

		Mockito.when(creneauRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			avisService.createAvis(avisDto);

		});

		String expectedMessage = "creneau introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getAvisTest() throws ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);

		Mockito.when(avisRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(avis));

		Avis avis1 = avisService.getAvis(1L);

		assertEquals(avis.getCommentaire(), avis1.getCommentaire());
	}

	@Test
	public void getAvisTestResultNotFoundException() {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		AvisDto avisDto = new AvisDto(3, "insufisant", creneau);

		Mockito.when(avisRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			avisService.getAvis(1L);

		});

		String expectedMessage = "avis introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getListAvisTest() throws ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		Avis avis1 = new Avis(4, "insufisant", creneau);
		Avis avis2 = new Avis(2, "insufisant", creneau);
		List<Avis> listavis = new ArrayList<>();
		listavis.add(avis);
		listavis.add(avis1);
		listavis.add(avis2);

		Mockito.when(avisRepository.findByCreneau(creneau)).thenReturn(listavis);

		List<Avis> listavis1 = avisService.getlistAvis(creneau);

		assertEquals(listavis1.size(), 3);

	}

	@Test
	public void deleteAvisTest() throws ForbiddenException, ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		Avis avis1 = new Avis(4, "insufisant", creneau);
		Avis avis2 = new Avis(2, "insufisant", creneau);
		List<Avis> listavis = new ArrayList<>();
		listavis.add(avis);
		listavis.add(avis1);
		listavis.add(avis2);
		Mockito.when(avisRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(avis));
		Mockito.doNothing().when(avisRepository).delete(avis);

		avisService.deleteAvis(1L, 2L);

	}

	@Test
	public void deleteAvisTestResultNotFound() {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);

		Mockito.when(avisRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			avisService.deleteAvis(1L, 2L);

		});

		String expectedMessage = "avis introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void deleteAvisTestForbiddenException() {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);

		Mockito.when(avisRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(avis));

		ForbiddenException exception = assertThrows(ForbiddenException.class, () -> {
			avisService.deleteAvis(1L, 5L);

		});

		String expectedMessage = "Impossible de supprimer cet avis pour cette utilisateur";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

}
