package com.igt.ww.betcapture.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;

@Embeddable
public class BetLegId implements Serializable {

	private long betId;
	private int legIndex;

	public BetLegId() {}

	public BetLegId(long betId, int legIndex) {
		this.betId = betId;
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

	@Override public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BetLegId)) return false;
		var id = (BetLegId) o;
		return betId == id.betId && legIndex == id.legIndex;
	}

	@Override public int hashCode() {
		return Objects.hash(betId, legIndex);
	}

	@Override public String toString() {
		return "BetLegId{" +
			"betId=" + betId +
			", legIndex=" + legIndex +
			'}';
	}
}