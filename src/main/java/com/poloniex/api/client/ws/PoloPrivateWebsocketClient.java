package com.poloniex.api.client.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.poloniex.api.client.model.event.BalanceEvent;
import com.poloniex.api.client.model.event.OrderEvent;
import com.poloniex.api.client.security.SignatureGenerator;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.List;

import static com.poloniex.api.client.common.PoloApiConstants.*;

/**
 * Polo private websocket client for authenticated channels
 */
@Slf4j
public class PoloPrivateWebsocketClient extends PoloBaseWebsocketClient {

    private final String apiKey;
    private final String secret;

    /**
     * Instantiates a new Polo private websocket client
     *
     * @param baseUrl base url for Polo private websocket
     * @param apiKey  user's API key
     * @param secret  user's API key secret
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code PoloPrivateWebsocket privateWebsocket = new PoloPrivateWebsocket("<POLO_PRIVATE_WS_URL>", "<YOUR_API_KEY>", "<YOUR_SECRET>");}
     *  </pre>
     */
    public PoloPrivateWebsocketClient(String baseUrl, String apiKey, String secret) {
        super(new OkHttpClient.Builder().build(), baseUrl);
        this.apiKey = apiKey;
        this.secret = secret;
    }

    /**
     * Instantiates a new Polo private websocket client
     *
     * @param client  http client
     * @param baseUrl base url for Polo private websocket
     * @param apiKey  user's API key
     * @param secret  user's API key secret
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code PoloPrivateWebsocket privateWebsocket = new PoloPrivateWebsocket(okHttpClient, "<POLO_PRIVATE_WS_URL>", "<YOUR_API_KEY>", "<YOUR_SECRET>");}
     *  </pre>
     */
    public PoloPrivateWebsocketClient(OkHttpClient client, String baseUrl, String apiKey, String secret) {
        super(client, baseUrl);
        this.apiKey = apiKey;
        this.secret = secret;
    }

    /**
     * Authentication to user private websocket channels
     *
     * @param webSocket ok http websocket
     */
    public void onAuth(WebSocket webSocket) {
        long timestamp = System.currentTimeMillis();
        String signature = SignatureGenerator.generateSignature(secret, SIGNATURE_PAYLOAD + timestamp);
        subscribe(webSocket, List.of(CHANNEL_AUTH), apiKey, timestamp, signature);
    }

    /**
     * Orders Channel:
     * Real time information about client’s orders. There are three types of events: "place", "trade", and "canceled".
     *
     * @param symbols  symbol names
     * @param callback callback to handle order events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code privateWebsocket.onOrderEvent(List.of("all"), new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onOrderEvent(List<String> symbols, PoloApiCallback<OrderEvent> callback) {
        final PoloWebsocketListener<OrderEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, List.of(CHANNEL_ORDERS), symbols);
        return webSocket;
    }

    /**
     * Balances Channel:
     * Real time information about all of client’s balance(s) updates.
     *
     * @param callback callback to handle balance events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code privateWebsocket.onBalanceEvent(new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onBalanceEvent(PoloApiCallback<BalanceEvent> callback) {
        final PoloWebsocketListener<BalanceEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, List.of(CHANNEL_BALANCES));
        return webSocket;
    }

}
