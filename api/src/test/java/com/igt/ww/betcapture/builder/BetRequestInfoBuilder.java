package com.igt.ww.betcapture.builder;

import com.igt.ww.betcapture.api.BetRequestInfo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public final class BetRequestInfoBuilder {

    private BigDecimal stake;
    private String externalId;
    private List<BetLegInfoBuilder> betLegInfos = new ArrayList<>();

    private BetRequestInfoBuilder() {}

    public static BetRequestInfoBuilder aBetRequestInfo() {
        return new BetRequestInfoBuilder();
    }

    public BetRequestInfoBuilder withStake(BigDecimal stake) {
        this.stake = stake;
        return this;
    }

    public BetRequestInfoBuilder withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public BetRequestInfoBuilder withBetLegInfos(List<BetLegInfoBuilder> betLegInfos) {
        this.betLegInfos = betLegInfos;
        return this;
    }

    public BetRequestInfoBuilder but() {
        return aBetRequestInfo().withStake(stake).withExternalId(externalId).withBetLegInfos(betLegInfos);
    }

    public BetRequestInfo build() {
        BetRequestInfo betRequestInfo = new BetRequestInfo();
        betRequestInfo.setStake(stake);
        betRequestInfo.setExternalId(externalId);
        betRequestInfo.setBetLegInfos(betLegInfos.stream().map(BetLegInfoBuilder::build).collect(toList()));
        return betRequestInfo;
    }
}
