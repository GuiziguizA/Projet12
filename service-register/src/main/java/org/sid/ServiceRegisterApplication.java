package org.sid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ServiceRegisterApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServiceRegisterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceRegisterApplication.class, args);
		logger.info("service-register Started........");
	}

}
