package com.poloniex.api.client.future.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.poloniex.api.client.future.common.PoloApiException;
import com.poloniex.api.client.future.model.event.future.AccountChangeEvent;
import com.poloniex.api.client.future.model.event.future.OrdersEvent;
import com.poloniex.api.client.future.model.event.future.PositionsEvent;
import com.poloniex.api.client.future.model.event.future.TradeEvent;
import com.poloniex.api.client.future.model.request.future.AuthParams;
import com.poloniex.api.client.future.model.request.future.SubscribeRequest;
import com.poloniex.api.client.future.ws.future.SpotPoloPrivateWebsocketClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.poloniex.api.client.future.common.PoloApiConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuturePoloPrivateWebsocketTest {
    @Mock
    private WebSocket webSocket;

    @Mock
    private OkHttpClient client;

    @Mock
    private ObjectMapper objectMapper;

    SpotPoloPrivateWebsocketClient poloPrivateWebsocket;

    @BeforeEach
    void init() throws NoSuchFieldException {

        poloPrivateWebsocket = new SpotPoloPrivateWebsocketClient(new OkHttpClient.Builder().build(),
                "wss://ws.poloniex.com/ws/v3/private/",
                "",
                "");

        poloPrivateWebsocket = new SpotPoloPrivateWebsocketClient(
                "wss://ws.poloniex.com/ws/v3/private/",
                "",
                "");

        FieldSetter.setField(poloPrivateWebsocket,
                poloPrivateWebsocket.getClass().getSuperclass().getDeclaredField("objectMapper"), objectMapper);
        FieldSetter.setField(poloPrivateWebsocket,
                poloPrivateWebsocket.getClass().getSuperclass().getDeclaredField("client"), client);
    }

    @Test
    void testOnAuth() throws JsonProcessingException {

        String signature = null;
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .event(EVENT_SUBSCRIBE)
                .channel(List.of(CHANNEL_AUTH))
                .symbols(null)
                .depth(null)
                .params(StringUtils.isNotEmpty(null) ?
                        AuthParams.builder()
                                .key(null)
                                .signTimestamp(null)
                                .signature(signature)
                                .build() :
                        null)
                .build();
        when(webSocket.send(objectMapper.writeValueAsString(subscribeRequest))).thenReturn(true);
        poloPrivateWebsocket.onAuth(webSocket);
        verify(webSocket, times(1)).send(objectMapper.writeValueAsString(subscribeRequest));
    }

    @Test
    void testonPositionsEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> symbols = List.of("BTC_USDT_PERP");
        List<String> channels = List.of(Channel_Positions);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<PositionsEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onPositionsEvent(symbols, callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onPositionsEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
    @Test
    void testonOrdersEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> symbols = List.of("BTC_USDT_PERP");
        List<String> channels = List.of(Channel_Orders);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<OrdersEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onOrdersEvent(symbols, callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onOrdersEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
    @Test
    void testonTradeEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> symbols = List.of("BTC_USDT_PERP");
        List<String> channels = List.of(Channel_Trade);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<TradeEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onTradeEvent(symbols, callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onTradeEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
    @Test
    void testonAccountChangeEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> symbols = List.of("BTC_USDT_PERP");
        List<String> channels = List.of(Channel_Orders);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<AccountChangeEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onAccountChangeEvent(symbols, callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onAccountChangeEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }



}
