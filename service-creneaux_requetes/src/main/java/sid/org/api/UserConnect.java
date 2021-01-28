package sid.org.api;

import sid.org.classe.Users;
import sid.org.exception.APiUSerAndCompetenceException;

public interface UserConnect {

	public Users getUser(Long idUser) throws APiUSerAndCompetenceException;

}
