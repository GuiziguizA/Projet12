package sid.org.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sid.org.api.UserConnect;
import sid.org.classe.Avis;
import sid.org.classe.Creneau;
import sid.org.classe.Users;
import sid.org.dao.AvisRepository;
import sid.org.dao.CreneauRepository;
import sid.org.dto.AvisDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@Service
public class AvisServiceImpl implements AvisService {

	@Autowired
	AvisRepository avisRepository;
	@Autowired
	CreneauRepository creneauRepository;
	@Autowired
	UserConnect userConnect;

	@Override
	public Avis createAvis(AvisDto avisDto, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {

		Optional<Creneau> creneau = creneauRepository.findById(avisDto.getCreneau().getCodeCreneau());
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}
		Users user = userConnect.getUser(idUser);
		if (user.getCodeUtilisateur() != creneau.get().getIdUser1()) {
			throw new ForbiddenException("Vous n'estes pas autorisé a laisser un avis");
		}

		Avis avis = new Avis();
		avis.setCommentaire(avisDto.getCommentaire());
		avis.setNote(avisDto.getNote());
		avis.setCreneau(avisDto.getCreneau());

		avisRepository.saveAndFlush(avis);

		return avis;

	}

	@Override
	public Avis getAvis(Long id, Long idUser) throws ResultNotFoundException, ForbiddenException {

		Optional<Avis> avis = avisRepository.findById(id);

		if (!avis.isPresent()) {
			throw new ResultNotFoundException("avis introuvable");
		}

		if (idUser != avis.get().getCreneau().getIdUser()) {
			throw new ForbiddenException("Vous n'estes pas autorisé a consulter cet avis");
		}
		return avis.get();

	}

	@Override
	public void deleteAvis(Long id, Long Userid) throws ResultNotFoundException, ForbiddenException {
		Optional<Avis> avis = avisRepository.findById(id);

		if (!avis.isPresent()) {
			throw new ResultNotFoundException("avis introuvable");
		}
		if (avis.get().getCreneau().getIdUser() != Userid && avis.get().getCreneau().getIdUser1() != Userid) {
			throw new ForbiddenException("Impossible de supprimer cet avis pour cette utilisateur");
		}

		avisRepository.delete(avis.get());

	}

}
