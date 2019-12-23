package com.igt.ww.betcapture;

import java.math.*;
import java.time.*;
import java.util.*;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import com.igt.ww.betcapture.domain.*;
import com.igt.ww.postgresql.test.*;

import static org.assertj.core.api.Assertions.*;

@PostgreSQLTest
@DataJpaTest
class BetRepositoryIT {

	@Autowired private BetRepository betRepository;
	private int extId;

	@Test
	void betIsCreated() {
		var bet = makeBet();
		betRepository.save(bet);
		betRepository.flush();

		var bet2 = betRepository.findById(bet.getId());

		assertThat(bet2).isNotEmpty();
		assertThat(bet2.get()).isEqualToComparingFieldByField(bet);
	}

	private Bet makeBet() {
		var bet = new Bet();
		bet.setExternalId("LTM-" + ++extId);
		bet.setState(BetState.OPEN);
		bet.setStake(new BigDecimal("10"));
		bet.setMaxReturn(new BigDecimal("20"));
		bet.setTimestamp(LocalDateTime.now());
		BetLeg leg = new BetLeg(1);
		leg.setEventId(1L);
		leg.setMarketId(11L);
		leg.setSelectionId(111L);
		leg.setPrice(new BigDecimal("2.1"));
		bet.addLeg(leg);
		return bet;
	}

	@Test
	void openBetsAreQueried() {
		Bet bet = makeBet();
		betRepository.save(bet);
		betRepository.flush();

		List<Bet> openBets = betRepository.findAllByState(BetState.OPEN);
		assertThat(openBets).hasSize(1);
		List<BetLeg> legs = openBets.get(0).getLegs();
		assertThat(legs).hasSize(1);
		assertThat(legs.get(0).getBet().getState()).isEqualTo(BetState.OPEN);
	}

	@Test
	void betStateCountsAreQueried() {
		Bet bet1 = makeBet();
		betRepository.save(bet1);

		Bet bet2 = makeBet();
		betRepository.save(bet2);

		betRepository.flush();

		SortedMap<BetState, Integer> stateCounts = betRepository.findBetStateCounts();
		assertThat(stateCounts).containsExactly(entry(BetState.OPEN, 2));
	}
}
