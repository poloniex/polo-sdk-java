package com.poloniex.api.client.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.common.PoloApiException;
import com.poloniex.api.client.model.request.AuthParams;
import com.poloniex.api.client.model.request.SubscribeRequest;
import com.poloniex.api.client.model.request.UnsubscribeRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.internal.Util;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.poloniex.api.client.common.PoloApiConstants.EVENT_SUBSCRIBE;
import static com.poloniex.api.client.common.PoloApiConstants.EVENT_UNSUBSCRIBE;

/**
 * The base websocket client for both public and private websockets
 */
@Slf4j
public class PoloBaseWebsocketClient {

    private final OkHttpClient client;
    private final String baseUrl;
    protected final ObjectMapper objectMapper;
    protected final ScheduledThreadPoolExecutor executor;
    private static final int pingInterval = 20;
    private static final TimeUnit pingTimeUnit = TimeUnit.SECONDS;

    /**
     * Instantiates a new Polo base websocket.
     *
     * @param client  http client
     * @param baseUrl host base url
     */
    public PoloBaseWebsocketClient(OkHttpClient client, String baseUrl) {
        this.client = client;
        this.baseUrl = baseUrl;
        executor = new ScheduledThreadPoolExecutor(1, Util.threadFactory("websocket-heartbeat", false));
        objectMapper = new ObjectMapper();
    }

    /**
     * creates a new websocket that relays messages to the listener
     * @param listener polo websocket listener
     * @return ok http websocket
     */
    public WebSocket createNewWebSocket(PoloWebsocketListener<?> listener) {
        Request request = new Request.Builder()
                .url(baseUrl)
                .build();
        WebSocket webSocket = client.newWebSocket(request, listener);


        int pingIntervalMillis = Util.checkDuration("pingInterval", pingInterval, pingTimeUnit);
        if (pingIntervalMillis != 0L) {
            PingRunnable pingRunnable = new PingRunnable(webSocket);
            ScheduledFuture<?> future = this.executor.scheduleAtFixedRate(pingRunnable, pingIntervalMillis, pingIntervalMillis, TimeUnit.MILLISECONDS);
            listener.setFuture(future);
            log.debug("scheduled ping @ rate {} ms", pingIntervalMillis);
        }

        return webSocket;
    }

    /**
     * Subscribe to websocket channels
     *
     * @param webSocket ok http websocket
     * @param channels channel names
     * @return success of subscription
     */
    public boolean subscribe(WebSocket webSocket, List<String> channels) {
        return subscribe(webSocket, channels, null, null, null, null, null);
    }

    /**
     * Subscribe to websocket channels
     *
     * @param webSocket ok http websocket
     * @param channels channel names
     * @param symbols  symbols names
     * @return success of subscription
     */
    public boolean subscribe(WebSocket webSocket, List<String> channels, List<String> symbols) {
        return subscribe(webSocket, channels, symbols, null, null, null, null);
    }

    /**
     * Subscribe to websocket channels
     *
     * @param webSocket ok http websocket
     * @param channels channel names
     * @param symbols  symbols names
     * @param depth depth
     * @return success of subscription
     */
    public boolean subscribe(WebSocket webSocket, List<String> channels, List<String> symbols, Integer depth) {
        return subscribe(webSocket, channels, symbols, depth, null, null, null);
    }

    /**
     * Subscribe to websocket channels
     *
     * @param webSocket ok http websocket
     * @param channels channel names
     * @param key           the api key
     * @param signTimestamp the sign timestamp
     * @param signature     the signature
     * @return success of subscription
     */
    public boolean subscribe(WebSocket webSocket, List<String> channels, String key, Long signTimestamp, String signature) {
        return subscribe(webSocket, channels, null, null, key, signTimestamp, signature);
    }

    /**
     * Subscribe to websocket channels
     *
     * @param webSocket ok http websocket
     * @param channels channel names
     * @param symbols symbol names
     * @param depth depth
     * @param key           the api key
     * @param signTimestamp the sign timestamp
     * @param signature     the signature
     * @return success of subscription
     */
    public boolean subscribe(WebSocket webSocket, List<String> channels, List<String> symbols, Integer depth, String key, Long signTimestamp, String signature) {
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .event(EVENT_SUBSCRIBE)
                .channel(channels)
                .symbols(symbols)
                .depth(depth)
                .params(StringUtils.isNotEmpty(signature) ?
                        AuthParams.builder()
                                .key(key)
                                .signTimestamp(signTimestamp)
                                .signature(signature)
                                .build() :
                        null)
                .build();

        try {
            return webSocket.send(objectMapper.writeValueAsString(subscribeRequest));
        } catch (JsonProcessingException e) {
            throw new PoloApiException("Could not send subscribe request", e);
        }
    }

    /**
     * Unsubscribe from websocket channels
     *
     * @param webSocket ok http websocket
     * @param channels channel names
     * @param symbols  symbols names
     * @return success of unsubscription
     */
    public boolean unsubscribe(WebSocket webSocket, List<String> channels, List<String>  symbols) {
        UnsubscribeRequest unsubscribeRequest = UnsubscribeRequest.builder()
                .event(EVENT_UNSUBSCRIBE)
                .channel(channels)
                .symbols(symbols)
                .build();

        try {
            return webSocket.send(objectMapper.writeValueAsString(unsubscribeRequest));
        } catch (JsonProcessingException e) {
            throw new PoloApiException("Could not send unsubscribe request", e);
        }
    }

    /**
     * Close websocket
     * @param webSocket ok http websocket
     */
    public void close(WebSocket webSocket) {
        webSocket.close(1000, null);
    }

}
