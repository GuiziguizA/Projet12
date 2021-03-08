package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.client.HttpStatusCodeException;

public interface UserSession {

	public void loadUserInSession(HttpServletRequest request, String username, String password)
			throws HttpStatusCodeException;

}
