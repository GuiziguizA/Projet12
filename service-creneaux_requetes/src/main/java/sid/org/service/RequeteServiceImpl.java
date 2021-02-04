package sid.org.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.api.ChatConnect;
import sid.org.api.CompetenceApi;
import sid.org.api.UserConnect;
import sid.org.classe.Competence;
import sid.org.classe.Requete;
import sid.org.classe.Users;
import sid.org.dao.RequeteRepository;
import sid.org.dto.ChatDto;
import sid.org.dto.RequeteDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.EntityAlreadyExistException;
import sid.org.exception.ForbiddenException;
import sid.org.exception.ResultNotFoundException;

@Service
public class RequeteServiceImpl implements RequeteService {
	@Autowired
	private RequeteRepository requeteRepository;
	@Autowired
	private ChatConnect chatConnect;
	@Autowired
	CompetenceApi competenceApi;
	@Autowired
	UserConnect userConnect;

	private static final Logger logger = LoggerFactory.getLogger(RequeteServiceImpl.class);

	@Override
	public Requete createRequete(RequeteDto requeteDto)
			throws EntityAlreadyExistException, APiUSerAndCompetenceException, ForbiddenException {

		Optional<Requete> requete = requeteRepository.findByIdUserAndIdComp(requeteDto.getIdUser(),
				requeteDto.getIdComp());
		if (requete.isPresent()) {
			throw new EntityAlreadyExistException("la requete existe deja");
		}

		userConnect.getUser(requeteDto.getIdUser());
		Competence competence = competenceApi.getCompetence(requeteDto.getIdComp());

		if (requeteDto.getIdUser() == competence.getUser().getCodeUtilisateur()) {
			throw new ForbiddenException("un utilisateur ne peut pas s'envoyer une requete a lui même");
		}

		Requete requete1 = new Requete();

		requete1.setIdComp(requeteDto.getIdComp());
		requete1.setDate(new Date());
		requete1.setStatut("demande");
		requete1.setIdUser(requeteDto.getIdUser());

		requeteRepository.saveAndFlush(requete1);
		return requete1;

	}

	@Override
	public void validerUneRequete(Long id, Long idUser1) throws ResultNotFoundException, EntityAlreadyExistException,
			HttpStatusCodeException, APiUSerAndCompetenceException, ForbiddenException {
		Optional<Requete> requete = requeteRepository.findById(id);
		if (!requete.isPresent()) {
			throw new ResultNotFoundException("requete introuvable");
		}
		logger.info(new Long(requete.get().getIdComp()).toString());
		Competence competence = competenceApi.getCompetence(requete.get().getIdComp());
		if (idUser1 == competence.getUser().getCodeUtilisateur()) {

			try {
				chatConnect.getChat(requete.get().getIdUser(), idUser1);
				throw new EntityAlreadyExistException("le chat existe deja");

			} catch (APiUSerAndCompetenceException e) {
				ChatDto chatDto = new ChatDto(requete.get().getIdUser(), idUser1);
				chatConnect.createChat(chatDto, id);
			}

			requete.get().setStatut("valide");
			requeteRepository.saveAndFlush(requete.get());
		} else {
			throw new ForbiddenException("action interdite");
		}

	}

	@Override
	public Requete getRequete(Long id, Long idUser)
			throws ResultNotFoundException, ForbiddenException, APiUSerAndCompetenceException {

		Optional<Requete> requete = requeteRepository.findById(id);
		if (!requete.isPresent()) {
			throw new ResultNotFoundException("le requete est introuvable");
		}

		Users user = userConnect.getUser(idUser);
		Competence comp = competenceApi.getCompetence(requete.get().getIdComp());

		if (requete.get().getIdUser() == idUser || comp.getUser().getCodeUtilisateur() == idUser) {
			return requete.get();
		} else {
			throw new ForbiddenException("Vous n'avez pas accès a cette requete");
		}

	}

	@Override
	public Page<Requete> getRequetes(Long idUser) throws APiUSerAndCompetenceException {
		Pageable pageable = PageRequest.of(0, 2);
		userConnect.getUser(idUser);
		Page<Requete> requete = requeteRepository.findByIdUser(idUser, pageable);
		return requete;

	}

	@Override
	public void deleteRequete(Long id, Long idUser)
			throws ResultNotFoundException, APiUSerAndCompetenceException, ForbiddenException {
		Optional<Requete> requete = requeteRepository.findById(id);
		if (!requete.isPresent()) {
			throw new ResultNotFoundException("le requete est introuvable");
		}

		Users user = userConnect.getUser(idUser);
		Competence comp = competenceApi.getCompetence(requete.get().getIdComp());

		if (requete.get().getIdUser() == idUser || comp.getUser().getCodeUtilisateur() == idUser) {
			requeteRepository.delete(requete.get());
		} else {
			throw new ForbiddenException("Vous n'avez pas accès a cette requete");
		}

	}

}