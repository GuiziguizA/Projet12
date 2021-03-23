package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Creneau;
import sid.org.dto.ChatDateDtoObject;

public interface CreneauService {

	public void createCreneau(ChatDateDtoObject chatDateDtoObject, Long idUser, String userDemandeName,
			HttpServletRequest request) throws HttpStatusCodeException;

	public Page<Creneau> getCreneauxUser(Long idUser, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

	public Page<Creneau> getCreneauxUser1(Long idUser1, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

	public void validerCreneau(Long idCreneau, Long idUser, HttpServletRequest request) throws HttpStatusCodeException;

	public Creneau getCreneau(Long id, HttpServletRequest request) throws HttpStatusCodeException;

}
