package sid.org.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import sid.org.classe.Avis;
import sid.org.classe.Creneau;
import sid.org.dao.AvisRepository;
import sid.org.dao.CreneauRepository;
import sid.org.dto.AvisDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

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
	public void createAvisTest() throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException,
			EntityAlreadyExistException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		AvisDto avisDto = new AvisDto(3, 2L, 3L, "insufisant", creneau);

		Mockito.when(creneauRepository.findById(1L)).thenReturn(Optional.of(creneau));
		Mockito.when(avisRepository.findByCreneau(creneau)).thenReturn(Optional.of(avis));
		Avis avis1 = avisService.createAvis(avisDto, 1L);

		assertEquals(avis.getCommentaire(), avis1.getCommentaire());
	}

	@Test
	public void createAvisTestResultNotFoundException() {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);

		AvisDto avisDto = new AvisDto(3, 2L, 3L, "insufisant", creneau);

		Mockito.when(creneauRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

		ResultNotFoundException exception = assertThrows(ResultNotFoundException.class, () -> {
			avisService.createAvis(avisDto, 1L);

		});

		String expectedMessage = "creneau introuvable";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	public void getAvisTest() throws ForbiddenException, ResultNotFoundException {
		Creneau creneau = new Creneau(new Date(), 1L, 2L, 3L);
		Avis avis = new Avis(3, "insufisant", creneau);
		Avis avis1 = new Avis(4, "insufisant", creneau);
		Avis avis2 = new Avis(2, "insufisant", creneau);
		List<Avis> listavis = new ArrayList<>();
		listavis.add(avis);
		listavis.add(avis1);
		listavis.add(avis2);
		Pageable pageable = PageRequest.of(0, 2);
		Page<Avis> pageAvis = new PageImpl<Avis>(listavis);
		Mockito.when(avisRepository.findByIdComp(Mockito.anyLong(), pageable)).thenReturn(pageAvis);

		Page<Avis> pageAvis1 = avisService.getAvis(1L, 0, 2);

		assertEquals(3, pageAvis1.getSize());
	}

}
