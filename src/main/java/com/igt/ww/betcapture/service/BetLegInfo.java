package com.igt.ww.betcapture.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

@JsonPropertyOrder({
        "legIndex",
        "eventId",
        "marketId",
        "selectionId",
        "price"
})
public class BetLegInfo {

    @JsonProperty(value = "legIndex", required = false)
    private int legIndex;
    @JsonProperty(value = "eventId", required = true)
    private long eventId;
    @JsonProperty(value = "marketId", required = true)
    private long marketId;
    @JsonProperty(value = "selectionId", required = true)
    private long selectionId;
    @JsonProperty(value = "price", required = true)
    private BigDecimal price;

    @Override
    public String toString() {
        return "BetLegInfo{" +
                "legIndex=" + legIndex +
                ", eventId=" + eventId +
                ", marketId=" + marketId +
                ", selectionId=" + selectionId +
                ", price=" + price +
                '}';
    }

    public int getLegIndex() {
        return legIndex;
    }

    public void setLegIndex(int legIndex) {
        this.legIndex = legIndex;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getMarketId() {
        return marketId;
    }

    public void setMarketId(long marketId) {
        this.marketId = marketId;
    }

    public long getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(long selectionId) {
        this.selectionId = selectionId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
