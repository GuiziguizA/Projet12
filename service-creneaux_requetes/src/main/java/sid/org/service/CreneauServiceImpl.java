package sid.org.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sid.org.api.ChatConnect;
import sid.org.api.CompetenceApi;
import sid.org.api.UserConnect;
import sid.org.classe.Competence;
import sid.org.classe.Creneau;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.config.MessagingConfig;
import sid.org.dao.CreneauRepository;
import sid.org.dao.RequeteRepository;
import sid.org.dto.CreneauDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@Service
public class CreneauServiceImpl implements CreneauService {
	@Autowired
	CreneauRepository creneauRepository;
	@Autowired
	CompetenceApi competenceApi;
	@Autowired
	UserConnect userConnect;
	@Autowired
	ChatConnect chatConnect;
	@Autowired
	RequeteRepository requeteRepository;
	@Autowired
	RabbitTemplate template;
	@Value("${statut.1}")
	private String statut1;
	@Value("${statut.2}")
	private String statut2;
	@Value("${statut.3}")
	private String statut3;

	@Override
	public Creneau createCreneau(CreneauDto creneauDto, Long idRequete, Long idUserDemande)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException,
			ResultNotFoundException {

		List<Creneau> creneau = creneauRepository.findByIdUserAndIdUser1AndIdCompAndStatut(creneauDto.getIdUser(),
				creneauDto.getIdUser1(), creneauDto.getIdComp(), statut2);
		List<Creneau> creneau2 = creneauRepository.findByIdUserAndIdUser1AndIdCompAndStatut(creneauDto.getIdUser1(),
				creneauDto.getIdUser(), creneauDto.getIdComp(), statut2);

		if (!creneau.isEmpty()) {
			throw new EntityAlreadyExistException("Un creneau validé existe deja pour cette competence");
		}
		if (!creneau2.isEmpty()) {
			throw new EntityAlreadyExistException("Un creneau validé existe deja pour cette competence");
		}

		Optional<Requete> requete = requeteRepository.findById(idRequete);

		if (!requete.isPresent()) {
			throw new ResultNotFoundException("la requete est introuvable");
		}

		// chatConnect verifie si le chat existe

		chatConnect.getChat(creneauDto.getIdUser(), creneauDto.getIdUser1(), idRequete);

		Creneau creneau1 = new Creneau();
		creneau1.setIdComp(creneauDto.getIdComp());
		creneau1.setCompetenceName(competenceApi.getCompetence(creneauDto.getIdComp()).getNom());
		;

		creneau1.setDate(new Date());
		creneau1.setIdUser(creneauDto.getIdUser());
		creneau1.setUserDemandeName(creneauDto.getUserDemandeName());
		creneau1.setIdUser1(creneauDto.getIdUser1());
		creneau1.setIdRequete(creneauDto.getIdRequete());
		creneau1.setIdUserDemande(idUserDemande);
		if (idUserDemande == creneauDto.getIdUser1()) {
			creneau1.setIdUserRecoit(creneauDto.getIdUser());
			Users user = userConnect.getUser(creneauDto.getIdUser());
			creneau1.setUserRecoitName(user.getUsername());
		} else {
			creneau1.setIdUserRecoit(creneauDto.getIdUser1());
			Users user = userConnect.getUser(creneauDto.getIdUser1());
			creneau1.setUserRecoitName(user.getUsername());
		}
		creneau1.setStatut(statut1);
		creneau1.setDate(creneauDto.getDate());
		creneau1.setIdChat(creneauDto.getIdChat());
		creneauRepository.saveAndFlush(creneau1);

		return creneau1;

	}

	@Override
	public void updateCreneau(Long id, Long idUser) throws ResultNotFoundException, ForbiddenException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}

		if (creneau.get().getIdUserDemande() == idUser) {
			throw new ForbiddenException("Vous n'etes pas autorisé a valider le creneau");
		}
		if (creneau.get().getStatut().equals(statut1)) {
			creneau.get().setStatut(statut2);
			List<Creneau> listCreneau = creneauRepository.findByIdChatAndStatut(creneau.get().getIdChat(), statut1);
			if (!listCreneau.isEmpty()) {
				creneauRepository.deleteAll(listCreneau);
			}
		} else if (creneau.get().getStatut().equals(statut2) && idUser == creneau.get().getIdUserDemande()) {
			throw new ForbiddenException("Vous n'etes pas autorisé a valider le creneau");
		} else if (creneau.get().getStatut().equals(statut2)) {
			creneau.get().setStatut(statut3);

			template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTIN_KEY2, creneau.get().getIdChat());

		}
		creneauRepository.save(creneau.get());
	}

	@Override
	public Creneau getCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}

		return creneau.get();

	}

	@Override
	public Page<Creneau> getCreneauxUser(Long idUser, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "codeCreneau");
		Page<Creneau> creneaux = creneauRepository.findByIdUserDemande(idUser, pageable);

		return creneaux;

	}

	@Override
	public Page<Creneau> getCreneauxUser1(Long idUser1, int page, int size) {

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "codeCreneau");
		Page<Creneau> creneaux = creneauRepository.findByIdUserRecoit(idUser1, pageable);

		return creneaux;

	}

	@Override
	public Creneau getCreneau(Long id) throws ResultNotFoundException {

		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}

		return creneau.get();

	}

	@Override
	public Page<Creneau> getlistCreneauxComp(Competence comp, Long idUser, int page, int size)
			throws ForbiddenException {
		Pageable pageable = PageRequest.of(page, size);
		Page<Creneau> listCreneau = creneauRepository.findByIdComp(comp.getCodeCompetence(), pageable);

		if (idUser != comp.getUser().getCodeUtilisateur()) {
			throw new ForbiddenException("Vous n'estes pas autorisé a consulter ces avis");
		}

		return listCreneau;

	}

}
