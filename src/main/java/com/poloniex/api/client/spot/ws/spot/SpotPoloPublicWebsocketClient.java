package com.poloniex.api.client.spot.ws.spot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.poloniex.api.client.spot.common.CandlestickChannels;
import com.poloniex.api.client.spot.model.event.spot.*;
import com.poloniex.api.client.spot.ws.PoloApiCallback;
import com.poloniex.api.client.spot.ws.PoloBaseWebsocketClient;
import com.poloniex.api.client.spot.ws.PoloWebsocketListener;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.List;
import java.util.stream.Collectors;

import static com.poloniex.api.client.spot.common.PoloApiConstants.*;

/**
 * Polo public websocket client for public channels
 */
@Slf4j
public class SpotPoloPublicWebsocketClient extends PoloBaseWebsocketClient {

    /**
     * Instantiates a new Polo public websocket client
     *
     * @param baseUrl base url for Polo public websocket
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code PoloPublicWebsocket publicWebsocket = new PoloPublicWebsocket("<POLO_PUBLIC_WS_URL>");}
     *  </pre>
     */
    public SpotPoloPublicWebsocketClient(String baseUrl) {
        super(new OkHttpClient.Builder().build(), baseUrl);
    }

    /**
     * Instantiates a new Polo public websocket client
     *
     * @param client  http client
     * @param baseUrl base url for Polo public websocket
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code PoloPublicWebsocket publicWebsocket = new PoloPublicWebsocket(new OkHttpClient.Builder().build(), "<POLO_PUBLIC_WS_URL>");}
     *  </pre>
     */
    public SpotPoloPublicWebsocketClient(OkHttpClient client, String baseUrl) {
        super(client, baseUrl);
    }

    /**
     * Candlesticks Channel:
     * Continuous feed of candlestick data with default/provided depth.
     *
     * @param channels channel interval
     * @param symbols  symbol names
     * @param callback callback to handle candlestick events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onCandlestickEvent(List.of(CandlestickChannels.CANDLES_MINUTE_1), List.of("BTC_USDT"), new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onCandlestickEvent(List<CandlestickChannels> channels, List<String> symbols, PoloApiCallback<CandlestickEvent> callback) {
        final List<String> channelsString = channels.stream().map(c -> c.name().toLowerCase()).collect(Collectors.toList());
        final PoloWebsocketListener<CandlestickEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, channelsString, symbols);
        return webSocket;
    }

    /**
     * Symbols Channel:
     * Information for symbols such as state and trade limits info. These are the same fields as /markets.
     * Supports subscribing to a list of symbols or to all symbols.
     *
     * @param symbols  symbol name
     * @param callback callback to handle symbols events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onSymbolEvent(List.of("ETH_USDT", new PoloLoggingCallback<>()));}
     *  </pre>
     */
    public WebSocket onSymbolEvent(List<String> symbols, PoloApiCallback<SymbolEvent> callback) {
//        final PoloWebsocketListener<SymbolEvent> listener = new PoloWebsocketListener(executor, objectMapper, callback, new TypeReference<SymbolEvent>() {});
        final PoloWebsocketListener<SymbolEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
//        final PoloWebsocketListener<ArrayList<SymbolEvent>> listener   = new PoloWebsocketListener(executor, objectMapper, callback, new TypeReference<ArrayList<SymbolEvent>>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_SYMBOLS ), symbols);
        return webSocket;
    }

    /**
     * Currencies Channel:
     * Information for currencies. These are the same fields as /currencies including child chains.
     * Supports subscribing to a list of currencies or to all currencies.
     *
     * @param currencies  currencies name
     * @param callback callback to handle currencies events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onCurrenciesEvent(List.of("BTC", new PoloLoggingCallback<>()));}
     *  </pre>
     */
    public WebSocket onCurrenciesEvent(List<String> currencies, PoloApiCallback<CurrenciesEvent> callback) {

        final PoloWebsocketListener<CurrenciesEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
//        final PoloWebsocketListener<List<CurrenciesEvent>> listener   = new PoloWebsocketListener(executor, objectMapper, callback, new TypeReference<List<CurrenciesEvent>>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_CURRENCIES), currencies);
        return webSocket;
    }

    /**
     * Exchange Channel:
     * Provides status of MM (maintenance mode) or POM (post only mode) for the exchange.
     * @param callback callback to handle currencies events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onCurrenciesEvent(List.of("BTC", new PoloLoggingCallback<>()));}
     *  </pre>
     */
    public WebSocket onExchangeEvent(PoloApiCallback<ExchangeEvent> callback) {
        final PoloWebsocketListener<ExchangeEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_EXCHANGE));
        return webSocket;
    }

    /**
     * Trades Channel:
     * Continuous feed of recent trades with default/provided depth.
     *
     * @param symbols  symbol name
     * @param callback callback to handle trades events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onTradeEvent(List.of("ETH_USDT", new PoloLoggingCallback<>()));}
     *  </pre>
     */
    public WebSocket onTradeEvent(List<String> symbols, PoloApiCallback<TradeEvent> callback) {
        final PoloWebsocketListener<TradeEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_TRADES), symbols);
        return webSocket;
    }

    /**
     * Ticker Channel:
     * Continuous feed (updated every second) of current day ticker data
     *
     * @param symbols  list of symbols
     * @param callback callback to handle ticker events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onTickerEvent(List.of("BTC_USDT"), new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onTickerEvent(List<String> symbols, PoloApiCallback<TickerEvent> callback) {
        final PoloWebsocketListener<TickerEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_TICKER), symbols);
        return webSocket;
    }

    /**
     * Ticker Channel:
     * Continuous feed (updated every second) of current day ticker data for all symbols
     *
     * @param callback callback to handle ticker events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onTickerAllEvent(new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onTickerAllEvent(PoloApiCallback<TickerEvent> callback) {
        final PoloWebsocketListener<TickerEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_TICKER), List.of(SYMBOLS_ALL));
        return webSocket;
    }

    /**
     * Book Channel:
     * Continuous feed of order book data containing asks and bids with default/provided depth.
     *
     * @param symbols  symbol name
     * @param depth Default depth is 5. Valid depths are 5, 10, or 20
     * @param callback callback to handle book events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onBookEvent(List.of("ETH_USDT"), 10, new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onBookEvent(List<String> symbols, Integer depth, PoloApiCallback<BookEvent> callback) {
        final PoloWebsocketListener<BookEvent> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_BOOK), symbols, depth);
        return webSocket;
    }

    /**
     * Book Level 2 Channel:
     * Receive a snapshot of the full 20 level order book. Then, continuously in realtime receive an updated order book
     * when the first 20 levels change.
     *
     * @param symbols  symbol name
     * @param callback callback to handle book level 2 events
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code publicWebsocket.onBookLv2Event(List.of("BTC_USDT","ETH_USDT"), new PoloLoggingCallback<>());}
     *  </pre>
     */
    public WebSocket onBookLv2Event(List<String> symbols, PoloApiCallback<BookLv2Event> callback) {
        final PoloWebsocketListener<BookLv2Event> listener = new PoloWebsocketListener<>(executor, objectMapper, callback, new TypeReference<>() {});
        final WebSocket webSocket = createNewWebSocket(listener);
        subscribe(webSocket, List.of(CHANNEL_BOOK_LV_2), symbols);
        return webSocket;
    }

}
