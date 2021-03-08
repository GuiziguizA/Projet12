package sid.org.service;

import org.springframework.web.client.HttpStatusCodeException;

public interface HttpService {

	/**
	 * afficher uniquelment le message de l'exception
	 * 
	 * @param error
	 * @return le message d'erreur
	 */
	String traiterLesExceptionsApi(HttpStatusCodeException error);

}
