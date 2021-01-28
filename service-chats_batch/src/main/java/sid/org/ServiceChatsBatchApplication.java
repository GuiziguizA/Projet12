package sid.org;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceChatsBatchApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServiceChatsBatchApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceChatsBatchApplication.class, args);
		logger.info("service-chat_batch Started........");
	}

}
