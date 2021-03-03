package sid.org.service;

import javax.servlet.http.HttpServletRequest;

public interface UserSession {

	public void loadUserInSession(HttpServletRequest request, String username, String password);

}
