package com.concrete.desafiojava.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.concrete.desafiojava.domain.orm.ApplicationUser;

@Configuration
public class UserConfiguration {

	@Bean
	public ApplicationUser user() {
		return new ApplicationUser();
	}

}
