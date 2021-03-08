package sid.org.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;

@Service
public class HttpServiceImpl implements HttpService {

	@Override
	public String traiterLesExceptionsApi(HttpStatusCodeException error) {
		String messageErreur = error.getMessage();

		String[] split = messageErreur.split("message");
		if (split.length == 1) {
			return "error";
		} else {

			String[] split1 = split[1].split(":");
			String[] split2 = split1[1].split(",");
			String result = split2[0];

			return result.replaceAll("\"", "");
		}

	}
}