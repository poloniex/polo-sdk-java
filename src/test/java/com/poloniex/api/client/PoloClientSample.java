package com.poloniex.api.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.poloniex.api.client.model.Account;
import com.poloniex.api.client.model.Order;
import com.poloniex.api.client.model.SmartOrder;
import com.poloniex.api.client.model.event.OrderEvent;
import com.poloniex.api.client.model.event.PoloEvent;
import com.poloniex.api.client.model.event.TickerEvent;
import com.poloniex.api.client.model.request.AccountsTransferRequest;
import com.poloniex.api.client.common.CandlestickChannels;
import com.poloniex.api.client.model.request.NewCurrencyAddressRequest;
import com.poloniex.api.client.model.request.WithdrawCurrencyRequest;
import com.poloniex.api.client.rest.PoloRestClient;
import com.poloniex.api.client.ws.PoloApiCallback;
import com.poloniex.api.client.ws.PoloLoggingCallback;
import com.poloniex.api.client.ws.PoloPrivateWebsocketClient;
import com.poloniex.api.client.ws.PoloPublicWebsocketClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

import java.util.List;

import static com.poloniex.api.client.common.PoloApiConstants.CHANNEL_TICKER;

@Slf4j
public class PoloClientSample {

    private static final String HOST = "<POLO_HOST_URL>";
    private static final String PUBLIC_WS_URL = "<POLO_PUBLIC_WS_URL>";
    private static final String PRIVATE_WS_URL = "<POLO_PRIVATE_WS_URL>";

    private static final String API_KEY = "<YOUR_API_KEY>";
    private static final String SECRET = "<YOUR_SECRET>";

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    public static void main(String[] args) throws JsonProcessingException {

        testPublicClient();
        testPrivateClient();
        websocketPublicTest();
        websocketPrivateTest();

    }

    private static void testPublicClient() throws JsonProcessingException {
        PoloRestClient poloniexApiClient = new PoloRestClient(HOST);
        testPublicEndpoints(poloniexApiClient);
    }

    private static void testPrivateClient() throws JsonProcessingException {
        PoloRestClient poloniexApiClient = new PoloRestClient(HOST, API_KEY, SECRET);
        testAllEndpoints(poloniexApiClient);
    }

    private static void testPublicEndpoints(PoloRestClient poloniexApiClient) throws JsonProcessingException {
        log.info("getTimestamp: {}", writer.writeValueAsString(poloniexApiClient.getTimestamp()));
        log.info("getPrice: {}", writer.writeValueAsString(poloniexApiClient.getPrice("BTC_USDT")));
        log.info("getPrices: {}", writer.writeValueAsString(poloniexApiClient.getPrices()));
        log.info("getOrderBook: {}", writer.writeValueAsString(poloniexApiClient.getOrderBook("BTC_USDT", "10", 10)));
        log.info("getCandles: {}", writer.writeValueAsString(poloniexApiClient.getCandles("ETH_USDT", "MINUTE_5", 5, 0L, System.currentTimeMillis())));
        log.info("getMarketTrades: {}", writer.writeValueAsString(poloniexApiClient.getMarketTrades("BTC_USDT", 2)));
        log.info("getTicker24h: {}", writer.writeValueAsString(poloniexApiClient.getTicker24h("ETH_USDT")));
        log.info("getTicker24hAll: {}", writer.writeValueAsString(poloniexApiClient.getTicker24hAll()));
        log.info("getMarkets: {}", writer.writeValueAsString(poloniexApiClient.getMarkets()));
        log.info("getMarket: {}", writer.writeValueAsString(poloniexApiClient.getMarket("BTC_USDT")));
        log.info("getCurrencies: {}", writer.writeValueAsString(poloniexApiClient.getCurrencies()));
        log.info("getCurrencies: {}", writer.writeValueAsString(poloniexApiClient.getCurrencies(null)));
        log.info("getCurrencies: {}", writer.writeValueAsString(poloniexApiClient.getCurrencies(true)));
        log.info("getCurrency: {}", writer.writeValueAsString(poloniexApiClient.getCurrency("BTC", null)));
    }

