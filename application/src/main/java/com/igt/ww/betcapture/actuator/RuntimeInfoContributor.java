package com.igt.ww.betcapture.actuator;

import java.lang.management.*;
import java.util.*;

import org.apache.catalina.startup.*;
import org.springframework.boot.*;
import org.springframework.boot.actuate.info.*;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Component @Profile("!dev")
public class RuntimeInfoContributor implements InfoContributor {

	@Override public void contribute(Info.Builder builder) {
		builder.withDetail("runtime", Map.of(
			"jvm.version", ManagementFactory.getRuntimeMXBean().getVmVersion(),
			"spring-boot.version", getVersion(SpringApplication.class),
			"spring.version", getVersion(ApplicationContext.class),
			"tomcat.version", getVersion(Tomcat.class)
		));
	}

	private String getVersion(Class<?> cls) {
		return cls.getPackage().getImplementationVersion();
	}
}
