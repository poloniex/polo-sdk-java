package com.poloniex.api.client.future.common;

/**
 * Exception class
 */
public class PoloApiException extends RuntimeException {
    public PoloApiException(String message) {
        super(message);
    }

    public PoloApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
