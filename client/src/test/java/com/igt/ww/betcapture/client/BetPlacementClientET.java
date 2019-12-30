package com.igt.ww.betcapture.client;

import java.util.*;

import org.junit.jupiter.api.*;

import com.igt.ww.betcapture.api.*;
import com.igt.ww.betcapture.maker.*;

import static org.assertj.core.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BetPlacementClientET {

	private BetPlacementAPI client;

	@BeforeAll
	void setUp() {
		client = BetPlacementClientFactory.client("http://localhost:8080");
	}

	@Test
	void placeBet() {
		BetInfo bet = client.placeBet(BetRequestInfoMaker.makeSingleBetRequest());

		assertThat(bet.getId()).isPositive();
		assertThat(bet.getExternalId()).isNotEmpty();
	}

	@Test
	void getAllBets() {
		List<BetInfo> bets = client.getAllBets();

		assertThat(bets).isNotEmpty();
	}
}