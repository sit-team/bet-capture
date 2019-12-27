package com.igt.ww.betcapture.service;

public class InvalidBetRequestException extends RuntimeException {

    public InvalidBetRequestException(String message) {
        super(message);
    }
}