    private static void testAllEndpoints(PoloRestClient poloniexApiClient) throws JsonProcessingException {
        // public
        testPublicEndpoints(poloniexApiClient);

        //private
        // accounts
        List<Account> accounts = poloniexApiClient.getAccounts();
        log.info("getAccounts: {}", writer.writeValueAsString(accounts));
        log.info("getAccountBalancesByType: {}", writer.writeValueAsString(poloniexApiClient.getAccountBalancesByType("SPOT")));
        log.info("getAccountBalancesById: {}", writer.writeValueAsString(poloniexApiClient.getAccountBalancesById(Long.parseLong(accounts.get(0).getAccountId()))));
        log.info("accountsTransfer: {}", writer.writeValueAsString(poloniexApiClient.accountsTransfer(AccountsTransferRequest.builder().currency("USDT").amount("10.5").fromAccount("SPOT").toAccount("FUTURES").build())));
        log.info("getAccountsTransfers: {}", writer.writeValueAsString(poloniexApiClient.getAccountsTransfers(null, null, null, null)));
        log.info("getAccountsTransfers: {}", writer.writeValueAsString(poloniexApiClient.getAccountsTransferById(123L)));
        log.info("getFeeInfo: {}", writer.writeValueAsString(poloniexApiClient.getFeeInfo()));
        log.info("getAccountsActivity: {}", writer.writeValueAsString(poloniexApiClient.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "")));

        // wallets
        log.info("getDepositAddresses: {}", writer.writeValueAsString(poloniexApiClient.getDepositAddresses()));
        log.info("getDepositAddressesByCurrency: {}", writer.writeValueAsString(poloniexApiClient.getDepositAddressesByCurrency("USDT")));
        log.info("addNewCurrencyAddress: {}", writer.writeValueAsString(poloniexApiClient.addNewCurrencyAddress(NewCurrencyAddressRequest.builder().currency("USDT").build())));
        log.info("getWalletsActivities: {}", writer.writeValueAsString(poloniexApiClient.getWalletsActivities(System.currentTimeMillis() - 10000000L, System.currentTimeMillis(), null)));
        log.info("withdrawCurrency: {}", writer.writeValueAsString(poloniexApiClient.withdrawCurrency(WithdrawCurrencyRequest.builder().currency("XRP").amount("1.50").address("0xbb8d").paymentID("123").build())));

