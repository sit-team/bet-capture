package com.igt.ww.betcapture.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bet_leg")
public class BetLeg {

	@EmbeddedId private BetLegId id = new BetLegId();
	@Column(name = "event_id") private long eventId;
	@Column(name = "market_id") private long marketId;
	@Column(name = "selection_id") private long selectionId;
	@Column(name = "price") private BigDecimal price;

    public BetLeg() {}

    public BetLeg(int legIndex) {
        id.setLegIndex(legIndex);
    }

    public BetLegId getId() {
        return id;
    }

    public void setId(BetLegId id) {
        this.id = id;
    }

    public long getBetId() {
        return id.getBetId();
    }

    public Bet getBet() {
        return id.getBet();
    }

    public void setBet(Bet bet) {
        id.setBet(bet);
    }

    public int getLegIndex() {
        return id.getLegIndex();
    }

    public void setLegIndex(int legIndex) {
        id.setLegIndex(legIndex);
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
