package com.igt.ww.betcapture.builder;

import com.igt.ww.betcapture.domain.BetLeg;
import com.igt.ww.betcapture.domain.BetLegId;

import java.math.BigDecimal;

public final class BetLegBuilder {

    private BetLegId id = new BetLegId();
    private long eventId;
    private long marketId;
    private long selectionId;
    private BigDecimal price;

    private BetLegBuilder() {
    }

    public static BetLegBuilder defaultBetLeg(int legIndex) {
        return new BetLegBuilder()
                .withBetLegIndex(legIndex)
                .withEventId(1L)
                .withMarketId(1L)
                .withSelectionId(1L)
                .withPrice(new BigDecimal(2.0));
    }

    public static BetLegBuilder aBetLeg() {
        return new BetLegBuilder();
    }

    public BetLegBuilder withId(BetLegId id) {
        this.id = id;
        return this;
    }

    public BetLegBuilder withBetLegIndex(int index) {
        id.setLegIndex(index);
        return this;
    }

    public BetLegBuilder withEventId(long eventId) {
        this.eventId = eventId;
        return this;
    }

    public BetLegBuilder withMarketId(long marketId) {
        this.marketId = marketId;
        return this;
    }

    public BetLegBuilder withSelectionId(long selectionId) {
        this.selectionId = selectionId;
        return this;
    }

    public BetLegBuilder withPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BetLegBuilder but() {
        return aBetLeg().withId(id).withEventId(eventId).withMarketId(marketId).withSelectionId(selectionId).withPrice(price);
    }

    public BetLeg build() {
        BetLeg betLeg = new BetLeg();
        betLeg.setId(id);
        betLeg.setEventId(eventId);
        betLeg.setMarketId(marketId);
        betLeg.setSelectionId(selectionId);
        betLeg.setPrice(price);
        return betLeg;
    }
}
