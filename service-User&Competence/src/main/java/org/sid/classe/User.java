package org.sid.classe;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codeUtilisateur;
	private String nom;
	@Column(unique = true)
	private String mail;
	private String adresse;
	private String motDePasse;
	@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres")
	private String codePostal;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Collection<Competence> competences;
	@ManyToOne
	@JoinColumn(name = "ID_ROLES")
	private Roles roles;

}
