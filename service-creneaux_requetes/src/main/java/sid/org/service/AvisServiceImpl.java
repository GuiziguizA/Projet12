package sid.org.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sid.org.api.UserConnect;
import sid.org.classe.Avis;
import sid.org.classe.Creneau;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.config.MessagingConfig;
import sid.org.dao.AvisRepository;
import sid.org.dao.CreneauRepository;
import sid.org.dao.RequeteRepository;
import sid.org.dto.AvisDto;
import sid.org.dto.NotesDto;
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
	@Autowired
	RequeteRepository requeteRepository;
	@Autowired
	public RabbitTemplate template;
	private static final Logger logger = LoggerFactory.getLogger(AvisServiceImpl.class);

	@Value("${statut.4}")
	private String statut4;

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
		logger.info(avisDto.getCreneau().getIdRequete().toString());
		Optional<Requete> requete = requeteRepository.findById(avisDto.getCreneau().getIdRequete());
		if (!requete.isPresent()) {
			throw new ResultNotFoundException("requete introuvable");
		}
		if (user.getCodeUtilisateur() == requete.get().getIdUserComp()) {
			logger.info("id du User co" + user.getCodeUtilisateur());
			logger.info("id du User qui a fait la demande de la competence" + avisDto.getCreneau().getIdUserDemande());
			throw new ForbiddenException("Vous n'estes pas autorisé a laisser un avis");
		}

		Avis avis = new Avis();
		avis.setCommentaire(avisDto.getCommentaire());
		avis.setNote(avisDto.getNote());
		avis.setCreneau(avisDto.getCreneau());
		avis.setIdComp(avisDto.getCreneau().getIdComp());
		avis.setIdUser(user.getCodeUtilisateur());
		avis.setUsername(user.getUsername());
		avisRepository.saveAndFlush(avis);

		modifNoteCompetence(avisDto.getCreneau().getIdComp());
		modifStatutCreneauEtRequete(avisDto.getCreneau().getCodeCreneau());
		return avis;
	}

	@Override
	public Page<Avis> getAvis(Long idComp, int page, int size) throws ResultNotFoundException, ForbiddenException {
		Pageable pageable = PageRequest.of(page, size);
		Page<Avis> avis = avisRepository.findByIdComp(idComp, pageable);

		return avis;

	}

	public void modifNoteCompetence(Long idComp) {
		List<Avis> listAvis = avisRepository.findByIdComp(idComp);

		int somme = 0, moy;
		for (int i = 0; i < listAvis.size(); i++)
			somme += listAvis.get(i).getNote();

		// trouver la valeur moyenne
		moy = somme / listAvis.size();
		logger.info("La moyenne des notes: " + moy);

		NotesDto notes = new NotesDto();
		notes.setIdComp(idComp);
		notes.setMoyenne(moy);
		notes.setNombreDAvis(listAvis.size());

		template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTIN_KEY1, notes);

		logger.info("Les notes du rabbitmq: " + notes.getMoyenne() + notes.getNombreDAvis());
	}

	public void modifStatutCreneauEtRequete(Long idCreneau) throws ResultNotFoundException {
		Optional<Creneau> creneau = creneauRepository.findById(idCreneau);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("le creneau est inexistant");
		}
		Optional<Requete> requete = requeteRepository.findById(creneau.get().getIdRequete());
		creneau.get().setStatut(statut4);
		if (!requete.isPresent()) {
			throw new ResultNotFoundException("la requete est inexistante");
		}
		requete.get().setStatut(statut4);

		creneauRepository.saveAndFlush(creneau.get());
		requeteRepository.saveAndFlush(requete.get());
	}

}
