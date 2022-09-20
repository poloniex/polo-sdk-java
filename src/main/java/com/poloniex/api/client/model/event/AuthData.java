package com.poloniex.api.client.model.event;

import lombok.Data;

/**
 * Auth event data
 */
@Data
public class AuthData {
    /**
     * success of auth
     */
    private Boolean success;

    /**
     * message of auth response
     */
    private String message;

    /**
     * timestamp
     */
    private String ts;
}
