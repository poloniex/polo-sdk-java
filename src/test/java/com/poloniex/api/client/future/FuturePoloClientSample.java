package com.poloniex.api.client.future;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import com.poloniex.api.client.future.common.CandlesChannels;
import com.poloniex.api.client.future.common.IndexPriceK_lineDataChannels;
import com.poloniex.api.client.future.common.K_lineDataChannels;
import com.poloniex.api.client.future.common.MarkPriceK_lineDataChannels;
import com.poloniex.api.client.future.model.event.future.AccountChangeEvent;
import com.poloniex.api.client.future.model.event.future.FundingRateEvent;
import com.poloniex.api.client.future.model.event.future.PoloEvent;
import com.poloniex.api.client.future.model.request.future.*;
import com.poloniex.api.client.future.rest.future.SpotPoloRestClient;
import com.poloniex.api.client.future.ws.PoloApiCallback;
import com.poloniex.api.client.future.ws.PoloLoggingCallback;
import com.poloniex.api.client.future.ws.future.SpotPoloPrivateWebsocketClient;
import com.poloniex.api.client.future.ws.future.SpotPoloPublicWebsocketClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.Collections;
import java.util.List;

import static com.poloniex.api.client.future.common.PoloApiConstants.Channel_Funding_Rate;

@Slf4j
public class FuturePoloClientSample {

    private static final String HOST = "";
    private static final String PUBLIC_WS_URL = "wss://ws.poloniex.com/ws/v3/public";
    private static final String PRIVATE_WS_URL = "wss://ws.poloniex.com/ws/v3/private";

    // your api key
    private static final String API_KEY = "";

    // your api secret
    private static final String SECRET = "";


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    public static void main(String[] args) throws JsonProcessingException {

//        testPublicClient();
//            testPrivateClient();
        websocketPublicTest();
        websocketPrivateTest();

    }

    private static void testPublicClient() throws JsonProcessingException {
        SpotPoloRestClient poloniexApiClient = new SpotPoloRestClient(HOST);

        testPublicEndpoints(poloniexApiClient);
    }
    private static void testPrivateClient() throws JsonProcessingException {
        SpotPoloRestClient poloniexApiClient = new SpotPoloRestClient(HOST, API_KEY, SECRET);
        testPrivateEndpoints(poloniexApiClient);
    }

    private static void testPublicEndpoints(SpotPoloRestClient poloniexApiClient) throws JsonProcessingException{
       log.info("getOrderBook: {}", writer.writeValueAsString(poloniexApiClient.getOrderBook("BTC_USDT_PERP","100",5)));
        log.info("getKlineData: {}", writer.writeValueAsString(poloniexApiClient.getKlineData("BTC_USDT_PERP", String.valueOf(K_lineDataChannels.MINUTE_1),null,null,null)));
        log.info("getExecutionInfo: {}", writer.writeValueAsString(poloniexApiClient.getExecutionInfo("BTC_USDT_PERP",null)));
        log.info("getMarketInfo: {}", writer.writeValueAsString(poloniexApiClient.getMarketInfo()));
        log.info("getIndexPrice: {}", writer.writeValueAsString(poloniexApiClient.getIndexPrice("BTC_USDT_PERP")));
        log.info("getIndexPricecomponents: {}", writer.writeValueAsString(poloniexApiClient.getIndexPricecomponents("BTC_USDT_PERP")));
        log.info("getIndexPriceK_lineData: {}", writer.writeValueAsString(poloniexApiClient.getIndexPriceK_lineData("BTC_USDT_PERP", "MINUTE_1",null,null,null)));
        log.info("getPremiumIndexK_lineData: {}", writer.writeValueAsString(poloniexApiClient.getPremiumIndexK_lineData("BTC_USDT_PERP", K_lineDataChannels.MINUTE_1.name(), null,null,null)));
        log.info("getMarkPrice: {}", writer.writeValueAsString(poloniexApiClient.getMarkPrice("BTC_USDT_PERP")));

      log.info("getMarkPriceK_lineData: {}", writer.writeValueAsString(poloniexApiClient.getMarkPriceK_lineData("BTC_USDT_PERP", K_lineDataChannels.MINUTE_1.name(),null,null,null)));

        log.info("getProductInfo: {}", writer.writeValueAsString(poloniexApiClient.getProductInfo("BTC_USDT_PERP")));

        log.info("getCurrentFundingRate: {}", writer.writeValueAsString(poloniexApiClient.getCurrentFundingRate("BTC_USDT_PERP")));
        log.info("currentOpenpositions: {}", writer.writeValueAsString(poloniexApiClient.currentOpenpositions("BTC_USDT_PERP")));
        log.info("queryInsurance: {}", writer.writeValueAsString(poloniexApiClient.queryInsurance()));
        log.info("getHistoricalFundingRates: {}", writer.writeValueAsString(poloniexApiClient.getHistoricalFundingRates("BTC_USDT_PERP",null,null,null)));
        log.info("getFuturesRiskLimit: {}", writer.writeValueAsString(poloniexApiClient.getFuturesRiskLimit("BTC_USDT_PERP")));
    }


