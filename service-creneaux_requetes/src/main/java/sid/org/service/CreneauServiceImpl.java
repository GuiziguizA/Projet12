package sid.org.service;

import java.util.Date;
import java.util.List;
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
	public Creneau createCreneau(CreneauDto creneauDto, Long idRequete, Long idUserDemande)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException,
			ResultNotFoundException {

		List<Creneau> creneau = creneauRepository.findByIdUserAndIdUser1AndIdCompAndStatut(creneauDto.getIdUser(),
				creneauDto.getIdUser1(), creneauDto.getIdComp(), "valide");
		List<Creneau> creneau2 = creneauRepository.findByIdUserAndIdUser1AndIdCompAndStatut(creneauDto.getIdUser1(),
				creneauDto.getIdUser(), creneauDto.getIdComp(), "valide");

		if (!creneau.isEmpty()) {
			throw new EntityAlreadyExistException("Un creneau validé existe deja pour cette competence");
		}
		if (!creneau2.isEmpty()) {
			throw new EntityAlreadyExistException("Un creneau validé existe deja pour cette competence");
		}

		/*
		 * if (creneau.isPresent()) { if (creneau.get().getStatut() == "valide") { throw
		 * new EntityAlreadyExistException("Un creneau a deja été reservé"); } } if
		 * (creneau2.isPresent()) { if (creneau2.get().getStatut() == "valide") { throw
		 * new EntityAlreadyExistException("Un creneau a deja été reservé"); } }
		 */
		Optional<Requete> requete = requeteRepository.findById(idRequete);

		if (!requete.isPresent()) {
			throw new ResultNotFoundException("la requete est introuvable");
		}

		// chatConnect verifie si le chat existe

		chatConnect.getChat(creneauDto.getIdUser(), creneauDto.getIdUser1(), idRequete);

		Creneau creneau1 = new Creneau();
		creneau1.setIdComp(creneauDto.getIdComp());
		creneau1.setDate(new Date());
		creneau1.setIdUser(creneauDto.getIdUser());
		creneau1.setIdUser1(creneauDto.getIdUser1());

		creneau1.setIdUserDemande(idUserDemande);
		if (idUserDemande == creneauDto.getIdUser1()) {
			creneau1.setIdUserRecoit(creneauDto.getIdUser());
		} else {
			creneau1.setIdUserRecoit(creneauDto.getIdUser1());
		}
		creneau1.setStatut("demande");
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
			throw new ForbiddenException("Vous n'etes pas autorisé a valider le creneaux");
		}
		if (creneau.get().getStatut().equals("demande")) {
			creneau.get().setStatut("valide");
			List<Creneau> listCreneau = creneauRepository.findByIdChatAndStatut(creneau.get().getIdChat(), "demande");
			if (!listCreneau.isEmpty()) {
				creneauRepository.deleteAll(listCreneau);
			}
		} else if (creneau.get().getStatut().equals("valide")) {
			creneau.get().setStatut("passe");
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
		// chatConnect verifie si le chat existe
		/* chatConnect.getChat(idUser, idUser1); */
		return creneau.get();

	}

	@Override
	public Page<Creneau> getCreneauxUser(Long idUser, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<Creneau> creneaux = creneauRepository.findByIdUserDemande(idUser, pageable);

		return creneaux;

	}

	@Override
	public Page<Creneau> getCreneauxUser1(Long idUser1, int page, int size) {

		Pageable pageable = PageRequest.of(page, size);
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

	/*
	 * @Override public void deleteCreneau(Long id, Long idUser, Long idUser1)
	 * throws ResultNotFoundException { Optional<Creneau> creneau =
	 * creneauRepository.findById(id); if (!creneau.isPresent()) { throw new
	 * ResultNotFoundException("creneau introuvable"); } // chatConnect verifie si
	 * le chat existe chatConnect.getChat(idUser, idUser1);
	 * creneauRepository.delete(creneau.get());
	 * 
	 * }
	 */

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
