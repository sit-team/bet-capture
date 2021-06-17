package com.igt.ww.betcapture.builder;

import com.igt.ww.betcapture.api.BetLegInfo;

import java.math.BigDecimal;

public final class BetLegInfoBuilder {

    private int legIndex;
    private long eventId;
    private long marketId;
    private long selectionId;
    private BigDecimal price;

    private BetLegInfoBuilder() {
    }

    public static BetLegInfoBuilder aBetLegInfo() {
        return new BetLegInfoBuilder();
    }

    public BetLegInfoBuilder withLegIndex(int legIndex) {
        this.legIndex = legIndex;
        return this;
    }

    public BetLegInfoBuilder withEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public BetLegInfoBuilder withMarketId(long marketId) {
        this.marketId = marketId;
        return this;
    }

    public BetLegInfoBuilder withSelectionId(long selectionId) {
        this.selectionId = selectionId;
        return this;
    }

    public BetLegInfoBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BetLegInfoBuilder but() {
        return aBetLegInfo().withLegIndex(legIndex).withEventId(eventId).withMarketId(marketId).withSelectionId(selectionId).withPrice(price);
    }

    public BetLegInfo build() {
        var betLegInfo = new BetLegInfo();
        betLegInfo.setLegIndex(legIndex);
        betLegInfo.setEventId(eventId);
        betLegInfo.setMarketId(marketId);
        betLegInfo.setSelectionId(selectionId);
        betLegInfo.setPrice(price);
        return betLegInfo;
    }
}
