package com.poloniex.api.client.spot.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poloniex.api.client.spot.model.event.spot.PoloEvent;

/**
 * The Polo API Callback functional interface defines what users need to implement in order to receive data from websockets
 *
 * @param <T> the event type
 */
public interface PoloApiCallback<T> {

    /**
     * handle response
     *
     * @param response the polo event response
     */
    void onResponse(PoloEvent<T> response) throws JsonProcessingException;

    /**
     * handle failure
     *
     * @param t throwable
     */
    void onFailure(Throwable t);

}
