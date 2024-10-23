package com.poloniex.api.client.future.ws.future;

import com.fasterxml.jackson.core.type.TypeReference;
import com.poloniex.api.client.future.common.CandlesChannels;
import com.poloniex.api.client.future.common.IndexPriceK_lineDataChannels;
import com.poloniex.api.client.future.common.MarkPriceK_lineDataChannels;
import com.poloniex.api.client.future.model.event.future.*;
import com.poloniex.api.client.future.ws.PoloApiCallback;
import com.poloniex.api.client.future.ws.PoloBaseWebsocketClient;
import com.poloniex.api.client.future.ws.PoloWebsocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.List;
import java.util.stream.Collectors;

import static com.poloniex.api.client.future.common.PoloApiConstants.*;

/**
 * Polo public websocket client for public channels
 */
@Slf4j
public class SpotPoloPublicWebsocketClient extends PoloBaseWebsocketClient {


    public SpotPoloPublicWebsocketClient(String baseUrl) {
        super(new OkHttpClient.Builder().build(), baseUrl);
    }


    public SpotPoloPublicWebsocketClient(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);
    }


    public WebSocket onProductInfo_symbolEvent(List<String> symbols, PoloApiCallback<ProductInfo_symbolEvent> callback) {

        final PoloWebsocketListener<ProductInfo_symbolEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Product_Info_Symbol), symbols);
        return webSocket;
    }


    public WebSocket onOrderBookEvent(List<String> symbols, PoloApiCallback<OrderBookEvent> callback, Integer dep) {
        final PoloWebsocketListener<OrderBookEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Order_Book), symbols, dep);
        return webSocket;
    }

    public WebSocket onOrderBookEvent(List<String> symbols, PoloApiCallback<OrderBookEvent> callback) {
        final PoloWebsocketListener<OrderBookEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Order_Book), symbols);
        return webSocket;
    }


    public WebSocket onOrderBookV2Event(List<String> symbols, PoloApiCallback<OrderBookV2Event> callback) {
        final PoloWebsocketListener<OrderBookV2Event> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Order_Book_V2), symbols);
        return webSocket;
    }

    public WebSocket onTickersEvent(List<String> symbols, PoloApiCallback<TickersEvent> callback) {
        final PoloWebsocketListener<TickersEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Tickers), symbols);
        return webSocket;
    }

    public WebSocket onK_lineDataEvent(List<CandlesChannels> channels, List<String> symbols, PoloApiCallback<K_lineDataEvent> callback) {
        final List<String> channelsString = channels.stream().map(c -> c.name().toLowerCase()).collect(Collectors.toList());
        final PoloWebsocketListener<K_lineDataEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, channelsString, symbols);
        return webSocket;
    }

    public WebSocket onMarkPriceK_lineDataEvent(List<MarkPriceK_lineDataChannels> channels, List<String> symbols, PoloApiCallback<MarkPriceK_lineDataEvent> callback) {
        final List<String> channelsString = channels.stream().map(c -> c.name().toLowerCase()).collect(Collectors.toList());
        final PoloWebsocketListener<MarkPriceK_lineDataEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, channelsString, symbols);
        return webSocket;
    }

    public WebSocket onIndexPriceK_lineDataEvent(List<IndexPriceK_lineDataChannels> channels, List<String> symbols, PoloApiCallback<IndexPriceK_lineDataEvent> callback) {
        final List<String> channelsString = channels.stream().map(c -> c.name().toLowerCase()).collect(Collectors.toList());
        final PoloWebsocketListener<IndexPriceK_lineDataEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, channelsString, symbols);
        return webSocket;
    }

    public WebSocket onTradingInfoEvent(List<String> symbols, PoloApiCallback<TradingInfoEvent> callback) {
        final PoloWebsocketListener<TradingInfoEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Trading_Info), symbols);
        return webSocket;
    }


    public WebSocket onIndexPriceEvent(List<String> symbols, PoloApiCallback<IndexPriceEvent> callback) {
        final PoloWebsocketListener<IndexPriceEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Index_Price), symbols);
        return webSocket;
    }


    public WebSocket onMarkPriceEvent(List<String> symbols, PoloApiCallback<MarkPriceEvent> callback) {
        final PoloWebsocketListener<MarkPriceEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Mark_Price), symbols);
        return webSocket;
    }

    public WebSocket onFundingRateEvent(List<String> symbols, PoloApiCallback<FundingRateEvent> callback) {
        final PoloWebsocketListener<FundingRateEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {
        });
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(Channel_Funding_Rate), symbols);
        return webSocket;
    }


}
