package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Requete;

public interface RequeteService {

	public Page<Requete> getRequetes(Long idUserComp, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

	public void validerRequete(Long idRequete, Long idUser, HttpServletRequest request) throws HttpStatusCodeException;

	public void createRequete(Long idComp, Long idUser, HttpServletRequest request) throws HttpStatusCodeException;

	public Page<Requete> getRequetesU(Long idUser, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

}
