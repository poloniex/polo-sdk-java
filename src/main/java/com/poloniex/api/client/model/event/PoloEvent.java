package com.poloniex.api.client.model.event;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Polo Event defines the format of all websocket events
 *
 * @param <T> event type
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PoloEvent<T> {

    /**
     * message event type
     */
    private String event;

    /**
     * channel name
     */
    private String channel;

    /**
     * symbol names
     */
    private List<String> symbols;

    /**
     * list of data events
     */
    private List<T> data;

    /**
     * snapshot, update
     */
    private String action;

    /**
     * error message
     */
    private String message;
}
