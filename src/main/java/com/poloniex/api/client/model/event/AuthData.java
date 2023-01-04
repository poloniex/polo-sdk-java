package com.poloniex.api.client.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Auth event data
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
