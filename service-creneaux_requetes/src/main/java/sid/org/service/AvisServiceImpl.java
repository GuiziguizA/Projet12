package sid.org.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sid.org.api.UserConnect;
import sid.org.classe.Avis;
import sid.org.classe.Creneau;
import sid.org.classe.Users;
import sid.org.dao.AvisRepository;
import sid.org.dao.CreneauRepository;
import sid.org.dto.AvisDto;
import sid.org.exception.EntityAlreadyExistException;
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
	private static final Logger logger = LoggerFactory.getLogger(AvisServiceImpl.class);

	@Override
	public Avis createAvis(AvisDto avisDto, Long idUser)
			throws ResultNotFoundException, ForbiddenException, EntityAlreadyExistException {

		Optional<Creneau> creneau = creneauRepository.findById(avisDto.getCreneau().getCodeCreneau());
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}
		Optional<Avis> avis1 = avisRepository.findByCreneau(creneau.get());
		if (avis1.isPresent()) {
			throw new EntityAlreadyExistException("l'avis existe deja");
		}

		if (!creneau.get().getStatut().equals("passe")) {
			logger.info(creneau.get().getStatut());
			throw new ForbiddenException("Vous n'estes pas autorisé a laisser un avis");
		}
		Users user = userConnect.getUser(idUser);
		if (user.getCodeUtilisateur() != avisDto.getCreneau().getIdUserDemande()) {
			logger.info("id du User co" + user.getCodeUtilisateur());
			logger.info("id du User qui a fait la demande de la competence" + avisDto.getCreneau().getIdUserDemande());
			throw new ForbiddenException("Vous n'estes pas autorisé a laisser un avis1");
		}

		Avis avis = new Avis();
		avis.setCommentaire(avisDto.getCommentaire());
		avis.setNote(avisDto.getNote());
		avis.setCreneau(avisDto.getCreneau());
		avis.setIdComp(avisDto.getCreneau().getIdComp());
		avis.setIdUser(user.getCodeUtilisateur());

		avisRepository.saveAndFlush(avis);
		return avis;
	}

	@Override
	public Page<Avis> getAvis(Long idComp, int page, int size) throws ResultNotFoundException, ForbiddenException {
		Pageable pageable = PageRequest.of(page, size);
		Page<Avis> avis = avisRepository.findByIdComp(idComp, pageable);

		return avis;

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
