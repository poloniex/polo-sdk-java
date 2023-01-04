package com.poloniex.api.client.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poloniex.api.client.common.PoloApiException;
import com.poloniex.api.client.model.*;
import com.poloniex.api.client.model.request.*;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoloRestClientTest {
    @Mock
    private PoloPrivateApiService poloPrivateApiService;
    @Mock
    private PoloPublicApiService poloPublicApiService;

    PoloRestClient rest;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        rest = new PoloRestClient("https://sandbox-api.poloniex.com");
        rest = new PoloRestClient("https://sandbox-api.poloniex.com", "2", "3");
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPublicApiService"), poloPublicApiService);
    }

    @Test
    void testGetMarkets() {
        List<Market> markets = new ArrayList<>();
        Market market = new Market();
        market.setDisplayName("test");
        markets.add(market);
        Call<List<Market>> call = new Call<>() {
            @Override
            public Response<List<Market>> execute() throws IOException {
                Response<List<Market>> response = Response.success(markets);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Market>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Market>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        when(poloPublicApiService.getMarkets()).thenReturn(call);
        List<Market> lom = rest.getMarkets();
        verify(poloPublicApiService, times(1)).getMarkets();
        Assertions.assertEquals("test", lom.get(0).getDisplayName());

        //test execute response not successful case
        //only need to test it once since it doesn't vary across each test case
        call = new Call<>() {
            @Override
            public Response<List<Market>> execute() throws IOException {
                ResponseBody rb = ResponseBody.create(null,"test");
                Response<List<Market>> response = Response.error(400, rb);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Market>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Market>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getMarkets()).thenReturn(call);
        try {
            rest.getMarkets();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("test", e.getMessage());
        }

        //test execute exceptions cases
        //only need to test it once since it doesn't vary across each test case
        call = new Call<>() {
            @Override
            public Response<List<Market>> execute() throws IOException {
                throw new IOException();
            }

            @Override
            public void enqueue(Callback<List<Market>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Market>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getMarkets()).thenReturn(call);
        try {
            rest.getMarkets();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals(null, e.getMessage());
        }
    }

    @Test
    void testGetMarket() {
        List<Market> markets = new ArrayList<>();
        Market market = new Market();
        market.setDisplayName("test");
        markets.add(market);
        Call<List<Market>> call = new Call<>() {
            @Override
            public Response<List<Market>> execute() throws IOException {
                Response<List<Market>> response = Response.success(markets);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Market>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Market>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        when(poloPublicApiService.getMarket("BTC_USDT")).thenReturn(call);
        List<Market> lom = rest.getMarket("BTC_USDT");
        verify(poloPublicApiService, times(1)).getMarket("BTC_USDT");
        Assertions.assertEquals("test", lom.get(0).getDisplayName());
    }

    @Test
    void testGetCurrencies() {
        List<Map<String, Currency>> currencies = new ArrayList<>();
        Currency currency = new Currency();
        currency.setId(1);
        Map<String, Currency> map = new HashMap<>();
        map.put("id1", currency);
        currencies.add(map);
        Call<List<Map<String, Currency>>> call = new Call<List<Map<String, Currency>>>() {
            @Override
            public Response<List<Map<String, Currency>>> execute() throws IOException {
                Response<List<Map<String, Currency>>> response = Response.success(currencies);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Map<String, Currency>>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Map<String, Currency>>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        //0 argument
        when(poloPublicApiService.getCurrencies()).thenReturn(call);
        List<Map<String, Currency>> lom = rest.getCurrencies();
        verify(poloPublicApiService, times(1)).getCurrencies();
        Assertions.assertEquals(1, lom.get(0).get("id1").getId());

        //1 argument
        when(poloPublicApiService.getCurrencies(true)).thenReturn(call);
        lom = rest.getCurrencies(true);
        verify(poloPublicApiService, times(1)).getCurrencies(true);
        Assertions.assertEquals(1, lom.get(0).get("id1").getId());
    }

    @Test
    void testGetCurrency() {
        Currency currency = new Currency();
        currency.setId(1);
        Map<String, Currency> map = new HashMap<>();
        map.put("id1", currency);
        Call<Map<String, Currency>> call = new Call<Map<String, Currency>>() {
            @Override
            public Response<Map<String, Currency>> execute() throws IOException {
                Response<Map<String, Currency>> response = Response.success(map);
                return response;
            }

            @Override
            public void enqueue(Callback<Map<String, Currency>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Map<String, Currency>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getCurrency("test", true)).thenReturn(call);
        Map<String, Currency> hash = rest.getCurrency("test", true);
        verify(poloPublicApiService, times(1)).getCurrency("test", true);
        Assertions.assertEquals(1, hash.get("id1").getId());
    }

    @Test
    void testGetTimestamp() {
        Timestamp ts = new Timestamp();
        ts.setServerTime(0L);
        Call<Timestamp> call = new Call<Timestamp>() {
            @Override
            public Response<Timestamp> execute() throws IOException {
                Response<Timestamp> response = Response.success(ts);
                return response;
            }

            @Override
            public void enqueue(Callback<Timestamp> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Timestamp> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getTimestamp()).thenReturn(call);
        Timestamp timestamp = rest.getTimestamp();
        verify(poloPublicApiService, times(1)).getTimestamp();
        Assertions.assertEquals(0L, timestamp.getServerTime());
    }

    @Test
    void testGetPrices() {
        List<Price> lop = new ArrayList<>();
        Price price = new Price();
        price.setTs(0L);
        lop.add(price);
        Call<List<Price>> call = new Call<List<Price>>() {
            @Override
            public Response<List<Price>> execute() throws IOException {
                Response<List<Price>> response = Response.success(lop);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Price>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Price>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getPrices()).thenReturn(call);
        List<Price> prices = rest.getPrices();
        verify(poloPublicApiService, times(1)).getPrices();
        Assertions.assertEquals(0L, prices.get(0).getTs());
    }

    @Test
    void testGetPrice() {
        Price price = new Price();
        price.setTs(0L);
        Call<Price> call = new Call<Price>() {
            @Override
            public Response<Price> execute() throws IOException {
                Response<Price> response = Response.success(price);
                return response;
            }

            @Override
            public void enqueue(Callback<Price> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Price> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getPrice("BTC_USDT")).thenReturn(call);
        Price prices = rest.getPrice("BTC_USDT");
        verify(poloPublicApiService, times(1)).getPrice("BTC_USDT");
        Assertions.assertEquals(0L, price.getTs());
    }

    @Test
    void testGetOrderBook() {
        OrderBook orderBook = new OrderBook();
        orderBook.setTs(0L);
        Call<OrderBook> call = new Call<OrderBook>() {
            @Override
            public Response<OrderBook> execute() throws IOException {
                Response<OrderBook> response = Response.success(orderBook);
                return response;
            }

            @Override
            public void enqueue(Callback<OrderBook> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<OrderBook> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getOrderBook("BTC_USDT", "10", 10)).thenReturn(call);
        OrderBook ob = rest.getOrderBook("BTC_USDT", "10", 10);
        verify(poloPublicApiService, times(1)).getOrderBook("BTC_USDT", "10", 10);
        Assertions.assertEquals(0L, ob.getTs());
    }

    @Test
    void testGetCandles() {
        List<List<String>> lolos = new ArrayList<>();
        List<String> los = new ArrayList<>();
        los.add("test");
        lolos.add(los);
        Call<List<List<String>>> call = new Call<List<List<String>>>() {
            @Override
            public Response<List<List<String>>> execute() throws IOException {
                Response<List<List<String>>> response = Response.success(lolos);
                return response;
            }

            @Override
            public void enqueue(Callback<List<List<String>>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<List<String>>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getCandles("ETH_USDT",
                "MINUTE_5", 5, 0L, 1L)).thenReturn(call);
        List<List<String>> candles = rest.getCandles("ETH_USDT", "MINUTE_5", 5, 0L, 1L);
        verify(poloPublicApiService, times(1))
                .getCandles("ETH_USDT", "MINUTE_5", 5, 0L, 1L);
        Assertions.assertEquals("test", candles.get(0).get(0));
    }

    @Test
    void testGetMarketTrades() {
        List<MarketTrade> lomt = new ArrayList<>();
        MarketTrade mt = new MarketTrade();
        mt.setTs(0L);
        lomt.add(mt);
        Call<List<MarketTrade>> call = new Call<List<MarketTrade>>() {
            @Override
            public Response<List<MarketTrade>> execute() throws IOException {
                Response<List<MarketTrade>> response = Response.success(lomt);
                return response;
            }

            @Override
            public void enqueue(Callback<List<MarketTrade>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<MarketTrade>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getMarketTrades("BTC_USDT", 2)).thenReturn(call);
        List<MarketTrade> marketTrades = rest.getMarketTrades("BTC_USDT", 2);
        verify(poloPublicApiService, times(1))
                .getMarketTrades("BTC_USDT", 2);
        Assertions.assertEquals(0L, marketTrades.get(0).getTs());
    }

    @Test
    void testGetTicker24h() {
        Ticker24h ticker = new Ticker24h();
        ticker.setTs(2L);
        Call<Ticker24h> call = new Call<Ticker24h>() {
            @Override
            public Response<Ticker24h> execute() throws IOException {
                Response<Ticker24h> response = Response.success(ticker);
                return response;
            }

            @Override
            public void enqueue(Callback<Ticker24h> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Ticker24h> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getTicker24h("Ticker24h")).thenReturn(call);
        Ticker24h ticker24h = rest.getTicker24h("Ticker24h");
        verify(poloPublicApiService, times(1)).getTicker24h("Ticker24h");
        Assertions.assertEquals(2L, ticker24h.getTs());
    }

    @Test
    void testGetTicker24hAll() {
        List<Ticker24h> lot = new ArrayList<>();
        Ticker24h ticker = new Ticker24h();
        ticker.setTs(2L);
        lot.add(ticker);
        Call<List<Ticker24h>> call = new Call<List<Ticker24h>>() {
            @Override
            public Response<List<Ticker24h>> execute() throws IOException {
                Response<List<Ticker24h>> response = Response.success(lot);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Ticker24h>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Ticker24h>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPublicApiService.getTicker24hAll()).thenReturn(call);
        List<Ticker24h> ticker24hAll = rest.getTicker24hAll();
        verify(poloPublicApiService, times(1)).getTicker24hAll();
        Assertions.assertEquals(2L, ticker24hAll.get(0).getTs());
    }

    @Test
    void testGetAccounts() throws NoSuchFieldException {
        List<Account> loa = new ArrayList<>();
        Account account = new Account();
        account.setAccountId("test");
        loa.add(account);
        Call<List<Account>> call = new Call<List<Account>>() {
            @Override
            public Response<List<Account>> execute() throws IOException {
                Response<List<Account>> response = Response.success(loa);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Account>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Account>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getAccounts()).thenReturn(call);
        List<Account> accounts = rest.getAccounts();
        verify(poloPrivateApiService, times(1)).getAccounts();
        Assertions.assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccounts();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountBalancesByType() throws NoSuchFieldException {
        List<AccountBalance> loa = new ArrayList<>();
        AccountBalance ab = new AccountBalance();
        ab.setAccountId("test");
        loa.add(ab);
        Call<List<AccountBalance>> call = new Call<List<AccountBalance>>() {
            @Override
            public Response<List<AccountBalance>> execute() throws IOException {
                Response<List<AccountBalance>> response = Response.success(loa);
                return response;
            }

            @Override
            public void enqueue(Callback<List<AccountBalance>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<AccountBalance>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getAccountBalancesByType("SPOT")).thenReturn(call);
        List<AccountBalance> accounts = rest.getAccountBalancesByType("SPOT");
        verify(poloPrivateApiService, times(1)).getAccountBalancesByType("SPOT");
        Assertions.assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccountBalancesByType("SPOT");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountBalancesById() throws NoSuchFieldException {
        List<AccountBalance> loa = new ArrayList<>();
        AccountBalance ab = new AccountBalance();
        ab.setAccountId("test");
        loa.add(ab);
        Call<List<AccountBalance>> call = new Call<List<AccountBalance>>() {
            @Override
            public Response<List<AccountBalance>> execute() throws IOException {
                Response<List<AccountBalance>> response = Response.success(loa);
                return response;
            }

            @Override
            public void enqueue(Callback<List<AccountBalance>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<AccountBalance>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getAccountBalancesById(1L)).thenReturn(call);
        List<AccountBalance> accounts = rest.getAccountBalancesById(1L);
        verify(poloPrivateApiService, times(1)).getAccountBalancesById(1L);
        Assertions.assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccountBalancesById(1L);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testAccountsTransfer() throws NoSuchFieldException {
        //argument for method
        AccountsTransferRequest request = AccountsTransferRequest.builder().build();

        AccountsTransferResponse atr = new AccountsTransferResponse();
        atr.setTransferId("test");
        Call<AccountsTransferResponse> call = new Call<AccountsTransferResponse>() {
            @Override
            public Response<AccountsTransferResponse> execute() throws IOException {
                Response<AccountsTransferResponse> response = Response.success(atr);
                return response;
            }

            @Override
            public void enqueue(Callback<AccountsTransferResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<AccountsTransferResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.accountsTransfer(any(AccountsTransferRequest.class))).thenReturn(call);
        AccountsTransferResponse response = rest.accountsTransfer(request);
        verify(poloPrivateApiService, times(1)).accountsTransfer(request);
        Assertions.assertEquals("test", response.getTransferId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.accountsTransfer(request);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsTransfers() throws NoSuchFieldException {
        List<AccountsTransferRecord> loatr = new ArrayList<>();
        AccountsTransferRecord atr = new AccountsTransferRecord();
        atr.setId("test");
        loatr.add(atr);
        Call<List<AccountsTransferRecord>> call = new Call<List<AccountsTransferRecord>>() {
            @Override
            public Response<List<AccountsTransferRecord>> execute() throws IOException {
                Response<List<AccountsTransferRecord>> response = Response.success(loatr);
                return response;
            }

            @Override
            public void enqueue(Callback<List<AccountsTransferRecord>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<AccountsTransferRecord>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        when(poloPrivateApiService.getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), isNull(), isNull())).thenReturn(call);
        List<AccountsTransferRecord> records = rest.getAccountsTransfers(1, 1L, "te", "st");
        verify(poloPrivateApiService, times(1))
                .getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), isNull(), isNull());
        Assertions.assertEquals("test", records.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccountsTransfers(1, 1L, "te", "st");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);

        when(poloPrivateApiService.getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), anyLong(), anyLong())).thenReturn(call);
        records = rest.getAccountsTransfers(1, 1L, "te", "st", 1000L, 2000L);
        verify(poloPrivateApiService, times(1))
                .getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), anyLong(), anyLong());
        Assertions.assertEquals("test", records.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccountsTransfers(1, 1L, "te", "st", 1000L, 2000L);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsTransferById() throws NoSuchFieldException {
        AccountsTransferRecord atr = new AccountsTransferRecord();
        atr.setId("test");
        Call<AccountsTransferRecord> call = new Call<AccountsTransferRecord>() {
            @Override
            public Response<AccountsTransferRecord> execute() throws IOException {
                Response<AccountsTransferRecord> response = Response.success(atr);
                return response;
            }

            @Override
            public void enqueue(Callback<AccountsTransferRecord> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<AccountsTransferRecord> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getAccountsTransferById(1L)).thenReturn(call);
        AccountsTransferRecord record = rest.getAccountsTransferById(1L);
        verify(poloPrivateApiService, times(1))
                .getAccountsTransferById(1L);
        Assertions.assertEquals("test", record.getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccountsTransferById(1L);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsActivity() throws NoSuchFieldException {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId("123");
        activityResponse.setCurrency("BTC");
        activityResponse.setAmount("5");
        activityResponse.setState("SUCCESS");
        activityResponse.setCreateTime(1500L);
        activityResponse.setDescription("Your airdrop for BTC");
        activityResponse.setActivityType(201);

        List<ActivityResponse> activityResponseList = new ArrayList<>();
        activityResponseList.add(activityResponse);
        Call<List<ActivityResponse>> call = new Call<List<ActivityResponse>>()  {
            @Override
            public Response<List<ActivityResponse>> execute() throws IOException {
                Response<List<ActivityResponse>> response = Response.success(activityResponseList);
                return response;
            }

            @Override
            public void enqueue(Callback<List<ActivityResponse>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<ActivityResponse>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getAccountsActivity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                                                       ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
                                                       ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(),
                                                       ArgumentMatchers.anyString())).thenReturn(call);
        List<ActivityResponse> response = rest.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");
        verify(poloPrivateApiService, times(1))
                .getAccountsActivity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
        Assertions.assertEquals(activityResponse.getId(), response.get(0).getId());
        Assertions.assertEquals(activityResponse.getCurrency(), response.get(0).getCurrency());
        Assertions.assertEquals(activityResponse.getAmount(), response.get(0).getAmount());
        Assertions.assertEquals(activityResponse.getState(), response.get(0).getState());
        Assertions.assertEquals(activityResponse.getCreateTime(), response.get(0).getCreateTime());
        Assertions.assertEquals(activityResponse.getDescription(), response.get(0).getDescription());
        Assertions.assertEquals(activityResponse.getActivityType(), response.get(0).getActivityType());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetFeeInfo() throws NoSuchFieldException {
        FeeInfo fi = new FeeInfo();
        fi.setTrxDiscount(true);
        Call<FeeInfo> call = new Call<FeeInfo>() {
            @Override
            public Response<FeeInfo> execute() throws IOException {
                Response<FeeInfo> response = Response.success(fi);
                return response;
            }

            @Override
            public void enqueue(Callback<FeeInfo> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<FeeInfo> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getFeeInfo()).thenReturn(call);
        FeeInfo feeInfo = rest.getFeeInfo();
        verify(poloPrivateApiService, times(1))
                .getFeeInfo();
        Assertions.assertEquals(true, feeInfo.getTrxDiscount());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getFeeInfo();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetDepositAddresses() throws NoSuchFieldException {
        Map<String, String> map = new HashMap<>();
        map.put("1", "test");
        Call<Map<String, String>> call = new Call<Map<String, String>>() {
            @Override
            public Response<Map<String, String>> execute() throws IOException {
                Response<Map<String, String>> response = Response.success(map);
                return response;
            }

            @Override
            public void enqueue(Callback<Map<String, String>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Map<String, String>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getDepositAddressesByCurrency(null)).thenReturn(call);
        Map<String, String> addresses = rest.getDepositAddresses();
        verify(poloPrivateApiService, times(1))
                .getDepositAddressesByCurrency(null);
        Assertions.assertEquals("test", addresses.get("1"));

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getDepositAddresses();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetDepositAddressesByCurrency() throws NoSuchFieldException {
        Map<String, String> map = new HashMap<>();
        map.put("1", "test");
        Call<Map<String, String>> call = new Call<Map<String, String>>() {
            @Override
            public Response<Map<String, String>> execute() throws IOException {
                Response<Map<String, String>> response = Response.success(map);
                return response;
            }

            @Override
            public void enqueue(Callback<Map<String, String>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Map<String, String>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getDepositAddressesByCurrency("USDT")).thenReturn(call);
        Map<String, String> addresses = rest.getDepositAddressesByCurrency("USDT");
        verify(poloPrivateApiService, times(1))
                .getDepositAddressesByCurrency("USDT");
        Assertions.assertEquals("test", addresses.get("1"));

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getDepositAddressesByCurrency("USDT");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetWalletsActivities() throws NoSuchFieldException {
        WalletsActivities wa = new WalletsActivities();
        Call<WalletsActivities> call = new Call<WalletsActivities>() {
            @Override
            public Response<WalletsActivities> execute() throws IOException {
                Response<WalletsActivities> response = Response.success(wa);
                return response;
            }

            @Override
            public void enqueue(Callback<WalletsActivities> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<WalletsActivities> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getWalletsActivities(1L, 1L,"deposit")).thenReturn(call);
        WalletsActivities walletsActivities = rest.getWalletsActivities(1L, 1L,"deposit");
        verify(poloPrivateApiService, times(1))
                .getWalletsActivities(1L, 1L,"deposit");
        Assertions.assertEquals(null, walletsActivities.getDeposits());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getWalletsActivities(1L, 1L,"deposit");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testAddNewCurrencyAddress() throws NoSuchFieldException {
        NewCurrencyAddressRequest request = NewCurrencyAddressRequest.builder().build();
        NewCurrencyAddressResponse ncar = new NewCurrencyAddressResponse();
        ncar.setAddress("test");
        Call<NewCurrencyAddressResponse> call = new Call<NewCurrencyAddressResponse>() {
            @Override
            public Response<NewCurrencyAddressResponse> execute() throws IOException {
                Response<NewCurrencyAddressResponse> response = Response.success(ncar);
                return response;
            }

            @Override
            public void enqueue(Callback<NewCurrencyAddressResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<NewCurrencyAddressResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.addNewCurrencyAddress(request)).thenReturn(call);
        NewCurrencyAddressResponse address = rest.addNewCurrencyAddress(request);
        verify(poloPrivateApiService, times(1))
                .addNewCurrencyAddress(request);
        Assertions.assertEquals("test", address.getAddress());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.addNewCurrencyAddress(request);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testWithdrawCurrency() throws NoSuchFieldException {
        WithdrawCurrencyRequest request = WithdrawCurrencyRequest.builder().build();
        WithdrawCurrencyResponse wcr = new WithdrawCurrencyResponse();
        wcr.setWithdrawalRequestsId(1L);
        Call<WithdrawCurrencyResponse> call = new Call<WithdrawCurrencyResponse>() {
            @Override
            public Response<WithdrawCurrencyResponse> execute() throws IOException {
                Response<WithdrawCurrencyResponse> response = Response.success(wcr);
                return response;
            }

            @Override
            public void enqueue(Callback<WithdrawCurrencyResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<WithdrawCurrencyResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.withdrawCurrency(request)).thenReturn(call);
        WithdrawCurrencyResponse address = rest.withdrawCurrency(request);
        verify(poloPrivateApiService, times(1))
                .withdrawCurrency(request);
        Assertions.assertEquals(1L, address.getWithdrawalRequestsId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.withdrawCurrency(request);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testPlaceOrder() throws NoSuchFieldException {
        Order orderResponse = new Order();
        orderResponse.setId("test");

        OrderRequest request = OrderRequest.builder()
                .symbol("BTC_USDT")
                .side("BUY")
                .timeInForce("GTC")
                .type("LIMIT")
                .accountType("SPOT")
                .price("20640")
                .quantity("1")
                .amount("")
                .clientOrderId("T_D_UP_")
                .build();

        Call<Order> call = new Call<Order>() {
            @Override
            public Response<Order> execute() throws IOException {
                Response<Order> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<Order> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Order> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.placeOrder(request)).thenReturn(call);
        Order order = rest.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                "20640", "1", "", "T_D_UP_");
        verify(poloPrivateApiService, times(1))
                .placeOrder(request);
        Assertions.assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                    "20640", "1", "", "T_D_UP_");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testPlaceSmartOrder() throws NoSuchFieldException {
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");

        SmartOrderRequest request = SmartOrderRequest.builder()
                .symbol("BTC_USDT")
                .side("BUY")
                .timeInForce("GTC")
                .type("LIMIT")
                .accountType("SPOT")
                .price("20640")
                .quantity("1")
                .amount("")
                .clientOrderId("T_D_UP_")
                .stopPrice("4000")
                .build();

        Call<SmartOrder> call = new Call<SmartOrder>() {
            @Override
            public Response<SmartOrder> execute() throws IOException {
                Response<SmartOrder> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<SmartOrder> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<SmartOrder> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.placeSmartOrder(request)).thenReturn(call);
        SmartOrder order = rest.placeSmartOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                "20640", "1", "", "T_D_UP_", "4000");
        verify(poloPrivateApiService, times(1))
                .placeSmartOrder(request);
        Assertions.assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.placeSmartOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                    "20640", "1", "", "T_D_UP_", "4000");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrderByOrderId() throws NoSuchFieldException {
        Order orderResponse = new Order();
        orderResponse.setId("test");

        Call<Order> call = new Call<Order>() {
            @Override
            public Response<Order> execute() throws IOException {
                Response<Order> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<Order> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Order> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getOrderByOrderId("request")).thenReturn(call);
        Order order = rest.getOrderByOrderId("request");
        verify(poloPrivateApiService, times(1))
                .getOrderByOrderId("request");
        Assertions.assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getOrderByOrderId("request");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrderByClientOrderId() throws NoSuchFieldException {
        Order orderResponse = new Order();
        orderResponse.setId("test");

        Call<Order> call = new Call<Order>() {
            @Override
            public Response<Order> execute() throws IOException {
                Response<Order> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<Order> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Order> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getOrderByClientOrderId("request")).thenReturn(call);
        Order order = rest.getOrderByClientOrderId("request");
        verify(poloPrivateApiService, times(1))
                .getOrderByClientOrderId("request");
        Assertions.assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getOrderByClientOrderId("request");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrders() throws NoSuchFieldException {
        List<Order> loo = new ArrayList<>();
        Order orderResponse = new Order();
        orderResponse.setId("test");
        loo.add(orderResponse);

        Call<List<Order>> call = new Call<List<Order>>() {
            @Override
            public Response<List<Order>> execute() throws IOException {
                Response<List<Order>> response = Response.success(loo);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Order>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Order>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getOrders("ETH_USDT", "BUY", null, "NEXT", 10)).thenReturn(call);
        List<Order> orders = rest.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
        verify(poloPrivateApiService, times(1))
                .getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrderByOrderId() throws NoSuchFieldException {
        List<SmartOrder> loso = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loso.add(orderResponse);

        Call<List<SmartOrder>> call = new Call<List<SmartOrder>>() {
            @Override
            public Response<List<SmartOrder>> execute() throws IOException {
                Response<List<SmartOrder>> response = Response.success(loso);
                return response;
            }

            @Override
            public void enqueue(Callback<List<SmartOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<SmartOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getSmartOrderByOrderId("id")).thenReturn(call);
        List<SmartOrder> orders = rest.getSmartOrderByOrderId("id");
        verify(poloPrivateApiService, times(1)).getSmartOrderByOrderId("id");
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getSmartOrderByOrderId("id");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrderByClientOrderId() throws NoSuchFieldException {
        List<SmartOrder> loso = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loso.add(orderResponse);

        Call<List<SmartOrder>> call = new Call<List<SmartOrder>>() {
            @Override
            public Response<List<SmartOrder>> execute() throws IOException {
                Response<List<SmartOrder>> response = Response.success(loso);
                return response;
            }

            @Override
            public void enqueue(Callback<List<SmartOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<SmartOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getSmartOrderByClientOrderId("id")).thenReturn(call);
        List<SmartOrder> orders = rest.getSmartOrderByClientOrderId("id");
        verify(poloPrivateApiService, times(1)).getSmartOrderByClientOrderId("id");
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getSmartOrderByClientOrderId("id");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrders() throws NoSuchFieldException {
        List<SmartOrder> loso = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loso.add(orderResponse);

        Call<List<SmartOrder>> call = new Call<List<SmartOrder>>() {
            @Override
            public Response<List<SmartOrder>> execute() throws IOException {
                Response<List<SmartOrder>> response = Response.success(loso);
                return response;
            }

            @Override
            public void enqueue(Callback<List<SmartOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<SmartOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getSmartOrders(3)).thenReturn(call);
        List<SmartOrder> orders = rest.getSmartOrders(3);
        verify(poloPrivateApiService, times(1)).getSmartOrders(3);
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getSmartOrders(3);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelOrderByOrderId() throws NoSuchFieldException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = new Call<CanceledOrder>() {
            @Override
            public Response<CanceledOrder> execute() throws IOException {
                Response<CanceledOrder> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<CanceledOrder> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<CanceledOrder> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelOrderByOrderId("request")).thenReturn(call);
        CanceledOrder order = rest.cancelOrderByOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelOrderByOrderId("request");
        Assertions.assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelOrderByOrderId("request");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelOrderByClientOrderId() throws NoSuchFieldException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = new Call<CanceledOrder>() {
            @Override
            public Response<CanceledOrder> execute() throws IOException {
                Response<CanceledOrder> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<CanceledOrder> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<CanceledOrder> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelOrderByClientOrderId("request")).thenReturn(call);
        CanceledOrder order = rest.cancelOrderByClientOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelOrderByClientOrderId("request");
        Assertions.assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelOrderByClientOrderId("request");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelOrderByIds() throws NoSuchFieldException {
        List<CanceledOrder> loco = new ArrayList<>();
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");
        loco.add(orderResponse);

        String[] orderIds = new String[]{"request"};
        String[] clientOrderIds = new String[]{"test"};

        CancelOrdersByIdRequest request = CancelOrdersByIdRequest.builder()
                .orderIds(orderIds)
                .clientOrderIds(clientOrderIds)
                .build();

        Call<List<CanceledOrder>> call = new Call<List<CanceledOrder>>() {
            @Override
            public Response<List<CanceledOrder>> execute() throws IOException {
                Response<List<CanceledOrder>> response = Response.success(loco);
                return response;
            }

            @Override
            public void enqueue(Callback<List<CanceledOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<CanceledOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelOrderByIds(request)).thenReturn(call);
        List<CanceledOrder> orders = rest.cancelOrderByIds(orderIds, clientOrderIds);
        verify(poloPrivateApiService, times(1))
                .cancelOrderByIds(request);
        Assertions.assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelOrderByIds(orderIds, clientOrderIds);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelAllOrders() throws NoSuchFieldException, JsonProcessingException {
        List<CanceledOrder> loco = new ArrayList<>();
        CanceledOrder co = new CanceledOrder();
        co.setOrderId("test");
        loco.add(co);

        String[] symbols = new String[]{"TRX_USDT"};
        String[] accountTypes = new String[]{"SPOT"};

        CancelAllOrdersRequest request = CancelAllOrdersRequest.builder()
                .symbols(symbols)
                .accountTypes(accountTypes)
                .build();

        Call<List<CanceledOrder>> call = new Call<List<CanceledOrder>>() {
            @Override
            public Response<List<CanceledOrder>> execute() throws IOException {
                Response<List<CanceledOrder>> response = Response.success(loco);
                return response;
            }

            @Override
            public void enqueue(Callback<List<CanceledOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<CanceledOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelAllOrders(request)).thenReturn(call);
        List<CanceledOrder> orders = rest.cancelAllOrders(symbols, accountTypes);
        verify(poloPrivateApiService, times(1))
                .cancelAllOrders(request);
        Assertions.assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelAllOrders(symbols, accountTypes);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelSmartOrderByOrderId() throws NoSuchFieldException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = new Call<CanceledOrder>() {
            @Override
            public Response<CanceledOrder> execute() throws IOException {
                Response<CanceledOrder> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<CanceledOrder> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<CanceledOrder> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelSmartOrderById("request")).thenReturn(call);
        CanceledOrder order = rest.cancelSmartOrderByOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelSmartOrderById("request");
        Assertions.assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelSmartOrderByOrderId("request");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelSmartOrderByClientOrderId() throws NoSuchFieldException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = new Call<CanceledOrder>() {
            @Override
            public Response<CanceledOrder> execute() throws IOException {
                Response<CanceledOrder> response = Response.success(orderResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<CanceledOrder> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<CanceledOrder> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelSmartOrderByClientOrderId("request")).thenReturn(call);
        CanceledOrder order = rest.cancelSmartOrderByClientOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelSmartOrderByClientOrderId("request");
        Assertions.assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelSmartOrderByClientOrderId("request");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelSmartOrderByIds() throws NoSuchFieldException {
        List<CanceledOrder> loco = new ArrayList<>();
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");
        loco.add(orderResponse);

        String[] orderIds = new String[]{"request"};
        String[] clientOrderIds = new String[]{"test"};

        CancelOrdersByIdRequest request = CancelOrdersByIdRequest.builder()
                .orderIds(orderIds)
                .clientOrderIds(clientOrderIds)
                .build();

        Call<List<CanceledOrder>> call = new Call<List<CanceledOrder>>() {
            @Override
            public Response<List<CanceledOrder>> execute() throws IOException {
                Response<List<CanceledOrder>> response = Response.success(loco);
                return response;
            }

            @Override
            public void enqueue(Callback<List<CanceledOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<CanceledOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelSmartOrderByIds(request)).thenReturn(call);
        List<CanceledOrder> orders = rest.cancelSmartOrderByIds(orderIds, clientOrderIds);
        verify(poloPrivateApiService, times(1))
                .cancelSmartOrderByIds(request);
        Assertions.assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelSmartOrderByIds(orderIds, clientOrderIds);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelAllSmartOrders() throws NoSuchFieldException {
        List<CanceledOrder> loco = new ArrayList<>();
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");
        loco.add(orderResponse);

        String[] symbols = new String[]{"TRX_USDT"};
        String[] accountTypes = new String[]{"SPOT"};

        CancelAllOrdersRequest request = CancelAllOrdersRequest.builder()
                .symbols(symbols)
                .accountTypes(accountTypes)
                .build();

        Call<List<CanceledOrder>> call = new Call<List<CanceledOrder>>() {
            @Override
            public Response<List<CanceledOrder>> execute() throws IOException {
                Response<List<CanceledOrder>> response = Response.success(loco);
                return response;
            }

            @Override
            public void enqueue(Callback<List<CanceledOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<CanceledOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.cancelAllSmartOrders(request)).thenReturn(call);
        List<CanceledOrder> orders = rest.cancelAllSmartOrders(symbols, accountTypes);
        verify(poloPrivateApiService, times(1))
                .cancelAllSmartOrders(request);
        Assertions.assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelAllSmartOrders(symbols, accountTypes);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        //no argument
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);
        request = CancelAllOrdersRequest.builder().build();

        when(poloPrivateApiService.cancelAllSmartOrders(request)).thenReturn(call);
        orders = rest.cancelAllSmartOrders();
        verify(poloPrivateApiService, times(1))
                .cancelAllSmartOrders(request);
        Assertions.assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.cancelAllSmartOrders();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrderHistory() throws NoSuchFieldException {
        List<Order> loo = new ArrayList<>();
        Order orderResponse = new Order();
        orderResponse.setId("test");
        loo.add(orderResponse);

        Call<List<Order>> call = new Call<List<Order>>() {
            @Override
            public Response<List<Order>> execute() throws IOException {
                Response<List<Order>> response = Response.success(loo);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Order>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Order>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT",
                58067963198046208L, "PRE", "CANCELED", 100, false, null, null))
                .thenReturn(call);
        List<Order> orders = rest.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT",
                58067963198046208L, "PRE", "CANCELED", 100, false, null, null);
        verify(poloPrivateApiService, times(1))
                .getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L,
                        "PRE", "CANCELED", 100, false, null, null);
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L,
                    "PRE", "CANCELED", 100, false, null, null);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrderHistory() throws NoSuchFieldException {
        List<SmartOrder> loo = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loo.add(orderResponse);

        Call<List<SmartOrder>> call = new Call<List<SmartOrder>>() {
            @Override
            public Response<List<SmartOrder>> execute() throws IOException {
                Response<List<SmartOrder>> response = Response.success(loo);
                return response;
            }

            @Override
            public void enqueue(Callback<List<SmartOrder>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<SmartOrder>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT",
                null, null, "CANCELED", 1, false, null, null))
                .thenReturn(call);
        List<SmartOrder> orders = rest.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT",
                null, null, "CANCELED", 1, false, null, null);
        verify(poloPrivateApiService, times(1))
                .getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null,
                        null, "CANCELED", 1, false, null, null);
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null,
                    null, "CANCELED", 1, false, null, null);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetTrades() throws NoSuchFieldException {
        List<Trade> lot = new ArrayList<>();
        Trade trade = new Trade();
        trade.setId("test");
        lot.add(trade);

        Call<List<Trade>> call = new Call<List<Trade>>() {
            @Override
            public Response<List<Trade>> execute() throws IOException {
                Response<List<Trade>> response = Response.success(lot);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Trade>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Trade>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };

        when(poloPrivateApiService.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", null))
                .thenReturn(call);
        List<Trade> orders = rest.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");
        verify(poloPrivateApiService, times(1))
                .getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", null);
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);


        when(poloPrivateApiService.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT")))
                .thenReturn(call);
        orders = rest.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT"));
        verify(poloPrivateApiService, times(1))
                .getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT"));
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT"));
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetUserTradesByOrderId() throws NoSuchFieldException {
        List<Trade> lot = new ArrayList<>();
        Trade trade = new Trade();
        trade.setId("test");
        lot.add(trade);

        Call<List<Trade>> call = new Call<List<Trade>>() {
            @Override
            public Response<List<Trade>> execute() throws IOException {
                Response<List<Trade>> response = Response.success(lot);
                return response;
            }

            @Override
            public void enqueue(Callback<List<Trade>> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<List<Trade>> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getUserTradesByOrderId(1L)).thenReturn(call);
        List<Trade> orders = rest.getUserTradesByOrderId(1L);
        verify(poloPrivateApiService, times(1)).getUserTradesByOrderId(1L);
        Assertions.assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getUserTradesByOrderId(1L);
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testSetKillSwitch() throws NoSuchFieldException {
        KillSwitchRequest killSwitchRequest = new KillSwitchRequest("15");

        KillSwitchResponse killSwitchResponse = new KillSwitchResponse();
        killSwitchResponse.setStartTime(1500L);
        killSwitchResponse.setCancellationTime("3000");

        Call<KillSwitchResponse> call = new Call<KillSwitchResponse>() {
            @Override
            public Response<KillSwitchResponse> execute() throws IOException {
                Response<KillSwitchResponse> response = Response.success(killSwitchResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<KillSwitchResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<KillSwitchResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.setKillSwitch(killSwitchRequest)).thenReturn(call);
        KillSwitchResponse response = rest.setKillSwitch("15");
        verify(poloPrivateApiService, times(1)).setKillSwitch(ArgumentMatchers.any());
        Assertions.assertEquals(killSwitchResponse.getStartTime(), response.getStartTime());
        Assertions.assertEquals(killSwitchResponse.getCancellationTime(), response.getCancellationTime());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.setKillSwitch("15");
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetKillSwitch() throws NoSuchFieldException {
        KillSwitchResponse killSwitchResponse = new KillSwitchResponse();
        killSwitchResponse.setStartTime(1500L);
        killSwitchResponse.setCancellationTime("3000");

        Call<KillSwitchResponse> call = new Call<KillSwitchResponse>() {
            @Override
            public Response<KillSwitchResponse> execute() throws IOException {
                Response<KillSwitchResponse> response = Response.success(killSwitchResponse);
                return response;
            }

            @Override
            public void enqueue(Callback<KillSwitchResponse> callback) {

            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<KillSwitchResponse> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        };
        when(poloPrivateApiService.getKillSwitch()).thenReturn(call);
        KillSwitchResponse response = rest.getKillSwitch();
        verify(poloPrivateApiService, times(1)).getKillSwitch();
        Assertions.assertEquals(killSwitchResponse.getStartTime(), response.getStartTime());
        Assertions.assertEquals(killSwitchResponse.getCancellationTime(), response.getCancellationTime());

        //exception
        FieldSetter.setField(rest, rest.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            rest.getKillSwitch();
            Assertions.fail("Exception not thrown");
        } catch (PoloApiException e) {
            Assertions.assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }
}