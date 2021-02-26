package sid.org.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sid.org.api.ChatConnect;
import sid.org.api.CompetenceApi;
import sid.org.api.UserConnect;
import sid.org.classe.Competence;
import sid.org.classe.Creneau;
import sid.org.classe.Requete;
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

	@Override
	public Creneau createCreneau(CreneauDto creneauDto, Long idRequete) throws EntityAlreadyExistException,
			APiUSerAndCompetenceException, ForbiddenException, ResultNotFoundException {

		Optional<Creneau> creneau = creneauRepository.findByIdUserAndIdUser1AndIdComp(creneauDto.getIdUser(),
				creneauDto.getIdUser1(), creneauDto.getIdComp());
		Optional<Creneau> creneau2 = creneauRepository.findByIdUserAndIdUser1AndIdComp(creneauDto.getIdUser1(),
				creneauDto.getIdUser(), creneauDto.getIdComp());

		if (creneau.isPresent() || creneau2.isPresent()) {
			throw new EntityAlreadyExistException("le creneau existe deja");
		}
		Optional<Requete> requete = requeteRepository.findById(idRequete);

		if (!requete.isPresent()) {
			throw new ResultNotFoundException("la requete est introuvable");
		}

		Competence competence = competenceApi.getCompetence(requete.get().getIdComp());

		// chatConnect verifie si le chat existe

		chatConnect.getChat(creneauDto.getIdUser(), creneauDto.getIdUser1());

		Creneau creneau1 = new Creneau();
		creneau1.setIdComp(creneauDto.getIdComp());
		creneau1.setDate(new Date());
		creneau1.setIdUser(creneauDto.getIdUser());
		creneau1.setIdUser1(creneauDto.getIdUser1());
		creneau1.setStatut("demande");
		creneau1.setDate(creneauDto.getDate());
		creneauRepository.saveAndFlush(creneau1);

		return creneau1;

	}

	@Override
	public void updateCreneau(Long id, Long idUser) throws ResultNotFoundException, ForbiddenException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}

		if (creneau.get().getIdUser1() != idUser) {
			throw new ForbiddenException("Vous n'etes pas autorisé a valider le creneaux");
		}
		creneau.get().setStatut("valide");
		creneauRepository.save(creneau.get());
	}

	@Override
	public Creneau getCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}
		// chatConnect verifie si le chat existe
		chatConnect.getChat(idUser, idUser1);
		return creneau.get();

	}

	@Override
	public Page<Creneau> getCreneauxUser(Long idUser) throws APiUSerAndCompetenceException {

		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> creneaux = creneauRepository.findByIdUser(idUser, pageable);

		return creneaux;

	}

	@Override
	public Page<Creneau> getCreneauxUser1(Long idUser1) throws APiUSerAndCompetenceException {

		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> creneaux = creneauRepository.findByIdUser(idUser1, pageable);

		return creneaux;

	}

	@Override
	public void deleteCreneau(Long id, Long idUser, Long idUser1)
			throws ResultNotFoundException, APiUSerAndCompetenceException {
		Optional<Creneau> creneau = creneauRepository.findById(id);
		if (!creneau.isPresent()) {
			throw new ResultNotFoundException("creneau introuvable");
		}
		// chatConnect verifie si le chat existe
		chatConnect.getChat(idUser, idUser1);
		creneauRepository.delete(creneau.get());

	}

	@Override

	public Page<Creneau> getlistCreneauxComp(Competence comp, Long idUser)
			throws ResultNotFoundException, ForbiddenException {
		Pageable pageable = PageRequest.of(0, 2);
		Page<Creneau> listCreneau = creneauRepository.findByIdComp(comp.getCodeCompetence(), pageable);

		if (idUser != comp.getUser().getCodeUtilisateur()) {
			throw new ForbiddenException("Vous n'estes pas autorisé a consulter ces avis");
		}

		return listCreneau;

	}

}
