package com.igt.ww.betcapture.actuator;

import org.springframework.boot.actuate.health.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

@Component @Profile("!dev")
public class BetCaptureHealthIndicator implements HealthIndicator {

	@Override public Health health() {
		return Health.up().withDetail("Bet Capture", "Available").build();
	}
}
