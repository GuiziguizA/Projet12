package org.sid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ServiceProxyApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServiceProxyApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceProxyApplication.class, args);
		logger.info("service-proxy Started........");
	}

}
