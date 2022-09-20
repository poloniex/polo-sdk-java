package com.poloniex.api.client.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poloniex.api.client.common.PoloApiException;
import com.poloniex.api.client.model.event.BalanceEvent;
import com.poloniex.api.client.model.event.OrderEvent;
import com.poloniex.api.client.model.request.AuthParams;
import com.poloniex.api.client.model.request.SubscribeRequest;
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

import static com.poloniex.api.client.common.PoloApiConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PoloPrivateWebsocketTest {
    @Mock
    private WebSocket webSocket;

    @Mock
    private OkHttpClient client;

    @Mock
    private ObjectMapper objectMapper;

    PoloPrivateWebsocketClient poloPrivateWebsocket;

    @BeforeEach
    void init() throws NoSuchFieldException {

        poloPrivateWebsocket = new PoloPrivateWebsocketClient(new OkHttpClient.Builder().build(),
                "wss://sandbox-ws.poloniex.com/ws/private", "<YOUR_API_KEY>", "<YOUR_SECRET>");
        poloPrivateWebsocket = new PoloPrivateWebsocketClient("wss://sandbox-ws.poloniex.com/ws/private",
                "<YOUR_API_KEY>", "<YOUR_SECRET>");
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
}