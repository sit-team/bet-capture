package com.igt.ww.betcapture.domain;

import java.math.*;
import java.util.*;
import javax.persistence.*;

@Entity @IdClass(BetLegId.class)
@Table(name = "bet_leg")
public class BetLeg {

	@Id @Column(name = "bet_id") private long betId;
	@Id @Column(name = "leg_index") private int legIndex;
	@ManyToOne @MapsId("betId") private Bet bet;
	@Column(name = "event_id") private long eventId;
	@Column(name = "market_id") private long marketId;
	@Column(name = "selection_id") private long selectionId;
	@Column(name = "price") private BigDecimal price;

	public BetLeg() {}

	public BetLeg(Bet bet, int legIndex) {
		betId = bet.getId();
		this.legIndex = legIndex;
	}

	public long getBetId() {
		return betId;
	}

	public void setBetId(long betId) {
		this.betId = betId;
	}

	public int getLegIndex() {
		return legIndex;
	}

	public void setLegIndex(int legIndex) {
		this.legIndex = legIndex;
	}

	public Bet getBet() {
		return bet;
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

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BetLegId)) return false;
		var leg = (BetLeg) o;
		return betId == leg.betId && legIndex == leg.legIndex;
	}

	@Override public int hashCode() {
		return Objects.hash(betId, legIndex);
	}
}
