package sid.org.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component("appSettings")
@ConfigurationProperties(prefix = "app.settings")
@Data
public class AppSettings {

	boolean indentJson;

	Rabbit rabbit;

	@Data
	public static class Rabbit {
		String host;
		int port;
		String user;
		String pass;

		String exchange;
		String queue;

		Integer maxConnections;
		Integer maxConcurrentConsumers;
	}

}