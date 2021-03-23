package org.sid.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.sid.classe.Competence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

public class CompetenceSpecificationTriImpl implements Specification<Competence> {

	private static final long serialVersionUID = 2L;
	@Autowired
	private CompetenceCriteria criteria;

	public CompetenceSpecificationTriImpl(CompetenceCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public CompetenceSpecificationTriImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/*
	 * Modification de l'objet predicate
	 * 
	 * @param Root<Competence> root
	 * 
	 * @param CriteriaQuery<?> query
	 * 
	 * @param CriteriaBuilder builder
	 * 
	 * @return Predicate
	 */
	@Override
	public Predicate toPredicate(Root<Competence> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Predicate predicate = builder.conjunction();

		predicate.getExpressions().add(builder.lessThan(root.get("note"), "%" + criteria.getNote() + "%"));

		return builder.and(predicate);

	}

}
