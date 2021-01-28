package sid.org.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Requete;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class RequeteConnectImpl implements RequeteConnect {

	@Value("${api.url}")
	private String url;

	@Override
	public Requete getRequete(Long idRequete) throws APiUSerAndCompetenceException {

		String uri = url + "/compagny-creneaux_requetes/requete";

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Requete> user = rt.getForEntity(uri, Requete.class);
			return user.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("Impossible d'effectuer cette action");
		}

	}
}
