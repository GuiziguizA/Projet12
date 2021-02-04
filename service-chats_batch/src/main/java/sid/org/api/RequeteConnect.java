package sid.org.api;

import sid.org.classe.Requete;
import sid.org.exception.APiUSerAndCompetenceException;

public interface RequeteConnect {

	public Requete getRequete(Long idRequete, Long idUser) throws APiUSerAndCompetenceException;

}
