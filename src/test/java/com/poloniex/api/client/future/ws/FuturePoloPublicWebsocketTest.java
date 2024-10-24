package com.poloniex.api.client.future.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.poloniex.api.client.future.common.CandlesChannels;
import com.poloniex.api.client.future.common.IndexPriceK_lineDataChannels;
import com.poloniex.api.client.future.common.MarkPriceK_lineDataChannels;
import com.poloniex.api.client.future.common.PoloApiException;
import com.poloniex.api.client.future.model.event.future.*;
import com.poloniex.api.client.future.model.request.future.SubscribeRequest;
import com.poloniex.api.client.future.ws.future.SpotPoloPublicWebsocketClient;
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

import static com.poloniex.api.client.future.common.PoloApiConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuturePoloPublicWebsocketTest {
    @Mock
    private WebSocket webSocket;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private OkHttpClient client;

    SpotPoloPublicWebsocketClient poloPublicWebsocket;

    @BeforeEach
    void init() throws NoSuchFieldException {
        poloPublicWebsocket = new SpotPoloPublicWebsocketClient(new OkHttpClient.Builder().build(),
                "wss://ws.poloniex.com/ws/v3/public");

        poloPublicWebsocket = new SpotPoloPublicWebsocketClient("wss://ws.poloniex.com/ws/v3/public");


        FieldSetter.setField(poloPublicWebsocket,
                poloPublicWebsocket.getClass().getSuperclass().getDeclaredField("objectMapper"), objectMapper);
        FieldSetter.setField(poloPublicWebsocket,
                poloPublicWebsocket.getClass().getSuperclass().getDeclaredField("client"), client);
    }

    @Test
    void testonK_lineDataEvent() throws JsonProcessingException {
        //arguments for the method
        List<CandlesChannels> channels = List.of(CandlesChannels.CANDLES_MINUTE_1);
        List<String> symbols = List.of("BTC_USDT");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<K_lineDataEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onK_lineDataEvent(channels, symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onK_lineDataEvent(channels, symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonMarkPriceK_lineDataEvent() throws JsonProcessingException {
        //arguments for the method
        List<MarkPriceK_lineDataChannels> channels = List.of(MarkPriceK_lineDataChannels.Mark_Price_Candles_DAY_1);
        List<String> symbols = List.of("BTC_USDT");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<MarkPriceK_lineDataEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onMarkPriceK_lineDataEvent(channels, symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onMarkPriceK_lineDataEvent(channels, symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
    @Test
    void testonIndexPriceK_lineDataEvent() throws JsonProcessingException {
        //arguments for the method
        List<IndexPriceK_lineDataChannels> channels = List.of(IndexPriceK_lineDataChannels.Index_Candles_DAY_1);
        List<String> symbols = List.of("BTC_USDT");

        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<IndexPriceK_lineDataEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onIndexPriceK_lineDataEvent(channels, symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onIndexPriceK_lineDataEvent(channels, symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonProductInfo_symbolEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Product_Info_Symbol);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<ProductInfo_symbolEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onProductInfo_symbolEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onProductInfo_symbolEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonOrderBookEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Order_Book);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<OrderBookEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onOrderBookEvent(symbols, callback,5);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onOrderBookEvent(symbols, callback,5);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonOrderBookV2Event() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Order_Book_V2);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<OrderBookV2Event> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onOrderBookV2Event(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onOrderBookV2Event(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }
    @Test
    void testonTradingInfoEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Trading_Info);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<TradingInfoEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onTradingInfoEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onTradingInfoEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonExecutionInfoEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Tickers);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<TickersEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onTickersEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onTickersEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonIndexPriceEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Index_Price);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<IndexPriceEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onIndexPriceEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onIndexPriceEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonMarkPriceEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Mark_Price);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<MarkPriceEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onMarkPriceEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onMarkPriceEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

    @Test
    void testonFundingRateEvent() throws JsonProcessingException {
        //arguments for the method
        List<String> channels = List.of(Channel_Funding_Rate);
        List<String> symbols = List.of("BTC_USDT");


        when(client.newWebSocket(any(Request.class), any(PoloWebsocketListener.class))).thenReturn(webSocket);
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenReturn("");
        when(webSocket.send(anyString())).thenReturn(true);
        PoloApiCallback<FundingRateEvent> callback = new PoloLoggingCallback<>();
        poloPublicWebsocket.onFundingRateEvent(symbols, callback);
        verify(webSocket, times(1)).send(anyString());

        //exception case
        when(objectMapper.writeValueAsString(any(SubscribeRequest.class))).thenThrow(JsonProcessingException.class);
        try {
            poloPublicWebsocket.onFundingRateEvent(symbols, callback);
        } catch (PoloApiException e) {
            assertEquals("Could not send subscribe request", e.getMessage());
        }
    }

}
