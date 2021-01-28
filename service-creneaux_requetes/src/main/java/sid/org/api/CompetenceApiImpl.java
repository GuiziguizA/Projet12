package sid.org.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import sid.org.classe.Competence;
import sid.org.exception.APiUSerAndCompetenceException;

@Service
public class CompetenceApiImpl implements CompetenceApi {
	@Value("${api.url}")
	private String url;

	@Override
	public Competence getCompetence(Long idUser) throws APiUSerAndCompetenceException {
		String uri = url + "/compagny-user_competence/competence";

		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Competence> comp = rt.getForEntity(uri, Competence.class);
			return comp.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException("Impossible d'effectuer cette action");
		}

	}
}
