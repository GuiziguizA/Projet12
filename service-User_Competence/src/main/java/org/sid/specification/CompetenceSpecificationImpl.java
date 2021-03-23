package org.sid.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import org.sid.classe.Competence;


public class CompetenceSpecificationImpl implements Specification<Competence> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private CompetenceCriteria criteria;
	
	
	public CompetenceSpecificationImpl(CompetenceCriteria criteria) {
		super();
		this.criteria = criteria;
	}
	public CompetenceSpecificationImpl() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * Modification de l'objet predicate
	 *@param Root<Competence> root
	 *@param CriteriaQuery<?> query
	 *@param CriteriaBuilder builder
	 *@return Predicate
	 */
	@Override
	public Predicate toPredicate(Root<Competence> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		Predicate predicate = builder.conjunction();

		if (criteria.getNom()!=null) {
			predicate.getExpressions().add(builder.like(root.get("nom"),"%"+criteria.getNom()+"%"));
			
		}
		if (criteria.getDescription()!=null) {
			predicate.getExpressions().add(builder.like(root.get("description"),"%"+criteria.getDescription()+"%"));
		}
		if (criteria.getType()!=null) {
			predicate.getExpressions().add(builder.like(root.get("type"),"%"+criteria.getType()+"%"));
		}
		return builder.and(predicate);
		
	           
		 
	
	}

	
}