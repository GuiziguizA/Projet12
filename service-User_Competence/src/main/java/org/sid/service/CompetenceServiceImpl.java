package org.sid.service;

import java.util.Optional;

import org.sid.classe.Competence;
import org.sid.classe.Roles;
import org.sid.classe.Users;
import org.sid.dao.CompetenceRepository;
import org.sid.dto.CompetenceDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.sid.specification.CompetenceCriteria;
import org.sid.specification.CompetenceSpecificationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompetenceServiceImpl implements CompetenceService {
	@Autowired
	CompetenceRepository competenceRepository;

	@Override
	public Competence createCompetence(CompetenceDto competenceDto) throws EntityAlreadyExistException {

		Optional<Competence> competence = competenceRepository.findByUserAndNom(competenceDto.getUser(),
				competenceDto.getNom());
		if (competence.isPresent()) {
			throw new EntityAlreadyExistException("la competence existe deja pour cette utilisateur");
		}

		Competence competence1 = new Competence();
		competence1.setNom(competenceDto.getNom());
		competence1.setDescription(competenceDto.getDescription());
		competence1.setType(competenceDto.getType());
		competence1.setUser(competenceDto.getUser());
		competenceRepository.saveAndFlush(competence1);

		return competence1;
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

		Pageable pageable = PageRequest.of(page, size);

		Page<Competence> results = competenceRepository.findAll(competenceSpecificationImpl, pageable);

		return results;

	}

	@Override
	public CompetenceDto getCompetenceDto() {
		Roles role = new Roles("user");
		Users user = new Users(1L, "bob", "bob@gmail.com", "3 rue du cerisier", "bob", "45125", null, role);

		CompetenceDto competenceDto = new CompetenceDto("changer un pneu", "mecanique",
				"j'ai une machine permettant de changer les pneus d'une voiture", user);
		return competenceDto;

	}

	@Override
	public CompetenceCriteria getCompetenceCriteria() {
		CompetenceCriteria comp = new CompetenceCriteria(null, "cuisine", null);

		return comp;

	}

}
