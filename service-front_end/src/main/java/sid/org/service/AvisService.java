package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Avis;
import sid.org.dto.AvisDto;

public interface AvisService {

	public void createAvis(AvisDto avisDto, HttpServletRequest request, Long idUser) throws HttpStatusCodeException;

	public Page<Avis> getAvis(Long idComp, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

}
