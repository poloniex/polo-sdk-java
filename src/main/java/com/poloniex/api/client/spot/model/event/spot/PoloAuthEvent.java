package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Polo Auth Event defines the format for auth events
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoloAuthEvent {

    /**
     * channel name
     */
    private String channel;

    /**
     * list of data events
     */
    private AuthData data;
}
