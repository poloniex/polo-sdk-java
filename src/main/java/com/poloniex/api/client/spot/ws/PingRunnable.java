package com.poloniex.api.client.spot.ws;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.WebSocket;

import static com.poloniex.api.client.spot.common.PoloApiConstants.EVENT_PING;

/**
 * Runnable for sending ping events
 */
@AllArgsConstructor
@Slf4j
public class PingRunnable implements Runnable {

    private final WebSocket webSocket;

    /**
     * sends ping to server
     */
    public void run() {
        log.trace("sending ping");
        webSocket.send(EVENT_PING);
    }
}