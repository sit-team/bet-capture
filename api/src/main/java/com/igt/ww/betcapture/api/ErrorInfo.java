package com.igt.ww.betcapture.api;

import com.fasterxml.jackson.annotation.*;

@JsonPropertyOrder({
   "message"
})
public class ErrorInfo {

    @JsonProperty(value = "message", required = true)
    private String message;

    public ErrorInfo() {}

    public ErrorInfo(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override public String toString() {
        return message;
    }
}
