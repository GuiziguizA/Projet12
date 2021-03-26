package org.sid.service;

import java.util.Optional;

import org.sid.classe.Competence;
import org.sid.classe.Users;
import org.sid.config.MessagingConfig;
import org.sid.dao.CompetenceRepository;
import org.sid.dao.UsersRepository;
import org.sid.dto.CompetenceDto;
import org.sid.dto.NotesDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.sid.specification.CompetenceCriteria;
import org.sid.specification.CompetenceSpecificationImpl;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompetenceServiceImpl implements CompetenceService {
	@Autowired
	CompetenceRepository competenceRepository;
	@Autowired
	UsersRepository usersRepository;

	@Override
	public Competence createCompetence(CompetenceDto competenceDto)
			throws EntityAlreadyExistException, ResultNotFoundException {
		Optional<Users> user = usersRepository.findByUsername(competenceDto.getNomUser());

		if (!user.isPresent()) {
			throw new ResultNotFoundException("l'utilisateur nexiste pas");
		}
		Optional<Competence> competence = competenceRepository.findByUserAndNom(user.get(), competenceDto.getNom());
		if (competence.isPresent()) {
			throw new EntityAlreadyExistException("la competence existe deja pour cette utilisateur");
		}

		Competence competence1 = new Competence();
		competence1.setNom(competenceDto.getNom());
		competence1.setDescription(competenceDto.getDescription());
		competence1.setType(competenceDto.getType());
		competence1.setUser(user.get());
		competenceRepository.saveAndFlush(competence1);

		return competence1;
	}

	@Override
	public Competence getCompetence(Long id) throws ResultNotFoundException {

		Optional<Competence> competence = competenceRepository.findById(id);
		if (!competence.isPresent()) {
			throw new ResultNotFoundException("la competence n'existe pas");
		}

		Competence comp = competence.get();

		return comp;

	}

	@Override
	public void deleteCompetence(Long id) throws ResultNotFoundException {

		Optional<Competence> competence = competenceRepository.findById(id);
		if (!competence.isPresent()) {
			throw new ResultNotFoundException("la competence n'existe pas");
		}

		competenceRepository.delete(competence.get());

	}

	@Override
	public Page<Competence> searchCompetence(CompetenceCriteria criteria, int page, int size)
			throws ResultNotFoundException {

		CompetenceSpecificationImpl competenceSpecificationImpl = new CompetenceSpecificationImpl(criteria);

		if (size == 0) {
			throw new ResultNotFoundException("le parametre size est incorrect");
		}

		Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "note");

		Page<Competence> results = competenceRepository.findAll(competenceSpecificationImpl, pageable);

		return results;

	}

	@Override
	public Page<Competence> getCompetencesUser(String nom, int page, int size) throws ResultNotFoundException {

		Pageable pageable = PageRequest.of(page, size);
		Optional<Users> user = usersRepository.findByUsername(nom);
		if (!user.isPresent()) {
			throw new ResultNotFoundException("user introuvable");
		}
		Page<Competence> results = competenceRepository.findByUser(user.get(), pageable);

		return results;

	}

	@RabbitListener(queues = MessagingConfig.QUEUE1)
	public void modifierLaNoteEtNombreDAvis(NotesDto notesDto) {

		Optional<Competence> comp = competenceRepository.findById(notesDto.getIdComp());
		comp.get().setNote(notesDto.getMoyenne());
		comp.get().setNbreAvis(notesDto.getNombreDAvis());
		competenceRepository.saveAndFlush(comp.get());

	}

}
