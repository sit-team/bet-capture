package com.igt.ww.postgresql.test;

import org.springframework.boot.test.util.*;
import org.springframework.context.*;

public class PostgreSQLDataSourceInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public void initialize(ConfigurableApplicationContext context) {
		var postgreSQLContainer = PostgreSQLExtension.getContainer();
		TestPropertyValues.of(
			"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
			"spring.datasource.username=" + postgreSQLContainer.getUsername(),
			"spring.datasource.password=" + postgreSQLContainer.getPassword()
		).applyTo(context.getEnvironment());
	}
}
