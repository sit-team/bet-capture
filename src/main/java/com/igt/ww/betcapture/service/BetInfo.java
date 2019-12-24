package com.igt.ww.betcapture.service;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BetInfo {

    @JsonProperty()
    private long id;
    @JsonProperty()
    private String externalId;
    @JsonProperty
    private String betState;
    @JsonProperty
    private BigDecimal stake;
    @JsonProperty
    private BigDecimal maxReturn;
    @JsonProperty
    private List<BetLegInfo> betLegInfos;
    @JsonProperty
    private LocalDateTime timestamp;

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

    public String getBetState() {
        return betState;
    }

    public void setBetState(String betState) {
        this.betState = betState;
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

    public List<BetLegInfo> getBetLegInfos() {
        return betLegInfos;
    }

    public void setBetLegInfos(List<BetLegInfo> betLegInfos) {
        this.betLegInfos = betLegInfos;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
