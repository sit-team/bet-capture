package com.igt.ww.betcapture.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Table(name = "bet")
public class Bet {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "bet_id") private long id;
	@Column(name = "external_id") private String externalId;
	@Enumerated(EnumType.STRING) @Column(name = "state") private BetState state;
	@Column(name = "stake") private BigDecimal stake;
	@Column(name = "max_return") private BigDecimal maxReturn;
	@OneToMany(mappedBy = "id.bet", fetch = EAGER, cascade = {ALL}) @OrderBy("id.legIndex") private List<BetLeg> legs = new ArrayList<>();
	@Column(name = "timestamp") private LocalDateTime timestamp;
	@Version @Column(name = "version") private int version;

	public void addLeg(BetLeg betLeg) {
		legs.add(betLeg);
		betLeg.setBet(this);
	}

	public void calculateMaxReturn() {
		maxReturn = stake.multiply(legs.stream().map(BetLeg::getPrice).reduce(BigDecimal.ONE, BigDecimal::multiply));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public BetState getState() {
		return state;
	}

	public void setState(BetState state) {
		this.state = state;
	}

	public BigDecimal getStake() {
		return stake;
	}

	public void setStake(BigDecimal stake) {
		this.stake = stake;
	}

	public BigDecimal getMaxReturn() {
		return maxReturn;
	}

	public void setMaxReturn(BigDecimal maxReturn) {
		this.maxReturn = maxReturn;
	}

	public List<BetLeg> getLegs() {
		return legs;
	}

	public void setLegs(List<BetLeg> legs) {
		this.legs = legs;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