        // orders
        Order order1 = poloniexApiClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT", "20640", "1", "", "T_D_UP_" + System.currentTimeMillis());
        log.info("placeOrder: {}", writer.writeValueAsString(order1));
        Order order2 = poloniexApiClient.placeOrder("ETH_USDT", "BUY", "GTC", "LIMIT", "SPOT", "1121", "1", "", "T_D_UP_" + System.currentTimeMillis());
        Order order3 = poloniexApiClient.placeOrder("ETH_USDT", "BUY", "GTC", "LIMIT", "SPOT", "1121", "1", "", "T_D_UP_" + System.currentTimeMillis());
        Order order4 = poloniexApiClient.placeOrder("ETH_USDT", "BUY", "GTC", "LIMIT", "SPOT", "56", "1", "", "T_D_UP_" + System.currentTimeMillis());
        Order order5 = poloniexApiClient.placeOrder("TRX_USDT", "BUY", "GTC", "LIMIT", "SPOT", "0.01", "1000", "", "T_D_UP_" + System.currentTimeMillis());
        Order order6 = poloniexApiClient.placeOrder("ETH_USDT", "BUY", "GTC", "LIMIT", "SPOT", "56", "1", "", "T_D_UP_" + System.currentTimeMillis());
        log.info("getOrderByOrderId: {}", writer.writeValueAsString(poloniexApiClient.getOrderByOrderId(order1.getId())));
        log.info("getOrderByClientOrderId: {}", writer.writeValueAsString(poloniexApiClient.getOrderByClientOrderId(order2.getClientOrderId())));
        log.info("getOrders: {}", writer.writeValueAsString(poloniexApiClient.getOrders(null, null, null, null, null)));
        log.info("getOrders: {}", writer.writeValueAsString(poloniexApiClient.getOrders("ETH_USDT", "BUY", null, "NEXT", 10)));
        log.info("cancelOrderByOrderId: {}", writer.writeValueAsString(poloniexApiClient.cancelOrderByOrderId(order1.getId())));
        log.info("cancelOrderByClientOrderId: {}", writer.writeValueAsString(poloniexApiClient.cancelOrderByClientOrderId(order2.getClientOrderId())));
        log.info("cancelOrderByIds: {}", writer.writeValueAsString(poloniexApiClient.cancelOrderByIds(new String[]{order3.getId()},new String[]{order4.getClientOrderId()})));
        log.info("cancelAllOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelAllOrders(new String[]{"TRX_USDT"}, new String[]{"SPOT"}))); // cancel order5
        log.info("cancelAllOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelAllOrders(null, null))); // cancel order6
        SmartOrder smartOrder1 = poloniexApiClient.placeSmartOrder("ETH_USDT", "BUY", "0.1", "STOP_LIMIT", "SPOT", "FOK", "3900", "100", "T_D_UP_" + System.currentTimeMillis(), "4000");
        log.info("placeSmartOrder: {}", writer.writeValueAsString(smartOrder1));
        SmartOrder smartOrder2 = poloniexApiClient.placeSmartOrder("ETH_USDT", "BUY", "0.1", "STOP_LIMIT", "SPOT", "FOK", "3900", "100", "T_D_UP_" + System.currentTimeMillis(), "4000");
        SmartOrder smartOrder3 = poloniexApiClient.placeSmartOrder("ETH_USDT", "BUY", "0.1", "STOP_LIMIT", "SPOT", "FOK", "3900", "100", "T_D_UP_" + System.currentTimeMillis(), "4000");
        SmartOrder smartOrder4 = poloniexApiClient.placeSmartOrder("ETH_USDT", "BUY", "0.1", "STOP_LIMIT", "SPOT", "FOK", "3900", "100", "T_D_UP_" + System.currentTimeMillis(), "4000");
        SmartOrder smartOrder5 = poloniexApiClient.placeSmartOrder("TRX_USDT", "BUY", "1000", "STOP_LIMIT", "SPOT", "FOK", "0.01", "", "T_D_UP_" + System.currentTimeMillis(), "0.009");
        SmartOrder smartOrder6 = poloniexApiClient.placeSmartOrder("ETH_USDT", "BUY", "0.1", "STOP_LIMIT", "SPOT", "FOK", "3900", "100", "T_D_UP_" + System.currentTimeMillis(), "4000");
        log.info("getSmartOrderByOrderId: {}", writer.writeValueAsString(poloniexApiClient.getSmartOrderByOrderId(String.valueOf(smartOrder1.getId()))));
        log.info("getSmartOrderByClientOrderId: {}", writer.writeValueAsString(poloniexApiClient.getSmartOrderByClientOrderId(smartOrder2.getClientOrderId())));
        log.info("getSmartOrders: {}", writer.writeValueAsString(poloniexApiClient.getSmartOrders(10)));
        log.info("cancelSmartOrderByClientOrderId: {}", writer.writeValueAsString(poloniexApiClient.cancelSmartOrderByOrderId(String.valueOf(smartOrder1.getId()))));
        log.info("cancelSmartOrderByClientOrderId: {}", writer.writeValueAsString(poloniexApiClient.cancelSmartOrderByClientOrderId(smartOrder2.getClientOrderId())));
        log.info("cancelSmartOrderByIds: {}", writer.writeValueAsString(poloniexApiClient.cancelSmartOrderByIds(new String[]{String.valueOf(smartOrder3.getId())},new String[]{smartOrder4.getClientOrderId()})));
        log.info("cancelAllSmartOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelAllSmartOrders(new String[]{"TRX_USDT"}, new String[]{"SPOT"})));
        log.info("cancelAllSmartOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelAllSmartOrders(null, null)));
        log.info("cancelAllSmartOrders: {}", writer.writeValueAsString(poloniexApiClient.cancelAllSmartOrders()));
        log.info("getOrderHistory: {}", writer.writeValueAsString(poloniexApiClient.getOrderHistory(null, null, null, null, null, null, null, null, null, null, null)));
        log.info("getOrderHistory: {}", writer.writeValueAsString(poloniexApiClient.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L, "PRE", "CANCELED", 100, false, null, null)));
        log.info("getSmartOrderHistory: {}", writer.writeValueAsString(poloniexApiClient.getSmartOrderHistory(null, null, null, null, null, null, null, null, null, null, null)));
        log.info("getSmartOrderHistory: {}", writer.writeValueAsString(poloniexApiClient.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null, null, "CANCELED", 1, false, null, null)));

        // trades
        log.info("getTrades: {}", writer.writeValueAsString(poloniexApiClient.getTrades(null, null, null, null, null)));
        log.info("getTrades: {}", writer.writeValueAsString(poloniexApiClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT")));
        log.info("getUserTradesByOrderId: {}", writer.writeValueAsString(poloniexApiClient.getUserTradesByOrderId(62759197997072384L)));

        //kill switch
        log.info("setKillSwitch: {}", writer.writeValueAsString(poloniexApiClient.setKillSwitch("15")));
        log.info("getKillSwitch: {}", writer.writeValueAsString(poloniexApiClient.getKillSwitch()));
    }

    private static void websocketPublicTest() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        PoloPublicWebsocketClient publicWebsocket = new PoloPublicWebsocketClient(httpClient, PUBLIC_WS_URL);

        publicWebsocket.onCandlestickEvent(List.of(CandlestickChannels.CANDLES_MINUTE_1), List.of("BTC_USDT"), new PoloLoggingCallback<>());
        publicWebsocket.onTradeEvent(List.of("ETH_USDT"), new PoloLoggingCallback<>());
        publicWebsocket.onTickerEvent(List.of("BTC_USDT"), new PoloLoggingCallback<>());
        publicWebsocket.onTickerAllEvent(new PoloLoggingCallback<>());
        publicWebsocket.onBookEvent(List.of("ETH_USDT"), null, new PoloLoggingCallback<>());
        publicWebsocket.onBookLv2Event(List.of("BTC_USDT","ETH_USDT"), new PoloLoggingCallback<>());

        // example using inline callback
        PoloApiCallback<TickerEvent> callback = new PoloApiCallback<>() {
            @Override
            public void onResponse(PoloEvent<TickerEvent> response) {
                log.info(response.getData().get(0).getClose());
            }

            @Override
            public void onFailure(Throwable t) {
                log.warn("error",t);
            }
        };

        publicWebsocket.onTickerEvent(List.of("ADA_USDT"), callback);

        // example using unsubscribe and close
        WebSocket forTickerEvent = publicWebsocket.onTickerEvent(List.of("ADA_USDT", "BTC_USDT"), callback);
        try {
            Thread.sleep(5000L);
            // unsubscribe from BTC_USDT but keep ticker subscription for ADA_USDT
            publicWebsocket.unsubscribe(forTickerEvent, List.of(CHANNEL_TICKER), List.of("BTC_USDT"));
            Thread.sleep(5000L);
            // close ticker event websocket
            publicWebsocket.close(forTickerEvent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void websocketPrivateTest() {
        OkHttpClient httpClient = new OkHttpClient.Builder().build();

        PoloPrivateWebsocketClient privateWebsocket = new PoloPrivateWebsocketClient(httpClient, PRIVATE_WS_URL, API_KEY, SECRET);

        privateWebsocket.onOrderEvent(List.of("all"), new PoloLoggingCallback<>());

        // example of resubscribing after a failure
        privateWebsocket.onOrderEvent(List.of("all"), new PoloApiCallback<OrderEvent>() {
            @Override
            public void onResponse(PoloEvent<OrderEvent> response) {
                log.info("resp {}", response);
            }

            @Override
            public void onFailure(Throwable t) {
                try {
                    Thread.sleep(5000);
                    privateWebsocket.onOrderEvent(List.of("all"), this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        privateWebsocket.onBalanceEvent(new PoloLoggingCallback<>());

    }

}