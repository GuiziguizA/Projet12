package sid.org.api;

import sid.org.classe.Requete;
import sid.org.exception.APiUSerAndCompetenceException;

public interface RequeteConnect {

	Requete getRequete(Long idRequete) throws APiUSerAndCompetenceException;

}
