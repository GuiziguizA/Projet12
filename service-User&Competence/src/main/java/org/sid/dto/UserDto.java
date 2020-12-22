package org.sid.dto;

import javax.persistence.Column;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String nom;
	@Column(unique = true)
	private String mail;
	private String adresse;
	private String motDePasse;
	@Size(min = 5, max = 5, message = "le code postal doit etre a 5 chiffres")
	private String codePostal;

}
