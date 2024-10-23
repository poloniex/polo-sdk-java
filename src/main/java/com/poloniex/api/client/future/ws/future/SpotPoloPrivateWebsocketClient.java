package com.poloniex.api.client.future.ws.future;

import com.fasterxml.jackson.core.type.TypeReference;
import com.poloniex.api.client.future.model.event.future.AccountChangeEvent;
import com.poloniex.api.client.future.model.event.future.OrdersEvent;
import com.poloniex.api.client.future.model.event.future.PositionsEvent;
import com.poloniex.api.client.future.model.event.future.TradeEvent;
import com.poloniex.api.client.future.security.SignatureGenerator;
import com.poloniex.api.client.future.ws.PoloApiCallback;
import com.poloniex.api.client.future.ws.PoloBaseWebsocketClient;
import com.poloniex.api.client.future.ws.PoloWebsocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.List;

import static com.poloniex.api.client.future.common.PoloApiConstants.*;

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
     *                <dt><b>Example:</b></dt>
     *                <pre>
     *                 {@code PoloPrivateWebsocket privateWebsocket = new PoloPrivateWebsocket("<POLO_PRIVATE_WS_URL>", "<YOUR_API_KEY>", "<YOUR_SECRET>");}
     *                 </pre>
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
     *                <dt><b>Example:</b></dt>
     *                <pre>
     *                 {@code PoloPrivateWebsocket privateWebsocket = new PoloPrivateWebsocket(okHttpClient, "<POLO_PRIVATE_WS_URL>", "<YOUR_API_KEY>", "<YOUR_SECRET>");}
     *                 </pre>
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


    public WebSocket onPositionsEvent(List<String> symbols, PoloApiCallback<PositionsEvent> callback) {
        final PoloWebsocketListener<PositionsEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, List.of(Channel_Positions), symbols);
        return webSocket;
    }


    public WebSocket onOrdersEvent(List<String> symbols, PoloApiCallback<OrdersEvent> callback) {
        final PoloWebsocketListener<OrdersEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, List.of(Channel_Orders), symbols);
        return webSocket;
    }

    public WebSocket onTradeEvent(List<String> symbols, PoloApiCallback<TradeEvent> callback) {
        final PoloWebsocketListener<TradeEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, List.of(Channel_Trade), symbols);
        return webSocket;
    }

    public WebSocket onAccountChangeEvent(List<String> symbols, PoloApiCallback<AccountChangeEvent> callback) {
        final PoloWebsocketListener<AccountChangeEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        onAuth(webSocket);
        subscribe(webSocket, List.of(Channel_Account_Change), symbols);
        return webSocket;
    }


}
