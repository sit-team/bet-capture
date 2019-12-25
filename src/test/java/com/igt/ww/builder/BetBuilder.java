package com.igt.ww.builder;

import com.igt.ww.betcapture.domain.Bet;
import com.igt.ww.betcapture.domain.BetState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class BetBuilder {

    private static long idValue = 1;

    private long id;
    private String externalId;
    private BetState state;
    private BigDecimal stake;
    private BigDecimal maxReturn;
    private List<BetLegBuilder> legs = new ArrayList<>();
    private LocalDateTime timestamp;
    private int version;

    private BetBuilder() {
    }

    public static BetBuilder defaultBet() {
        return new BetBuilder()
                .withStake(BigDecimal.ONE)
                .withExternalId("extId" + idValue++)
                .withState(BetState.ATTEMPTED)
                .withLegs(List.of(BetLegBuilder.defaultBetLeg(1)));
    }

    public static BetBuilder aBet() {
        return new BetBuilder();
    }

    public BetBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public BetBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public BetBuilder withState(BetState state) {
        this.state = state;
        return this;
    }

    public BetBuilder withStake(BigDecimal stake) {
        this.stake = stake;
        return this;
    }

    public BetBuilder withMaxReturn(BigDecimal maxReturn) {
        this.maxReturn = maxReturn;
        return this;
    }

    public BetBuilder withLegs(List<BetLegBuilder> legs) {
        this.legs = legs;
        return this;
    }

    public BetBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public BetBuilder withVersion(int version) {
        this.version = version;
        return this;
    }

    public BetBuilder but() {
        return aBet().withId(id).withExternalId(externalId).withState(state).withStake(stake).withMaxReturn(maxReturn).withLegs(legs).withTimestamp(timestamp).withVersion(version);
    }

    public Bet build() {
        Bet bet = new Bet();
        bet.setId(id);
        bet.setExternalId(externalId);
        bet.setState(state);
        bet.setStake(stake);
        bet.setMaxReturn(maxReturn);
        bet.setLegs(legs.stream().map(BetLegBuilder::build).collect(toList()));
        bet.setTimestamp(timestamp);
        bet.setVersion(version);
        return bet;
    }
}
