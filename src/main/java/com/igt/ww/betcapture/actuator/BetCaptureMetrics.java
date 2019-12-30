package com.igt.ww.betcapture.actuator;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import com.igt.ww.betcapture.domain.*;
import io.micrometer.core.instrument.*;

@Component
@Profile("!dev")
public class BetCaptureMetrics {

	@Autowired private BetRepository betRepository;

	public BetCaptureMetrics(MeterRegistry meterRegistry) {
		meterRegistry.gauge("bet.capture.bets.count", List.of(Tag.of("state", "open")), this, BetCaptureMetrics::getOpenBets);
	}

	public double getOpenBets() {
		return betRepository.findBetStateCounts().getOrDefault(BetState.OPEN, 0);
	}
}
