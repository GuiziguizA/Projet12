package sid.org.service;

import org.springframework.http.HttpHeaders;

public interface HeadersService {

	public HttpHeaders createTokenHeaders(String token);

}
