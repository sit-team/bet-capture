package com.igt.ww.betcapture.builder;

import java.math.*;
import java.util.*;

import com.igt.ww.betcapture.api.*;

import static java.util.stream.Collectors.*;

public final class BetInfoBuilder {

    private long id;
    private String betState;
    private BigDecimal stake;
    private String externalId;
    private List<BetLegInfoBuilder> betLegInfos = new ArrayList<>();

    private BetInfoBuilder() {}

    public static BetInfoBuilder aBetInfo() {
        return new BetInfoBuilder();
    }

    public BetInfoBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public BetInfoBuilder withBetState(String betState) {
        this.betState = betState;
        return this;
    }

    public BetInfoBuilder withStake(BigDecimal stake) {
        this.stake = stake;
        return this;
    }

    public BetInfoBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public BetInfoBuilder withBetLegInfos(List<BetLegInfoBuilder> betLegInfos) {
        this.betLegInfos = betLegInfos;
        return this;
    }

    public BetInfoBuilder but() {
        return aBetInfo().withStake(stake).withExternalId(externalId).withBetLegInfos(betLegInfos);
    }

    public BetInfo build() {
        var betInfo = new BetInfo();
        betInfo.setId(id);
        betInfo.setBetState(betState);
        betInfo.setStake(stake);
        betInfo.setExternalId(externalId);
        betInfo.setBetLegInfos(betLegInfos.stream().map(BetLegInfoBuilder::build).collect(toList()));
        return betInfo;
    }
}
