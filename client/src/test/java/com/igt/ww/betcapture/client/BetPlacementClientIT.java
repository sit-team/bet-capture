package com.igt.ww.betcapture.client;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.*;
import com.igt.ww.betcapture.api.*;
import com.igt.ww.wiremock.*;
import feign.*;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.igt.ww.betcapture.maker.BetInfoMaker.*;
import static com.igt.ww.betcapture.maker.BetRequestInfoMaker.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(WireMockExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BetPlacementClientIT {

	private ObjectMapper mapper;
	private BetPlacementAPI client;

	@BeforeAll
	public void setUp () {
		mapper = new ObjectMapper().registerModule(new JavaTimeModule());
		client = BetPlacementClient.client("http://localhost:" + WireMockExtension.port());
	}

	@Test
	void placeBet() throws JsonProcessingException {
		BetInfo stubbedBet = makeSingleBet();
		stubFor(post(urlEqualTo("/bet/place"))
			.withHeader("Content-Type", equalTo("application/json"))
			.willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody(mapper.writeValueAsString(stubbedBet))));

		BetInfo bet = client.placeBet(makeSingleBetRequest());

		assertThat(bet).usingRecursiveComparison().isEqualTo(stubbedBet);
	}

	@Test
	void placeBetIsFailed() throws JsonProcessingException {
		stubFor(post(urlEqualTo("/bet/place"))
			.withHeader("Content-Type", equalTo("application/json"))
			.willReturn(aResponse()
				.withStatus(500)
				.withHeader("Content-Type", "application/json")
				.withBody(mapper.writeValueAsString(new ErrorInfo("Booom!!!")))));

		assertThrows(FeignException.InternalServerError.class, () -> client.placeBet(makeSingleBetRequest()));
	}

	@Test
	void getBets() throws JsonProcessingException {
		List<BetInfo> stubbedBets = List.of(makeSingleBet());
		stubFor(get(urlEqualTo("/bet/all"))
			.willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody(mapper.writeValueAsString(stubbedBets))));

		List<BetInfo> bets = client.getAllBets();

		assertThat(bets).usingRecursiveFieldByFieldElementComparator().isEqualTo(stubbedBets);
	}

	@Test
	void getEmptyBets() {
		stubFor(get(urlEqualTo("/bet/all"))
			.willReturn(aResponse()
				.withStatus(200)
				.withHeader("Content-Type", "application/json")
				.withBody("[]")));

		List<BetInfo> bets = client.getAllBets();

		assertThat(bets).isEmpty();
	}
}