    private static void testPrivateEndpoints(SpotPoloRestClient poloniexApiClient) throws JsonProcessingException {
        testPublicEndpoints(poloniexApiClient);
        log.info("getAccountBalance: {}", writer.writeValueAsString(poloniexApiClient.getAccountBalance()));
        log.info("placeOrder: {}", writer.writeValueAsString(poloniexApiClient.placeOrder(PlaceOrderRequest.builder().symbol("BTC_USDT_PERP").side("BUY").type("MARKET").sz("10").build())));
        log.info("placeMultipleOrders: {}", writer.writeValueAsString(poloniexApiClient.placeMultipleOrders(List.of(PlaceMultipleOrdersRequest.builder().symbol("BTC_USDT_PERP").side("BUY").type("MARKET").sz("10").build()))));

        log.info("cancelOrder: {}", writer.writeValueAsString(poloniexApiClient.cancelOrder("BTC_USDT_PERP","331378951194935296",null)));

//        404资源不存在

        log.info("cancelMultipleOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelMultipleOrders(CancelMultipleOrdersRequest.builder().symbol("BTC_USDT_PERP").clOrdIds(List.of("12312321323123","357938478312624128")).ordIds(List.of()).build())));

        CancelMultipleOrdersRequest request = new CancelMultipleOrdersRequest("BTC_USDT_PERP", null, Collections.singletonList("121"));
        log.info("cancelMultipleOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelMultipleOrders(request)));
        log.info("cancelAllOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelAllOrders("BTC_USDT_PERP","BUY")));

        log.info("closeAtMarketPrice: {}", writer.writeValueAsString(poloniexApiClient.closeAtMarketPrice(CloseAtMarketPriceRequest.builder().symbol("BTC_USDT_PERP").build())));
        log.info("CloseAllAtMarketPrice: {}", writer.writeValueAsString(poloniexApiClient.CloseAllAtMarketPrice()));
        log.info("getCurrentOrders: {}", writer.writeValueAsString(poloniexApiClient.getCurrentOrders("BUY","BTC_USDT_PERP",null,null,null,null,null)));
        log.info("getExecutionDetails: {}", writer.writeValueAsString(poloniexApiClient.getExecutionDetails("BUY",null,null,null,null,null,null,null,null)));
        log.info("getOrderHistory: {}", writer.writeValueAsString(poloniexApiClient.getOrderHistory("BUY",null,null,null,null,null,null,null,null,null,null)));
        log.info("getCurrentPosition: {}", writer.writeValueAsString(poloniexApiClient.getCurrentPosition("BTC_USDT_PERP")));
        log.info("getPositionHistory: {}", writer.writeValueAsString(poloniexApiClient.getPositionHistory("BTC_USDT_PERP",null,null,null,null,null,null)));
        log.info("adjustMargin: {}", writer.writeValueAsString(poloniexApiClient.adjustMargin(AdjustMarginRequest.builder().symbol("DOT_USDT_PERP").type("ADD").amt("50").build())));
        log.info("switchCross: {}", writer.writeValueAsString(poloniexApiClient.switchCross(SwitchCrossRequest.builder().symbol("DOT_USDT_PERP").mgnMode("CROSS").build())));
        log.info("getMarginMode: {}", writer.writeValueAsString(poloniexApiClient.getMarginMode("DOT_USDT_PERP")));
        log.info("getLeverage: {}", writer.writeValueAsString(poloniexApiClient.getLeverage("BTC_USDT_PERP")));
        log.info("setLeverage: {}", writer.writeValueAsString(poloniexApiClient.setLeverage(SetLeverageRequest.builder().symbol("BTC_USDT_PERP").lever("7").build())));
    }


   private static void websocketPublicTest() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        SpotPoloPublicWebsocketClient publicWebsocket = new SpotPoloPublicWebsocketClient(httpClient, PUBLIC_WS_URL);

        publicWebsocket.onProductInfo_symbolEvent( List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onOrderBookEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>(),5);
       publicWebsocket.onOrderBookEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onOrderBookV2Event(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onK_lineDataEvent(List.of(CandlesChannels.CANDLES_MINUTE_1),List.of("BTC_USDT_PERP"),new PoloLoggingCallback<>());
        publicWebsocket.onMarkPriceK_lineDataEvent(List.of(MarkPriceK_lineDataChannels.Mark_Price_Candles_DAY_1),List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onIndexPriceK_lineDataEvent(List.of(IndexPriceK_lineDataChannels.Index_Candles_DAY_1),List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onTradingInfoEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onTickersEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        publicWebsocket.onIndexPriceEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
       publicWebsocket.onMarkPriceEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
       publicWebsocket.onFundingRateEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());

        // example using inline callback
       PoloApiCallback<FundingRateEvent> callback = new PoloApiCallback<>() {
            @Override
            public void onResponse(PoloEvent<FundingRateEvent> response) {
                log.info("121"+String.valueOf(response.getSymbols()));
            }

            @Override
            public void onFailure(Throwable t) {
                log.warn("error",t);
            }
        };

        publicWebsocket.onFundingRateEvent(List.of("BTC_USDT_PERP"), callback);

        // example using unsubscribe and close
       WebSocket forTickerEvent = publicWebsocket.onFundingRateEvent(List.of("BTC_USDT_PERP"), callback);
        try {
            Thread.sleep(5000L);
            // unsubscribe from BTC_USDT but keep ticker subscription for ADA_USDT
            publicWebsocket.unsubscribe(forTickerEvent, List.of(Channel_Funding_Rate), List.of("BTC_USDT"));
            Thread.sleep(5000L);
            log.info("关闭");
            // close ticker event websocket
            publicWebsocket.close(forTickerEvent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void websocketPrivateTest() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();
        SpotPoloPrivateWebsocketClient privateWebsocket = new SpotPoloPrivateWebsocketClient(httpClient, PRIVATE_WS_URL, API_KEY, SECRET);
        // example of resubscribing after a failure
        final ObjectMapper objectMapper = new ObjectMapper();
        privateWebsocket.onAccountChangeEvent(List.of("BTC_USDT_PERP"), new PoloApiCallback<AccountChangeEvent>() {
            @Override
            public void onResponse(PoloEvent<AccountChangeEvent> response) throws JsonProcessingException {
                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    Thread.sleep(5000);
                    privateWebsocket.onAccountChangeEvent(List.of("BTC_USDT_PERP"), this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        privateWebsocket.onPositionsEvent(List.of("BTC_USDT_PERP"),new PoloLoggingCallback<>());
        privateWebsocket.onOrdersEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
        privateWebsocket.onTradeEvent(List.of("BTC_USDT_PERP"),new PoloLoggingCallback<>());
        privateWebsocket.onAccountChangeEvent(List.of("BTC_USDT_PERP"), new PoloLoggingCallback<>());
    }

}