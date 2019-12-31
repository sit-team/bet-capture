package com.igt.ww.betcapture.maker;

import java.math.*;
import java.util.*;

import com.igt.ww.betcapture.api.*;
import com.igt.ww.betcapture.builder.*;

public class BetInfoMaker {

	public static BetInfo makeSingleBet() {
		return BetInfoBuilder
			.aBetInfo()
			.withId(1L)
			.withBetState("OPEN")
			.withStake(BigDecimal.ONE)
			.withExternalId("Bet1")
			.withBetLegInfos(Collections.singletonList(
				BetLegInfoBuilder.aBetLegInfo()
					.withEventId(1L)
					.withMarketId(1L)
					.withSelectionId(1L)
					.withPrice(BigDecimal.TEN)
			)).build();
	}

	public static BetInfo makeMultipleBet() {
		return BetInfoBuilder
			.aBetInfo()
			.withId(1L)
			.withBetState("OPEN")
			.withStake(BigDecimal.ONE)
			.withExternalId("Bet1")
			.withBetLegInfos(List.of(
				BetLegInfoBuilder.aBetLegInfo()
					.withEventId(1L)
					.withMarketId(1L)
					.withSelectionId(1L)
					.withPrice(BigDecimal.TEN),
				BetLegInfoBuilder.aBetLegInfo()
					.withEventId(2L)
					.withMarketId(2L)
					.withSelectionId(2L)
					.withPrice(BigDecimal.TEN)
			)).build();
	}
}
