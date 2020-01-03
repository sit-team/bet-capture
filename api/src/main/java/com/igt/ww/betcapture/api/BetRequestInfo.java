package com.igt.ww.betcapture.api;

import java.math.*;
import java.util.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
    "stake",
    "externalId",
    "betLegInfos"
})
public class BetRequestInfo {

    @JsonProperty(value = "stake", required = true) @NotNull @Min(0)
    private BigDecimal stake;

    @JsonProperty(value = "externalId", required = true) @NotBlank @Size(min = 1, max = 50)
    private String externalId;

    @JsonProperty(value = "betLegs", required = true) @NotEmpty @Size(min = 1, max = 50)
    private List<BetLegInfo> betLegInfos = new ArrayList<>();

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

    @Override public String toString() {
        return "BetRequestInfo{" +
           "stake=" + stake +
           ", externalId='" + externalId + '\'' +
           ", betLegInfos=" + betLegInfos +
           '}';
    }
}
