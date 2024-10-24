package com.poloniex.api.client.future.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.future.common.PoloApiException;
import com.poloniex.api.client.future.model.event.future.PoloEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import static com.poloniex.api.client.future.common.PoloApiConstants.EVENT_PONG;

/**
 * Polo API Websocket Listener receives all websocket messages and handles them appropriately using provided callbacks
 */
@Slf4j
public class PoloWebsocketListener<T> extends WebSocketListener {

    public static final String CHANNEL_AUTH = "\"channel\":\"auth\"";

    private final ScheduledThreadPoolExecutor executor;
    private final ObjectMapper objectMapper;

    private TypeReference<PoloEvent<T>> poloEventsTypeReference;

    @Setter
    private ScheduledFuture<?> future;

    @Getter
    private PoloApiCallback<T> poloApiCallback = new PoloLoggingCallback<>();

    /**
     * Instantiates a new Polo websocket listener.
     *
     * @param executor     the executor
     * @param objectMapper the object mapper
     */
    public PoloWebsocketListener(ScheduledThreadPoolExecutor executor, ObjectMapper objectMapper) {
        this.executor = executor;
        this.objectMapper = objectMapper;
    }

    /**
     * Instantiates a new Polo websocket listener.
     *
     * @param executor        the executor
     * @param objectMapper    the object mapper
     * @param poloApiCallback callback which handles polo events
     * @param typeReference   type reference to polo event
     */
    public PoloWebsocketListener(ScheduledThreadPoolExecutor executor, ObjectMapper objectMapper, PoloApiCallback<T> poloApiCallback, TypeReference<PoloEvent<T>> typeReference) {
        this.executor = executor;
        this.objectMapper = objectMapper;
        this.poloApiCallback = poloApiCallback;
        this.poloEventsTypeReference = typeReference;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        log.info("open {}", response.toString());
    }

    /**
     * handles messages received on websocket and sends events to callback while ignoring ping responses
     *
     * @param webSocket websocket
     * @param text      event text
     */
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        try {
            if (!EVENT_PONG.equals(text)) {
                if (text.contains(CHANNEL_AUTH)) {
                    log.info(text);
                } else {
                    PoloEvent<T> poloEvent = objectMapper.readValue(text, poloEventsTypeReference);
                    poloApiCallback.onResponse(poloEvent);
                }
            }
        } catch (Exception e) {
            throw new PoloApiException("message error", e);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        log.info("closing {} {}", code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        log.info("closed {} {}", code, reason);
        future.cancel(true);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        poloApiCallback.onFailure(t);
        future.cancel(true);
    }

}
