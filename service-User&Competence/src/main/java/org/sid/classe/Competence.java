package org.sid.classe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Competence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeCompetence;
	private String nom;
	private String type;
	private String description;
	@ManyToOne
	@JoinColumn(name = "ID_USER")
	private User user;

}
