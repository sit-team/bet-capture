package com.igt.ww.betcapture.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "stake",
        "betLegInfos"
})
public class BetRequestInfo {

    @JsonProperty(value = "stake", required = true)
    private BigDecimal stake;

    @JsonProperty(value = "externalId", required = false)
    private String externalId;

    @JsonProperty(value = "betLegs", required = true)
    private List<BetLegInfo> betLegInfos = new ArrayList<>();

    @Override
    public String toString() {
        return "BetRequestInfo{" +
                "stake=" + stake +
                ", externalId='" + externalId + '\'' +
                ", betLegInfos=" + betLegInfos +
                '}';
    }

    public BigDecimal getStake() {
        return stake;
    }

    public void setStake(BigDecimal stake) {
        this.stake = stake;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public List<BetLegInfo> getBetLegInfos() {
        return betLegInfos;
    }

    public void setBetLegInfos(List<BetLegInfo> betLegInfos) {
        this.betLegInfos = betLegInfos;
    }
}
