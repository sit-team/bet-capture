package com.igt.ww.betcapture.api;

import java.math.*;
import java.time.*;
import java.util.*;

import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.*;

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

    @JsonProperty(value = "id", required = true) @Min(1L)
    private long id;
    @JsonProperty(value = "externalId", required = true) @NotBlank @Size(min = 1, max = 50)
    private String externalId;
    @JsonProperty(value = "state", required = true) @NotBlank @Size(min = 1, max = 20)
    private String betState;
    @JsonProperty(value = "stake", required = true) @NotNull @Min(0)
    private BigDecimal stake;
    @JsonProperty(value = "maxReturn", required = true) @NotNull @Min(0)
    private BigDecimal maxReturn;
    @JsonProperty(value = "timestamp", required = true) @NotNull
    private LocalDateTime timestamp;
    @JsonProperty(value = "betLegs", required = true) @NotEmpty @Size(min = 1, max = 50)
    private List<BetLegInfo> betLegInfos;

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

    @Override public String toString() {
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
}
