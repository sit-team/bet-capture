package com.igt.ww.betcapture.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({
        "id",
        "externalId",
        "betState",
        "stake",
        "maxReturn",
        "timestamp",
        "betLegs"
})
public class BetInfo {

    @JsonProperty(value = "id", required = true)
    private long id;
    @JsonProperty(value = "externalId", required = false)
    private String externalId;
    @JsonProperty(value = "state", required = true)
    private String betState;
    @JsonProperty(value = "stake", required = true)
    private BigDecimal stake;
    @JsonProperty(value = "maxReturn", required = true)
    private BigDecimal maxReturn;
    @JsonProperty(value = "timestamp", required = true)
    private LocalDateTime timestamp;
    @JsonProperty(value = "betLegs", required = true)
    private List<BetLegInfo> betLegInfos;

    @Override
    public String toString() {
        return "BetInfo{" +
                "id=" + id +
                ", externalId='" + externalId + '\'' +
                ", betState='" + betState + '\'' +
                ", stake=" + stake +
                ", maxReturn=" + maxReturn +
                ", timestamp=" + timestamp +
                ", betLegInfos=" + betLegInfos +
                '}';
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public List<BetLegInfo> getBetLegInfos() {
        return betLegInfos;
    }

    public void setBetLegInfos(List<BetLegInfo> betLegInfos) {
        this.betLegInfos = betLegInfos;
    }
}
