package com.poloniex.api.client.future.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.future.model.event.future.PoloEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Example callback for websocket subscriptions. It will print events to log.
 *
 * @param <T> Event Type
 */
@Slf4j
public class PoloLoggingCallback<T> implements PoloApiCallback<T> {

    private final ObjectMapper objectMapper;

    /**
     * Instantiates a new Polo logging callback.
     */
    public PoloLoggingCallback() {
        objectMapper = new ObjectMapper();
    }

    /**
     * log response
     *
     * @param response response event
     */
    @Override
    public void onResponse(PoloEvent<T> response) {
        try {
            String s = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
            log.info(s);
        } catch (JsonProcessingException e) {
            log.error("Could not print response", e);
        }
    }

    /**
     * log error
     *
     * @param t throwable
     */
    @Override
    public void onFailure(Throwable t) {
        log.error("Websocket failure", t);
    }
}
