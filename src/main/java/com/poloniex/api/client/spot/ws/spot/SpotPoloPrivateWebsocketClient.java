package com.poloniex.api.client.spot.ws.spot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.poloniex.api.client.spot.model.event.spot.BalanceEvent;
import com.poloniex.api.client.spot.model.event.spot.CancelOrderEvent;
import com.poloniex.api.client.spot.model.event.spot.CreateOrderEvent;
import com.poloniex.api.client.spot.model.event.spot.OrderEvent;
import com.poloniex.api.client.spot.model.request.spot.CancelOrderRequest;
import com.poloniex.api.client.spot.model.request.spot.CreateOrderRequest;
import com.poloniex.api.client.spot.security.SignatureGenerator;
import com.poloniex.api.client.spot.ws.PoloApiCallback;
import com.poloniex.api.client.spot.ws.PoloBaseWebsocketClient;
import com.poloniex.api.client.spot.ws.PoloWebsocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.List;

import static com.poloniex.api.client.spot.common.PoloApiConstants.*;

/**
 * Polo private websocket client for authenticated channels
 */
@Slf4j
public class SpotPoloPrivateWebsocketClient extends PoloBaseWebsocketClient {

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
    public SpotPoloPrivateWebsocketClient(String baseUrl, String apiKey, String secret) {
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
    public SpotPoloPrivateWebsocketClient(OkHttpClient client, String baseUrl, String apiKey, String secret) {
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

    /**
     * Create Order Channel:
     * Create an order for an account.
     *
     * @param callback callback to handle Create Order events
     * @param id id
     * @param params CreateOrderRequest params
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code privateWebsocket.onCreateOrderEvent("123456789", new CreateOrderRequest(), new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onCreateOrderEvent(String id, CreateOrderRequest params, PoloApiCallback<CreateOrderEvent> callback) {
        final PoloWebsocketListener<CreateOrderEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, id, EVENT_CREATE_ORDER, params);
        return webSocket;
    }

    /**
     * Cancel Multiple Orders Channel:
     * Batch cancel one or many active orders in an account by IDs.
     *
     * @param callback callback to handle Cancel Multiple Orders events
     * @param id id
     * @param params CancelOrderRequest params
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code privateWebsocket.onCancelOrderEvent("123456789", new CancelOrderRequest(), new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onCancelOrderEvent(String id, CancelOrderRequest params, PoloApiCallback<CancelOrderEvent> callback) {
        final PoloWebsocketListener<CancelOrderEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, id, EVENT_CANCEL_ORDERS, params);
        return webSocket;
    }

    /**
     * Cancel All Orders Channel:
     * Batch cancel one or many active orders in an account by IDs.
     *
     * @param callback callback to handle Cancel All Orders events
     * @param id id
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code privateWebsocket.onCancelAllOrderEvent("123456789", new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onCancelAllOrderEvent(String id, PoloApiCallback<CancelOrderEvent> callback) {
        final PoloWebsocketListener<CancelOrderEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, id, EVENT_CANCEL_ALL_ORDERS, null);
        return webSocket;
    }

}
