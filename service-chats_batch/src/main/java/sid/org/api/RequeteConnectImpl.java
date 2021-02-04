package sid.org.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Requete;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class RequeteConnectImpl implements RequeteConnect {
	private static final Logger logger = LoggerFactory.getLogger(RequeteConnectImpl.class);

	@Value("${api.url}")
	private String url;

	@Override
	public Requete getRequete(Long idRequete, Long idUser)
			throws APiUSerAndCompetenceException, HttpStatusCodeException {

		String uri = url + "/compagny-creneaux_requetes/requete?id=" + idRequete + "&idUser=" + idUser;

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Requete> requete = rt.getForEntity(uri, Requete.class);
			logger.info("connection api requete reussi");
			return requete.getBody();

		} catch (HttpStatusCodeException e) {
			logger.info("connection api requete échoué");
			throw new APiUSerAndCompetenceException(
					"l'api getRequete ne marche pas et l'erreure est : " + e.getMessage());
		}

	}
}
