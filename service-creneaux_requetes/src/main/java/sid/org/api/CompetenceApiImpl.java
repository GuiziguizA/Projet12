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
	public Competence getCompetence(Long idcompetence) throws APiUSerAndCompetenceException, HttpStatusCodeException {
		System.out.println(idcompetence);
		String uri = url + "/compagny-user_competence/competence/" + idcompetence;
		String uri1 = "http://localhost:8089/compagny-user_competence/competence/1";
		RestTemplate rt = new RestTemplate();

		try {
			ResponseEntity<Competence> comp = rt.getForEntity(uri, Competence.class);
			return comp.getBody();
		} catch (HttpStatusCodeException e) {
			throw new APiUSerAndCompetenceException(
					"l'api getCompetence ne marche pas et l'erreure est : " + e.getMessage());
		}

	}
}
