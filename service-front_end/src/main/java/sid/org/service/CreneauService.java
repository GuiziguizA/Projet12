package sid.org.service;

import org.springframework.data.domain.Page;

import sid.org.classe.Chat;
import sid.org.classe.Creneau;
import sid.org.dto.DateDto;
import sid.org.exception.APiUSerAndCompetenceException;
import sid.org.exception.ForbiddenException;

public interface CreneauService {

	public void createCreneau(Chat chat, DateDto dateDto) throws ForbiddenException;

	Page<Creneau> getCreneauxUser(Long idUser, int page, int size) throws APiUSerAndCompetenceException;

}
