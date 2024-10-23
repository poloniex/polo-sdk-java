package com.poloniex.api.client.spot.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.spot.common.PoloApiException;
import com.poloniex.api.client.spot.model.event.spot.BalanceEvent;
import com.poloniex.api.client.spot.model.event.spot.CancelOrderEvent;
import com.poloniex.api.client.spot.model.event.spot.CreateOrderEvent;
import com.poloniex.api.client.spot.model.event.spot.OrderEvent;
import com.poloniex.api.client.spot.model.request.spot.AuthParams;
import com.poloniex.api.client.spot.model.request.spot.CancelOrderRequest;
import com.poloniex.api.client.spot.model.request.spot.CreateOrderRequest;
import com.poloniex.api.client.spot.model.request.spot.SubscribeRequest;
import com.poloniex.api.client.spot.ws.PoloApiCallback;
import com.poloniex.api.client.spot.ws.PoloLoggingCallback;
import com.poloniex.api.client.spot.ws.PoloWebsocketListener;
import com.poloniex.api.client.spot.ws.spot.SpotPoloPrivateWebsocketClient;
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

import static com.poloniex.api.client.spot.common.PoloApiConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpotPoloPrivateWebsocketTest {
    @Mock
    private WebSocket webSocket;

    @Mock
    private OkHttpClient client;

    @Mock
    private ObjectMapper objectMapper;

    SpotPoloPrivateWebsocketClient poloPrivateWebsocket;


    @BeforeEach
    void init() throws NoSuchFieldException {

        poloPrivateWebsocket = new SpotPoloPrivateWebsocketClient(
                new OkHttpClient.Builder().build(),
                "wss://ws.poloniex.com/ws/private/",
                "",
                "");

        poloPrivateWebsocket = new SpotPoloPrivateWebsocketClient(
                "wss://ws.poloniex.com/ws/private/",
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
    void testOnOrderEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> symbols = List.of("all");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<OrderEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onOrderEvent(symbols, callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onOrderEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnBalanceEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(CHANNEL_BALANCES);

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<BalanceEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onBalanceEvent(callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onBalanceEvent(callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnCreateOrderEvent() throws JsonProcessingException {
        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<CreateOrderEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onCreateOrderEvent("1234567", CreateOrderRequest.builder().symbol("BTC_USDT").type("LIMIT").quantity("100").side("BUY").price("40000.50000").timeInForce("IOC").clientOrderId("1234Abc").build(), callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onCreateOrderEvent("1234567", CreateOrderRequest.builder().symbol("BTC_USDT").type("LIMIT").quantity("100").side("BUY").price("40000.50000").timeInForce("IOC").clientOrderId("1234Abc").build(), callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnCancelOrderEvent() throws JsonProcessingException {
        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<CancelOrderEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onCancelOrderEvent("1234567", CancelOrderRequest.builder().orderIds(List.of("170903943331844096", "170904091512410112")).clientOrderIds(List.of("45662xyz", "xasd2343")).build(), callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onCancelOrderEvent("1234567", CancelOrderRequest.builder().orderIds(List.of("170903943331844096", "170904091512410112")).clientOrderIds(List.of("45662xyz", "xasd2343")).build(), callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testOnCancelAllOrderEvent() throws JsonProcessingException {
        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<CancelOrderEvent> callback = new PoloLoggingCallback<>();
        poloPrivateWebsocket.onCancelAllOrderEvent("1234567", callback);
        verify(webSocket, times(2)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPrivateWebsocket.onCancelAllOrderEvent("1234567", callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
}