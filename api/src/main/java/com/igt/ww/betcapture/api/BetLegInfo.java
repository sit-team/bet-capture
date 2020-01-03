package com.igt.ww.betcapture.api;

import java.math.*;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
   "legIndex",
   "eventId",
   "marketId",
   "selectionId",
   "price"
})
public class BetLegInfo {

    @JsonProperty(value = "legIndex", required = true) @Min(1)
    private int legIndex;
    @JsonProperty(value = "eventId", required = true) @Min(1L)
    private long eventId;
    @JsonProperty(value = "marketId", required = true) @Min(1L)
    private long marketId;
    @JsonProperty(value = "selectionId", required = true) @Min(1L)
    private long selectionId;
    @JsonProperty(value = "price", required = true) @NotNull @Min(1)
    private BigDecimal price;

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

    @Override public String toString() {
        return "BetLegInfo{" +
           "legIndex=" + legIndex +
           ", eventId=" + eventId +
           ", marketId=" + marketId +
           ", selectionId=" + selectionId +
           ", price=" + price +
           '}';
    }
}
