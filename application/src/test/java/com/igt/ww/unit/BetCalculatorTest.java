package com.igt.ww.unit;

import com.igt.ww.betcapture.domain.Bet;
import com.igt.ww.betcapture.builder.BetBuilder;
import com.igt.ww.betcapture.builder.BetLegBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class BetCalculatorTest {

    @Test
    void betWithOneLegWillSuccessfullyCalculateMaxReturn() {
        Bet bet = BetBuilder
                .aBet()
                .withStake(BigDecimal.ONE)
                .withLegs(singletonList(BetLegBuilder
                        .aBetLeg()
                        .withPrice(new BigDecimal(50))))
                .build();
        bet.calculateMaxReturn();

        assertThat(bet.getMaxReturn()).isEqualTo(new BigDecimal(50));
    }

    @Test
    void betWilMultipleLetsWillSuccessfullyCalculateMaxReturn() {
        Bet bet = BetBuilder
                .aBet()
                .withStake(BigDecimal.TEN)
                .withLegs(List.of(BetLegBuilder
                                .aBetLeg()
                                .withPrice(new BigDecimal(5)),
                        BetLegBuilder
                                .aBetLeg()
                                .withPrice(new BigDecimal(5)),
                        BetLegBuilder
                                .aBetLeg()
                                .withPrice(new BigDecimal(5))))
                .build();
        bet.calculateMaxReturn();

        assertThat(bet.getMaxReturn()).isEqualTo(new BigDecimal(1250));
    }
}
