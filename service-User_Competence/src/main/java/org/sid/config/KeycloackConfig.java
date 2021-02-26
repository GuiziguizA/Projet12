package org.sid.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloackConfig {

	@Bean
	KeycloakSpringBootConfigResolver configResolver() {
		return new KeycloakSpringBootConfigResolver();
	}

}
