package org.sid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class ServiceConfigApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServiceConfigApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceConfigApplication.class, args);
		logger.info("service-config Started........");
	}

}
