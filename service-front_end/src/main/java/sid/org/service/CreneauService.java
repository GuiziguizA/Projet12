package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpStatusCodeException;

import sid.org.classe.Creneau;
import sid.org.dto.ChatDateDtoObject;

public interface CreneauService {

	public void createCreneau(ChatDateDtoObject chatDateDtoObject, HttpServletRequest request)
			throws HttpStatusCodeException;

	public Page<Creneau> getCreneauxUser(Long idUser, int page, int size, HttpServletRequest request)
			throws HttpStatusCodeException;

}
