package com.igt.ww.betcapture.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;

@Embeddable
public class BetLegId implements Serializable {

	@ManyToOne @JoinColumn(name = "bet_id") private Bet bet;
	@Column(name = "leg_index") private int legIndex;

	public BetLegId() {}

	public BetLegId(Bet bet, int legIndex) {
		this.bet = bet;
		this.legIndex = legIndex;
	}

	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}

	public long getBetId() {
		return bet.getId();
	}

	public int getLegIndex() {
		return legIndex;
	}

	public void setLegIndex(int legIndex) {
		this.legIndex = legIndex;
	}

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BetLegId)) return false;
		var id = (BetLegId) o;
		return getBetId() == id.getBetId() && legIndex == id.legIndex;
	}

	@Override public int hashCode() {
		return Objects.hash(getBetId(), legIndex);
	}

	@Override public String toString() {
		return "BetLegId{" +
			"betId=" + getBetId() +
			", legIndex=" + legIndex +
			'}';
	}
}