package sid.org.api;

import org.springframework.http.HttpHeaders;

public interface HeadersService {

	public HttpHeaders createTokenHeaders(String token);

}
