package com.igt.ww.betcapture.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.igt.ww.betcapture.api.*;

@ControllerAdvice
public class BetPlacementControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidBetRequestException.class})
    protected ResponseEntity<ErrorInfo> handleInvalidBetRequestException(InvalidBetRequestException e, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(e.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorInfo(ex.getMessage()));
    }

    @ExceptionHandler({Throwable.class})
    protected ResponseEntity<ErrorInfo> handleUnknownException(Throwable t, WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorInfo(t.getMessage()));
    }
}
