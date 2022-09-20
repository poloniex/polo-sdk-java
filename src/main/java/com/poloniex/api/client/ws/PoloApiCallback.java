package com.poloniex.api.client.ws;

import com.poloniex.api.client.model.event.PoloEvent;

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
    void onResponse(PoloEvent<T> response);

    /**
     * handle failure
     *
     * @param t throwable
     */
    void onFailure(Throwable t);

}
