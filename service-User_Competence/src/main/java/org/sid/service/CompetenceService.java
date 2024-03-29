package org.sid.service;

import org.sid.classe.Competence;
import org.sid.dto.CompetenceDto;
import org.sid.exception.EntityAlreadyExistException;
import org.sid.exception.ResultNotFoundException;
import org.sid.specification.CompetenceCriteria;
import org.springframework.data.domain.Page;

public interface CompetenceService {

	public Competence createCompetence(CompetenceDto competenceDto)
			throws EntityAlreadyExistException, ResultNotFoundException;

	public void deleteCompetence(Long id) throws ResultNotFoundException;

	public Page<Competence> searchCompetence(CompetenceCriteria criteria, int page, int size)
			throws ResultNotFoundException;

	public Competence getCompetence(Long id) throws ResultNotFoundException;

	public Page<Competence> getCompetencesUser(String nom, int page, int size) throws ResultNotFoundException;

}
