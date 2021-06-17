package com.igt.ww.betcapture.client;

import java.util.*;

import org.junit.jupiter.api.*;

import com.igt.ww.betcapture.api.*;
import com.igt.ww.betcapture.maker.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.*;
import static org.junit.jupiter.api.TestInstance.*;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class BetPlacementClientET {

	private BetPlacementAPI client;

	@BeforeAll
	void setUp() {
		client = BetPlacementClient.client("http://localhost:8080");
	}

	@Test @Order(1)
	void placeBet() {
		var bet = client.placeBet(BetRequestInfoMaker.makeSingleBetRequest());

		assertThat(bet.getId()).isPositive();
		assertThat(bet.getExternalId()).isNotEmpty();
	}

	@Test @Order(2)
	void getAllBets() {
		var bets = client.getAllBets();

		assertThat(bets).isNotEmpty();
	}
}