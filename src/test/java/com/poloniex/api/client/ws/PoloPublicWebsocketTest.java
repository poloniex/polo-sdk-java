package com.poloniex.api.client.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.common.CandlestickChannels;
import com.poloniex.api.client.common.PoloApiException;
import com.poloniex.api.client.model.event.*;
import com.poloniex.api.client.model.request.SubscribeRequest;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.poloniex.api.client.common.PoloApiConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoloPublicWebsocketTest {
    @Mock
    private WebSocket webSocket;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OkHttpClient client;

    PoloPublicWebsocketClient poloPublicWebsocket;

    @BeforeEach
    void init() throws NoSuchFieldException {
        poloPublicWebsocket = new PoloPublicWebsocketClient("wss://sandbox-ws.poloniex.com/ws/public");
        poloPublicWebsocket = new PoloPublicWebsocketClient(new OkHttpClient.Builder().build(),
            "wss://sandbox-ws.poloniex.com/ws/public");
        FieldSetter.setField(poloPublicWebsocket,
                poloPublicWebsocket.getClass().getSuperclass().getDeclaredField("objectMapper"), objectMapper);
        FieldSetter.setField(poloPublicWebsocket,
                poloPublicWebsocket.getClass().getSuperclass().getDeclaredField("client"), client);
    }

    @Test
    void testOnCandlestickEvent() throws JsonProcessingException {
        //arguments for the method
        java.util.List<CandlestickChannels> channels = List.of(CandlestickChannels.CANDLES_MINUTE_1);
        List<String> symbols = List.of("BTC_USDT");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<CandlestickEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onCandlestickEvent(channels, symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onCandlestickEvent(channels, symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnTradeEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(CHANNEL_TRADES);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<TradeEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onTradeEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onTradeEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnTickerEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(CHANNEL_TICKER);
        List<String> symbols = List.of("BTC_USDT");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<TickerEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onTickerEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onTickerEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnTickerAllEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(CHANNEL_TICKER);
        List<String> symbols = List.of(SYMBOLS_ALL);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<TickerEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onTickerAllEvent(callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onTickerAllEvent(callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnBookEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(CHANNEL_BOOK);
        List<String> symbols = List.of(SYMBOLS_ALL);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<BookEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onBookEvent(symbols, null, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onBookEvent(symbols, null, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnBookLv2Event() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(CHANNEL_BOOK_LV_2);
        List<String> symbols = List.of("BTC_USDT","ETH_USDT");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<BookLv2Event> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onBookLv2Event(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onBookLv2Event(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
}