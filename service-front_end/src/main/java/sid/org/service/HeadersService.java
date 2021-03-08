package sid.org.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpStatusCodeException;

public interface HeadersService {

	public HttpHeaders createTokenHeaders(HttpServletRequest request) throws HttpStatusCodeException;

	public HttpHeaders giveTokenHeaders() throws HttpStatusCodeException;

	public HttpHeaders createTokenHeaders1(String username, String password) throws HttpStatusCodeException;

}
