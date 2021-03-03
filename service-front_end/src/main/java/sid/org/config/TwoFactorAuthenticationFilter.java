package sid.org.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import sid.org.ServiceFrontEndApplication;
import sid.org.service.HeadersService;
import sid.org.service.KeycloakService;

public class TwoFactorAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	AuthenticationManager authenticationManager;
	@Autowired
	KeycloakService keycloakService;

	private static final Logger log = LoggerFactory.getLogger(ServiceFrontEndApplication.class);
	@Value("${keycloak.resource}")
	private String client;

	public TwoFactorAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		// idk why I have to do this, otherwise it's null
		super.setAuthenticationManager(authenticationManager);
	}

	@Autowired
	HeadersService headersService;

	@Value("${api.url}")
	private String apiUrl;

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = request.getParameter(getUsernameParameter());
		String password = request.getParameter(getPasswordParameter());

		String combinedUsername = username + ":" + password;
		HttpSession session = request.getSession();
		log.info(username + "+" + password);
		session.setAttribute("username", username);
		session.setAttribute("password", password);

		log.info(session.getAttribute("token") + "+" + session.getAttribute("username"));
		return combinedUsername;
	}
}