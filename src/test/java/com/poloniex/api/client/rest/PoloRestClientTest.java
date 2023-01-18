package com.poloniex.api.client.rest;

import com.poloniex.api.client.common.PoloApiException;
import com.poloniex.api.client.model.*;
import com.poloniex.api.client.model.request.*;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PoloRestClientTest {
    @Mock
    private PoloPrivateApiService poloPrivateApiService;
    @Mock
    private PoloPublicApiService poloPublicApiService;

    PoloRestClient poloRestClient;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        poloRestClient = new PoloRestClient("https://sandbox-api.poloniex.com");
        poloRestClient = new PoloRestClient("https://sandbox-api.poloniex.com", "2", "3");
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPublicApiService"), poloPublicApiService);
    }

    @Test
    void testGetMarkets() throws IOException {
        List<Market> markets = new ArrayList<>();
        Market market = new Market();
        market.setDisplayName("test");
        markets.add(market);
        Call<List<Market>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(markets));

        when(poloPublicApiService.getMarkets()).thenReturn(call);
        List<Market> lom = poloRestClient.getMarkets();
        verify(poloPublicApiService, times(1)).getMarkets();
        assertEquals("test", lom.get(0).getDisplayName());

        //test execute response not successful case
        //only need to test it once since it doesn't vary across each test case
        ResponseBody rb = ResponseBody.create(null,"test");
        Response<List<Market>> response = Response.error(400, rb);
        when(call.execute()).thenReturn(response);
        when(poloPublicApiService.getMarkets()).thenReturn(call);

        try {
            poloRestClient.getMarkets();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("test", e.getMessage());
        }

        //test execute exceptions cases
        //only need to test it once since it doesn't vary across each test case
        when(call.execute()).thenThrow(new IOException("ERROR"));
        when(poloPublicApiService.getMarkets()).thenReturn(call);

        try {
            poloRestClient.getMarkets();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("ERROR", e.getMessage());
        }
    }

    @Test
    void testGetMarket() throws IOException {
        List<Market> markets = new ArrayList<>();
        Market market = new Market();
        market.setDisplayName("test");
        markets.add(market);
        Call<List<Market>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(markets));

        when(poloPublicApiService.getMarket("BTC_USDT")).thenReturn(call);
        List<Market> lom = poloRestClient.getMarket("BTC_USDT");
        verify(poloPublicApiService, times(1)).getMarket("BTC_USDT");
        assertEquals("test", lom.get(0).getDisplayName());
    }

    @Test
    void testGetCurrencies() throws IOException {
        List<Map<String, Currency>> currencies = new ArrayList<>();
        Currency currency = new Currency();
        currency.setId(1);
        Map<String, Currency> map = new HashMap<>();
        map.put("id1", currency);
        currencies.add(map);
        Call<List<Map<String, Currency>>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(currencies));

        //0 argument
        when(poloPublicApiService.getCurrencies()).thenReturn(call);
        List<Map<String, Currency>> lom = poloRestClient.getCurrencies();
        verify(poloPublicApiService, times(1)).getCurrencies();
        assertEquals(1, lom.get(0).get("id1").getId());

        //1 argument
        when(poloPublicApiService.getCurrencies(true)).thenReturn(call);
        lom = poloRestClient.getCurrencies(true);
        verify(poloPublicApiService, times(1)).getCurrencies(true);
        assertEquals(1, lom.get(0).get("id1").getId());
    }

    @Test
    void testGetCurrency() throws IOException {
        Currency currency = new Currency();
        currency.setId(1);
        Map<String, Currency> map = new HashMap<>();
        map.put("id1", currency);
        Call<Map<String, Currency>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(map));
        when(poloPublicApiService.getCurrency("test", true)).thenReturn(call);

        Map<String, Currency> hash = poloRestClient.getCurrency("test", true);
        verify(poloPublicApiService, times(1)).getCurrency("test", true);
        assertEquals(1, hash.get("id1").getId());
    }

    @Test
    void testGetTimestamp() throws IOException {
        Timestamp ts = new Timestamp();
        ts.setServerTime(0L);
        Call<Timestamp> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(ts));
        when(poloPublicApiService.getTimestamp()).thenReturn(call);

        Timestamp timestamp = poloRestClient.getTimestamp();
        verify(poloPublicApiService, times(1)).getTimestamp();
        assertEquals(0L, timestamp.getServerTime());
    }

    @Test
    void testGetPrices() throws IOException {
        List<Price> lop = new ArrayList<>();
        Price price = new Price();
        price.setTs(0L);
        lop.add(price);
        Call<List<Price>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(lop));
        when(poloPublicApiService.getPrices()).thenReturn(call);

        List<Price> prices = poloRestClient.getPrices();
        verify(poloPublicApiService, times(1)).getPrices();
        assertEquals(0L, prices.get(0).getTs());
    }

    @Test
    void testGetPrice() throws IOException {
        Price price = new Price();
        price.setTs(0L);
        Call<Price> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(price));
        when(poloPublicApiService.getPrice("BTC_USDT")).thenReturn(call);

        Price prices = poloRestClient.getPrice("BTC_USDT");
        verify(poloPublicApiService, times(1)).getPrice("BTC_USDT");
        assertEquals(0L, prices.getTs());
    }

    @Test
    void testGetMarkPrices() throws IOException {
        MarkPrice markPrice = new MarkPrice();
        markPrice.setSymbol("TRX_USDT");
        markPrice.setMarkPrice("1.0");
        MarkPrice markPrice2 = new MarkPrice();
        markPrice2.setSymbol("BTC_USDT");
        markPrice2.setMarkPrice("20000");
        Call<List<MarkPrice>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(List.of(markPrice, markPrice2)));
        when(poloPublicApiService.getMarkPrices()).thenReturn(call);

        List<MarkPrice> markPriceResponse = poloRestClient.getMarkPrices();
        verify(poloPublicApiService, times(1)).getMarkPrices();
        assertEquals("20000", markPriceResponse.get(1).getMarkPrice());
    }

    @Test
    void testGetMarkPriceComponents() throws IOException {
        MarkPriceComponents markPriceComponents = new MarkPriceComponents();
        String symbol = "BTC_USDT";
        markPriceComponents.setSymbol(symbol);
        Component component1 = new Component();
        component1.setSymbol(symbol);
        component1.setExchange("POLONIEX");
        component1.setSymbolPrice("17000");
        Component component2 = new Component();
        component2.setSymbol(symbol);
        component2.setExchange("OTHER_EXCHANGE");
        component2.setSymbolPrice("16999");
        markPriceComponents.setComponents(List.of(component1, component2));

        Call<MarkPriceComponents> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(markPriceComponents));
        when(poloPublicApiService.getMarketPriceComponents(symbol)).thenReturn(call);

        MarkPriceComponents markPriceComponentsResponse = poloRestClient.getMarketPriceComponents(symbol);
        verify(poloPublicApiService, times(1)).getMarketPriceComponents(symbol);
        assertEquals("OTHER_EXCHANGE", markPriceComponentsResponse.getComponents().get(1).getExchange());
    }

    @Test
    void testGetMarkPrice() throws IOException {
        MarkPrice markPrice = new MarkPrice();
        markPrice.setSymbol("TRX_USDT");
        markPrice.setMarkPrice("1.0");
        Call<MarkPrice> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(markPrice));
        when(poloPublicApiService.getMarkPrice("BTC_USDT")).thenReturn(call);

        MarkPrice markPriceResponse = poloRestClient.getMarkPrice("BTC_USDT");
        verify(poloPublicApiService, times(1)).getMarkPrice("BTC_USDT");
        assertEquals("1.0", markPriceResponse.getMarkPrice());
    }

    @Test
    void testGetOrderBook() throws IOException {
        OrderBook orderBook = new OrderBook();
        orderBook.setTs(0L);
        Call<OrderBook> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderBook));
        when(poloPublicApiService.getOrderBook("BTC_USDT", "10", 10)).thenReturn(call);

        OrderBook ob = poloRestClient.getOrderBook("BTC_USDT", "10", 10);
        verify(poloPublicApiService, times(1)).getOrderBook("BTC_USDT", "10", 10);
        assertEquals(0L, ob.getTs());
    }

    @Test
    void testGetCandles() throws IOException {
        List<List<String>> lolos = new ArrayList<>();
        List<String> los = new ArrayList<>();
        los.add("test");
        lolos.add(los);
        Call<List<List<String>>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(lolos));
        when(poloPublicApiService.getCandles("ETH_USDT",
                "MINUTE_5", 5, 0L, 1L)).thenReturn(call);
        List<List<String>> candles = poloRestClient.getCandles("ETH_USDT", "MINUTE_5", 5, 0L, 1L);
        verify(poloPublicApiService, times(1))
                .getCandles("ETH_USDT", "MINUTE_5", 5, 0L, 1L);
        assertEquals("test", candles.get(0).get(0));
    }

    @Test
    void testGetMarketTrades() throws IOException {
        List<MarketTrade> lomt = new ArrayList<>();
        MarketTrade mt = new MarketTrade();
        mt.setTs(0L);
        lomt.add(mt);
        Call<List<MarketTrade>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(lomt));
        when(poloPublicApiService.getMarketTrades("BTC_USDT", 2)).thenReturn(call);
        List<MarketTrade> marketTrades = poloRestClient.getMarketTrades("BTC_USDT", 2);
        verify(poloPublicApiService, times(1))
                .getMarketTrades("BTC_USDT", 2);
        assertEquals(0L, marketTrades.get(0).getTs());
    }

    @Test
    void testGetTicker24h() throws IOException {
        Ticker24h ticker = new Ticker24h();
        ticker.setTs(2L);
        Call<Ticker24h> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(ticker));
        when(poloPublicApiService.getTicker24h("Ticker24h")).thenReturn(call);
        Ticker24h ticker24h = poloRestClient.getTicker24h("Ticker24h");
        verify(poloPublicApiService, times(1)).getTicker24h("Ticker24h");
        assertEquals(2L, ticker24h.getTs());
    }

    @Test
    void testGetTicker24hAll() throws IOException {
        List<Ticker24h> lot = new ArrayList<>();
        Ticker24h ticker = new Ticker24h();
        ticker.setTs(2L);
        lot.add(ticker);
        Call<List<Ticker24h>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(lot));
        when(poloPublicApiService.getTicker24hAll()).thenReturn(call);
        List<Ticker24h> ticker24hAll = poloRestClient.getTicker24hAll();
        verify(poloPublicApiService, times(1)).getTicker24hAll();
        assertEquals(2L, ticker24hAll.get(0).getTs());
    }

    @Test
    void testGetCollateralInfo() throws IOException {
        CollateralInfo c1 = new CollateralInfo();
        c1.setCurrency("BTC");
        c1.setCollateralRate("0.95");
        List<CollateralInfo> collateralInfo = List.of(c1);
        Call<List<CollateralInfo>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(collateralInfo));
        when(poloPublicApiService.getCollateralInfo()).thenReturn(call);
        List<CollateralInfo> collateralInfoResponse = poloRestClient.getCollateralInfo();
        verify(poloPublicApiService, times(1)).getCollateralInfo();
        assertEquals("0.95", collateralInfoResponse.get(0).getCollateralRate());
    }

    @Test
    void testGetCollateralInfoByCurrency() throws IOException {
        CollateralInfo c1 = new CollateralInfo();
        c1.setCurrency("BTC");
        c1.setCollateralRate("0.95");
        Call<CollateralInfo> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(c1));
        when(poloPublicApiService.getCollateralInfo("BTC")).thenReturn(call);
        CollateralInfo collateralInfoResponse = poloRestClient.getCollateralInfo("BTC");
        verify(poloPublicApiService, times(1)).getCollateralInfo("BTC");
        assertEquals("0.95", collateralInfoResponse.getCollateralRate());
    }

    @Test
    void testGetBorrowRatesInfo() throws IOException {
        BorrowRateTier borrowRateTier1 = new BorrowRateTier();
        borrowRateTier1.setTier("TIER1");
        BorrowRate br1 = new BorrowRate();
        br1.setCurrency("BTC");
        br1.setBorrowLimit("10");
        BorrowRate br2 = new BorrowRate();
        br2.setCurrency("ETH");
        br2.setBorrowLimit("100");
        borrowRateTier1.setRates(List.of(br1, br2));
        BorrowRateTier borrowRateTier2 = new BorrowRateTier();
        borrowRateTier2.setTier("TIER2");
        BorrowRate br3 = new BorrowRate();
        br3.setCurrency("BTC");
        br3.setBorrowLimit("11");
        borrowRateTier2.setRates(List.of(br3));
        List<BorrowRateTier> borrowRateTiers = List.of(borrowRateTier1, borrowRateTier2);

        Call<List<BorrowRateTier>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(borrowRateTiers));
        when(poloPublicApiService.getBorrowRatesInfo()).thenReturn(call);

        List<BorrowRateTier> borrowRatesInfo = poloRestClient.getBorrowRatesInfo();
        verify(poloPublicApiService, times(1)).getBorrowRatesInfo();
        assertEquals("TIER1", borrowRatesInfo.get(0).getTier());
        assertEquals("100", borrowRatesInfo.get(0).getRates().get(1).getBorrowLimit());
    }

    @Test
    void testGetAccounts() throws NoSuchFieldException, IOException {
        List<Account> loa = new ArrayList<>();
        Account account = new Account();
        account.setAccountId("test");
        loa.add(account);
        Call<List<Account>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loa));
        when(poloPrivateApiService.getAccounts()).thenReturn(call);
        List<Account> accounts = poloRestClient.getAccounts();
        verify(poloPrivateApiService, times(1)).getAccounts();
        assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccounts();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountBalancesByType() throws NoSuchFieldException, IOException {
        List<AccountBalance> loa = new ArrayList<>();
        AccountBalance ab = new AccountBalance();
        ab.setAccountId("test");
        loa.add(ab);
        Call<List<AccountBalance>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loa));
        when(poloPrivateApiService.getAccountBalancesByType("SPOT")).thenReturn(call);
        List<AccountBalance> accounts = poloRestClient.getAccountBalancesByType("SPOT");
        verify(poloPrivateApiService, times(1)).getAccountBalancesByType("SPOT");
        assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountBalancesByType("SPOT");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountBalancesById() throws NoSuchFieldException, IOException {
        List<AccountBalance> loa = new ArrayList<>();
        AccountBalance ab = new AccountBalance();
        ab.setAccountId("test");
        loa.add(ab);
        Call<List<AccountBalance>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loa));
        when(poloPrivateApiService.getAccountBalancesById(1L)).thenReturn(call);
        List<AccountBalance> accounts = poloRestClient.getAccountBalancesById(1L);
        verify(poloPrivateApiService, times(1)).getAccountBalancesById(1L);
        assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountBalancesById(1L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testAccountsTransfer() throws NoSuchFieldException, IOException {
        //argument for method
        AccountsTransferRequest request = AccountsTransferRequest.builder().build();

        AccountsTransferResponse atr = new AccountsTransferResponse();
        atr.setTransferId("test");
        Call<AccountsTransferResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));
        when(poloPrivateApiService.accountsTransfer(any(AccountsTransferRequest.class))).thenReturn(call);
        AccountsTransferResponse response = poloRestClient.accountsTransfer(request);
        verify(poloPrivateApiService, times(1)).accountsTransfer(request);
        assertEquals("test", response.getTransferId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.accountsTransfer(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsTransfers() throws NoSuchFieldException, IOException {
        List<AccountsTransferRecord> loatr = new ArrayList<>();
        AccountsTransferRecord atr = new AccountsTransferRecord();
        atr.setId("test");
        loatr.add(atr);
        Call<List<AccountsTransferRecord>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loatr));
        when(poloPrivateApiService.getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), isNull(), isNull())).thenReturn(call);
        List<AccountsTransferRecord> records = poloRestClient.getAccountsTransfers(1, 1L, "te", "st");
        verify(poloPrivateApiService, times(1))
                .getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), isNull(), isNull());
        assertEquals("test", records.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountsTransfers(1, 1L, "te", "st");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);

        when(poloPrivateApiService.getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), anyLong(), anyLong())).thenReturn(call);
        records = poloRestClient.getAccountsTransfers(1, 1L, "te", "st", 1000L, 2000L);
        verify(poloPrivateApiService, times(1))
                .getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), anyLong(), anyLong());
        assertEquals("test", records.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountsTransfers(1, 1L, "te", "st", 1000L, 2000L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsTransferById() throws NoSuchFieldException, IOException {
        AccountsTransferRecord atr = new AccountsTransferRecord();
        atr.setId("test");
        Call<AccountsTransferRecord> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));
        when(poloPrivateApiService.getAccountsTransferById(1L)).thenReturn(call);
        AccountsTransferRecord record = poloRestClient.getAccountsTransferById(1L);
        verify(poloPrivateApiService, times(1))
                .getAccountsTransferById(1L);
        assertEquals("test", record.getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountsTransferById(1L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsActivity() throws NoSuchFieldException, IOException {
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
        Call<List<ActivityResponse>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(activityResponseList));
        when(poloPrivateApiService.getAccountsActivity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                                                       ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
                                                       ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(),
                                                       ArgumentMatchers.anyString())).thenReturn(call);
        List<ActivityResponse> response = poloRestClient.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");
        verify(poloPrivateApiService, times(1))
                .getAccountsActivity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                        ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
                        ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(),
                        ArgumentMatchers.anyString());
        assertEquals(activityResponse.getId(), response.get(0).getId());
        assertEquals(activityResponse.getCurrency(), response.get(0).getCurrency());
        assertEquals(activityResponse.getAmount(), response.get(0).getAmount());
        assertEquals(activityResponse.getState(), response.get(0).getState());
        assertEquals(activityResponse.getCreateTime(), response.get(0).getCreateTime());
        assertEquals(activityResponse.getDescription(), response.get(0).getDescription());
        assertEquals(activityResponse.getActivityType(), response.get(0).getActivityType());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetFeeInfo() throws NoSuchFieldException, IOException {
        FeeInfo fi = new FeeInfo();
        fi.setTrxDiscount(true);
        Call<FeeInfo> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(fi));
        when(poloPrivateApiService.getFeeInfo()).thenReturn(call);
        FeeInfo feeInfo = poloRestClient.getFeeInfo();
        verify(poloPrivateApiService, times(1))
                .getFeeInfo();
        assertEquals(true, feeInfo.getTrxDiscount());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getFeeInfo();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSubaccounts() throws IOException, NoSuchFieldException {
        Subaccount primary = new Subaccount();
        primary.setAccountId("10000");
        primary.setIsPrimary("true");
        Subaccount subaccount = new Subaccount();
        subaccount.setAccountId("10001");
        subaccount.setIsPrimary("false");
        List<Subaccount> subaccountsResponse = List.of(primary, subaccount);

        Call<List<Subaccount>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountsResponse));

        when(poloPrivateApiService.getSubaccounts()).thenReturn(call);

        List<Subaccount> subaccountsResult = poloRestClient.getSubaccounts();
        verify(poloPrivateApiService, times(1)).getSubaccounts();
        assertEquals("true", subaccountsResult.get(0).getIsPrimary());
        assertEquals("false", subaccountsResult.get(1).getIsPrimary());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.getSubaccounts();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.getSubaccounts();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSubaccounts();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSubaccountBalances() throws IOException, NoSuchFieldException {
        Subaccount primary = new Subaccount();
        primary.setAccountId("10000");
        primary.setIsPrimary("true");
        Balance b1 = new Balance();
        b1.setCurrency("USDT");
        b1.setAvailable("1000");
        primary.setBalances(List.of(b1));
        Subaccount subaccount = new Subaccount();
        subaccount.setAccountId("10001");
        subaccount.setIsPrimary("false");
        Balance b2 = new Balance();
        b2.setCurrency("TRX");
        b2.setAvailable("20000");
        Balance b3 = new Balance();
        b3.setCurrency("USDD");
        b3.setAvailable("300");
        subaccount.setBalances(List.of(b2,b3));
        List<Subaccount> subaccountsResponse = List.of(primary, subaccount);

        Call<List<Subaccount>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountsResponse));

        when(poloPrivateApiService.getSubaccountBalances()).thenReturn(call);

        List<Subaccount> subaccountsResult = poloRestClient.getSubaccountBalances();
        verify(poloPrivateApiService, times(1)).getSubaccountBalances();
        assertEquals("1000", subaccountsResult.get(0).getBalances().get(0).getAvailable());
        assertEquals(2, subaccountsResult.get(1).getBalances().size());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.getSubaccountBalances();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSubaccountBalances();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSubaccountBalancesById() throws IOException, NoSuchFieldException {
        Subaccount subaccount = new Subaccount();
        String accountId = "10001";
        subaccount.setAccountId(accountId);
        subaccount.setIsPrimary("false");
        Balance b2 = new Balance();
        b2.setCurrency("TRX");
        b2.setAvailable("20000");
        Balance b3 = new Balance();
        b3.setCurrency("USDD");
        b3.setAvailable("300");
        subaccount.setBalances(List.of(b2,b3));
        List<Subaccount> subaccountsResponse = List.of(subaccount);

        Call<List<Subaccount>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountsResponse));

        when(poloPrivateApiService.getSubaccountBalancesById(accountId)).thenReturn(call);

        List<Subaccount> subaccountsResult = poloRestClient.getSubaccountBalancesById(accountId);
        verify(poloPrivateApiService, times(1)).getSubaccountBalancesById(accountId);
        assertEquals("20000", subaccountsResult.get(0).getBalances().get(0).getAvailable());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.getSubaccountBalancesById(accountId);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSubaccountBalancesById(accountId);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testTransferForSubaccount() throws IOException, NoSuchFieldException {
        SubaccountTransfer subaccountTransfer = new SubaccountTransfer();
        subaccountTransfer.setTransferId("123456");

        Call<SubaccountTransfer> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountTransfer));

        SubaccountTransferRequest request = SubaccountTransferRequest.builder()
                .fromAccountId("abc123")
                .fromAccountType("SPOT")
                .toAccountId("def456")
                .toAccountType("FUTURES")
                .currency("USDD")
                .amount("10000")
                .build();

        when(poloPrivateApiService.transferForSubaccount(request)).thenReturn(call);

        SubaccountTransfer result = poloRestClient.transferForSubaccount(request);
        verify(poloPrivateApiService, times(1)).transferForSubaccount(request);
        assertEquals("123456", result.getTransferId());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.transferForSubaccount(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.transferForSubaccount(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSubaccountTransferRecords() throws IOException, NoSuchFieldException {
        SubaccountTransfer subaccountTransfer = new SubaccountTransfer();
        subaccountTransfer.setId("123456");
        subaccountTransfer.setAmount("111");
        subaccountTransfer.setState("SUCCESS");
        SubaccountTransfer subaccountTransfer2 = new SubaccountTransfer();
        subaccountTransfer2.setId("123457");
        subaccountTransfer2.setAmount("222");
        subaccountTransfer2.setState("SUCCESS");
        List<SubaccountTransfer> subaccountTransfers = List.of(subaccountTransfer, subaccountTransfer2);

        Call<List<SubaccountTransfer>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountTransfers));

        when(poloPrivateApiService.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L)).thenReturn(call);

        List<SubaccountTransfer> result = poloRestClient.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
        verify(poloPrivateApiService, times(1)).getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
        assertEquals("111", result.get(0).getAmount());
        assertEquals("SUCCESS", result.get(1).getState());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSubaccountTransferRecordsById() throws IOException, NoSuchFieldException {
        Long id = 123456L;
        SubaccountTransfer subaccountTransfer = new SubaccountTransfer();
        subaccountTransfer.setId(String.valueOf(id));
        subaccountTransfer.setAmount("111");
        subaccountTransfer.setState("SUCCESS");
        List<SubaccountTransfer> subaccountTransfers = List.of(subaccountTransfer);

        Call<List<SubaccountTransfer>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountTransfers));

        when(poloPrivateApiService.getSubaccountTransferRecordsById(id)).thenReturn(call);

        List<SubaccountTransfer> result = poloRestClient.getSubaccountTransferRecordsById(id);
        verify(poloPrivateApiService, times(1)).getSubaccountTransferRecordsById(id);
        assertEquals("111", result.get(0).getAmount());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            poloRestClient.getSubaccountTransferRecordsById(id);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSubaccountTransferRecordsById(id);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetDepositAddresses() throws NoSuchFieldException, IOException {
        Map<String, String> map = new HashMap<>();
        map.put("1", "test");
        Call<Map<String, String>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(map));
        when(poloPrivateApiService.getDepositAddressesByCurrency(null)).thenReturn(call);
        Map<String, String> addresses = poloRestClient.getDepositAddresses();
        verify(poloPrivateApiService, times(1))
                .getDepositAddressesByCurrency(null);
        assertEquals("test", addresses.get("1"));

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getDepositAddresses();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetDepositAddressesByCurrency() throws NoSuchFieldException, IOException {
        Map<String, String> map = new HashMap<>();
        map.put("1", "test");
        Call<Map<String, String>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(map));
        when(poloPrivateApiService.getDepositAddressesByCurrency("USDT")).thenReturn(call);
        Map<String, String> addresses = poloRestClient.getDepositAddressesByCurrency("USDT");
        verify(poloPrivateApiService, times(1))
                .getDepositAddressesByCurrency("USDT");
        assertEquals("test", addresses.get("1"));

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getDepositAddressesByCurrency("USDT");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetWalletsActivities() throws NoSuchFieldException, IOException {
        WalletsActivities wa = new WalletsActivities();
        Deposit deposit = new Deposit();
        deposit.setAmount("1000");
        deposit.setCurrency("USDT");
        wa.setDeposits(List.of(deposit));
        Call<WalletsActivities> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(wa));
        when(poloPrivateApiService.getWalletsActivities(1L, 1L,"deposit")).thenReturn(call);
        WalletsActivities walletsActivities = poloRestClient.getWalletsActivities(1L, 1L,"deposit");
        verify(poloPrivateApiService, times(1))
                .getWalletsActivities(1L, 1L,"deposit");
        assertEquals("1000", walletsActivities.getDeposits().get(0).getAmount());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getWalletsActivities(1L, 1L,"deposit");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testAddNewCurrencyAddress() throws NoSuchFieldException, IOException {
        NewCurrencyAddressRequest request = NewCurrencyAddressRequest.builder().build();
        NewCurrencyAddressResponse ncar = new NewCurrencyAddressResponse();
        ncar.setAddress("test");
        Call<NewCurrencyAddressResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(ncar));
        when(poloPrivateApiService.addNewCurrencyAddress(request)).thenReturn(call);
        NewCurrencyAddressResponse address = poloRestClient.addNewCurrencyAddress(request);
        verify(poloPrivateApiService, times(1))
                .addNewCurrencyAddress(request);
        assertEquals("test", address.getAddress());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.addNewCurrencyAddress(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testWithdrawCurrency() throws NoSuchFieldException, IOException {
        WithdrawCurrencyRequest request = WithdrawCurrencyRequest.builder().build();
        WithdrawCurrencyResponse wcr = new WithdrawCurrencyResponse();
        wcr.setWithdrawalRequestsId(1L);
        Call<WithdrawCurrencyResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(wcr));
        when(poloPrivateApiService.withdrawCurrency(request)).thenReturn(call);
        WithdrawCurrencyResponse address = poloRestClient.withdrawCurrency(request);
        verify(poloPrivateApiService, times(1))
                .withdrawCurrency(request);
        assertEquals(1L, address.getWithdrawalRequestsId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.withdrawCurrency(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountMargin() throws IOException, NoSuchFieldException {
        AccountMargin accountMargin = new AccountMargin();
        accountMargin.setTotalMargin("10000");
        accountMargin.setTotalAccountValue("50000");
        Call<AccountMargin> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(accountMargin));
        when(poloPrivateApiService.getAccountMargin("SPOT")).thenReturn(call);
        AccountMargin accountMarginResponse = poloRestClient.getAccountMargin("SPOT");
        verify(poloPrivateApiService, times(1)).getAccountMargin("SPOT");
        assertEquals("10000", accountMarginResponse.getTotalMargin());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getAccountMargin("SPOT");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetBorrowStatus() throws IOException, NoSuchFieldException {
        BorrowStatus borrowStatus1 = new BorrowStatus();
        borrowStatus1.setCurrency("BTC");
        borrowStatus1.setBorrowed("-10.0");
        BorrowStatus borrowStatus2 = new BorrowStatus();
        borrowStatus2.setCurrency("ETH");
        borrowStatus2.setBorrowed("0.0");
        Call<List<BorrowStatus>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(List.of(borrowStatus1, borrowStatus2)));
        when(poloPrivateApiService.getBorrowStatus(null)).thenReturn(call);
        List<BorrowStatus> borrowStatuses = poloRestClient.getBorrowStatus(null);
        verify(poloPrivateApiService, times(1)).getBorrowStatus(null);
        assertEquals("-10.0", borrowStatuses.get(0).getBorrowed());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getBorrowStatus(null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetMaxSize() throws IOException, NoSuchFieldException {
        MaxSize maxSize = new MaxSize();
        maxSize.setSymbol("BTC_USDT");
        maxSize.setMaxLeverage(3);
        Call<MaxSize> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(maxSize));
        when(poloPrivateApiService.getMaxSize("BTC_USDT")).thenReturn(call);
        MaxSize maxSizeResponse = poloRestClient.getMaxSize("BTC_USDT");
        verify(poloPrivateApiService, times(1)).getMaxSize("BTC_USDT");
        assertEquals(3, maxSizeResponse.getMaxLeverage());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getMaxSize("BTC_USDT");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testPlaceOrder() throws NoSuchFieldException, IOException {
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

        Call<Order> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.placeOrder(request)).thenReturn(call);
        Order order = poloRestClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                "20640", "1", "", "T_D_UP_");
        verify(poloPrivateApiService, times(1))
                .placeOrder(request);
        assertEquals("test", order.getId());

        order = poloRestClient.placeOrder(request);
        verify(poloPrivateApiService, times(2))
                .placeOrder(request);
        assertEquals("test", order.getId());

        //exceptions
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                    "20640", "1", "", "T_D_UP_");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        try {
            poloRestClient.placeOrder(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testPlaceOrders() throws NoSuchFieldException, IOException {
        Order orderResponse1 = new Order();
        orderResponse1.setId("test1");
        Order orderResponse2 = new Order();
        orderResponse2.setId("test2");

        OrderRequest request1 = OrderRequest.builder()
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
        OrderRequest request2 = OrderRequest.builder()
                .symbol("TRX_USDT")
                .side("BUY")
                .timeInForce("GTC")
                .type("LIMIT")
                .accountType("SPOT")
                .price("30")
                .quantity("1")
                .amount("")
                .clientOrderId("T_D_UP_")
                .build();

        Call<List<Order>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(List.of(orderResponse1, orderResponse2)));

        List<OrderRequest> orderRequests = List.of(request1, request2);
        when(poloPrivateApiService.placeOrders(orderRequests)).thenReturn(call);
        List<Order> orders = poloRestClient.placeOrders(orderRequests);
        verify(poloPrivateApiService, times(1))
                .placeOrders(orderRequests);
        assertEquals("test1", orders.get(0).getId());
        assertEquals("test2", orders.get(1).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.placeOrders(orderRequests);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelReplaceOrderById() throws IOException, NoSuchFieldException {
        Order orderResponse = new Order();
        String id = "29772698821328896";
        orderResponse.setId(id);
        String clientOrderId = "1234Abc";
        orderResponse.setClientOrderId(clientOrderId);

        Call<Order> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.cancelReplaceOrderById(anyString(), any(OrderRequest.class))).thenReturn(call);

        Order cancelReplaceResponse = poloRestClient.cancelReplaceOrderById(id, clientOrderId, "18000", null,
                null, null, null, null, null);

        verify(poloPrivateApiService, times(1)).cancelReplaceOrderById(anyString(), any(OrderRequest.class));
        assertEquals(id, cancelReplaceResponse.getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelReplaceOrderById(id, clientOrderId, "18000", null, null, null, null, null, null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelReplaceOrderByClientOrderId() throws IOException, NoSuchFieldException {
        Order orderResponse = new Order();
        String id = "29772698821328896";
        orderResponse.setId(id);
        String clientOrderId = "1234Abc";
        orderResponse.setClientOrderId(clientOrderId);

        Call<Order> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.cancelReplaceOrderByClientOrderId(anyString(), any(OrderRequest.class))).thenReturn(call);

        Order cancelReplaceResponse = poloRestClient.cancelReplaceOrderByClientOrderId(clientOrderId, "18000", null,
                null, null, null, null, null);

        verify(poloPrivateApiService, times(1)).cancelReplaceOrderByClientOrderId(anyString(), any(OrderRequest.class));
        assertEquals(id, cancelReplaceResponse.getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelReplaceOrderByClientOrderId(clientOrderId, "18000", null, null, null, null, null, null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        try {
            poloRestClient.cancelReplaceOrderByClientOrderId(clientOrderId, "18000", null, null, null, null, null, null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testPlaceSmartOrder() throws NoSuchFieldException, IOException {
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

        Call<SmartOrder> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));

        when(poloPrivateApiService.placeSmartOrder(request)).thenReturn(call);
        SmartOrder order = poloRestClient.placeSmartOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                "20640", "1", "", "T_D_UP_", "4000");
        verify(poloPrivateApiService, times(1))
                .placeSmartOrder(request);
        assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.placeSmartOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                    "20640", "1", "", "T_D_UP_", "4000");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrderByOrderId() throws NoSuchFieldException, IOException {
        Order orderResponse = new Order();
        orderResponse.setId("test");

        Call<Order> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.getOrderByOrderId("request")).thenReturn(call);
        Order order = poloRestClient.getOrderByOrderId("request");
        verify(poloPrivateApiService, times(1))
                .getOrderByOrderId("request");
        assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getOrderByOrderId("request");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrderByClientOrderId() throws NoSuchFieldException, IOException {
        Order orderResponse = new Order();
        orderResponse.setId("test");

        Call<Order> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.getOrderByClientOrderId("request")).thenReturn(call);
        Order order = poloRestClient.getOrderByClientOrderId("request");
        verify(poloPrivateApiService, times(1))
                .getOrderByClientOrderId("request");
        assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getOrderByClientOrderId("request");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrders() throws NoSuchFieldException, IOException {
        List<Order> loo = new ArrayList<>();
        Order orderResponse = new Order();
        orderResponse.setId("test");
        loo.add(orderResponse);

        Call<List<Order>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loo));
        when(poloPrivateApiService.getOrders("ETH_USDT", "BUY", null, "NEXT", 10)).thenReturn(call);
        List<Order> orders = poloRestClient.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
        verify(poloPrivateApiService, times(1))
                .getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrderByOrderId() throws NoSuchFieldException, IOException {
        List<SmartOrder> loso = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loso.add(orderResponse);

        Call<List<SmartOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loso));
        when(poloPrivateApiService.getSmartOrderByOrderId("id")).thenReturn(call);
        List<SmartOrder> orders = poloRestClient.getSmartOrderByOrderId("id");
        verify(poloPrivateApiService, times(1)).getSmartOrderByOrderId("id");
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSmartOrderByOrderId("id");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrderByClientOrderId() throws NoSuchFieldException, IOException {
        List<SmartOrder> loso = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loso.add(orderResponse);

        Call<List<SmartOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loso));
        when(poloPrivateApiService.getSmartOrderByClientOrderId("id")).thenReturn(call);
        List<SmartOrder> orders = poloRestClient.getSmartOrderByClientOrderId("id");
        verify(poloPrivateApiService, times(1)).getSmartOrderByClientOrderId("id");
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSmartOrderByClientOrderId("id");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrders() throws NoSuchFieldException, IOException {
        List<SmartOrder> loso = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loso.add(orderResponse);

        Call<List<SmartOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loso));
        when(poloPrivateApiService.getSmartOrders(3)).thenReturn(call);
        List<SmartOrder> orders = poloRestClient.getSmartOrders(3);
        verify(poloPrivateApiService, times(1)).getSmartOrders(3);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSmartOrders(3);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelOrderByOrderId() throws NoSuchFieldException, IOException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.cancelOrderByOrderId("request")).thenReturn(call);
        CanceledOrder order = poloRestClient.cancelOrderByOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelOrderByOrderId("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelOrderByOrderId("request");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelOrderByClientOrderId() throws NoSuchFieldException, IOException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.cancelOrderByClientOrderId("request")).thenReturn(call);
        CanceledOrder order = poloRestClient.cancelOrderByClientOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelOrderByClientOrderId("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelOrderByClientOrderId("request");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelOrderByIds() throws NoSuchFieldException, IOException {
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

        Call<List<CanceledOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loco));
        when(poloPrivateApiService.cancelOrderByIds(request)).thenReturn(call);
        List<CanceledOrder> orders = poloRestClient.cancelOrderByIds(orderIds, clientOrderIds);
        verify(poloPrivateApiService, times(1))
                .cancelOrderByIds(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelOrderByIds(orderIds, clientOrderIds);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelAllOrders() throws NoSuchFieldException, IOException {
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

        Call<List<CanceledOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loco));
        when(poloPrivateApiService.cancelAllOrders(request)).thenReturn(call);
        List<CanceledOrder> orders = poloRestClient.cancelAllOrders(symbols, accountTypes);
        verify(poloPrivateApiService, times(1))
                .cancelAllOrders(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelAllOrders(symbols, accountTypes);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelSmartOrderByOrderId() throws NoSuchFieldException, IOException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.cancelSmartOrderById("request")).thenReturn(call);
        CanceledOrder order = poloRestClient.cancelSmartOrderByOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelSmartOrderById("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelSmartOrderByOrderId("request");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelSmartOrderByClientOrderId() throws NoSuchFieldException, IOException {
        CanceledOrder orderResponse = new CanceledOrder();
        orderResponse.setOrderId("test");

        Call<CanceledOrder> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderResponse));
        when(poloPrivateApiService.cancelSmartOrderByClientOrderId("request")).thenReturn(call);
        CanceledOrder order = poloRestClient.cancelSmartOrderByClientOrderId("request");
        verify(poloPrivateApiService, times(1))
                .cancelSmartOrderByClientOrderId("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelSmartOrderByClientOrderId("request");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelSmartOrderByIds() throws NoSuchFieldException, IOException {
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

        Call<List<CanceledOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loco));
        when(poloPrivateApiService.cancelSmartOrderByIds(request)).thenReturn(call);
        List<CanceledOrder> orders = poloRestClient.cancelSmartOrderByIds(orderIds, clientOrderIds);
        verify(poloPrivateApiService, times(1))
                .cancelSmartOrderByIds(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelSmartOrderByIds(orderIds, clientOrderIds);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testCancelAllSmartOrders() throws NoSuchFieldException, IOException {
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

        Call<List<CanceledOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loco));
        when(poloPrivateApiService.cancelAllSmartOrders(request)).thenReturn(call);
        List<CanceledOrder> orders = poloRestClient.cancelAllSmartOrders(symbols, accountTypes);
        verify(poloPrivateApiService, times(1))
                .cancelAllSmartOrders(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelAllSmartOrders(symbols, accountTypes);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        //no argument
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);
        request = CancelAllOrdersRequest.builder().build();

        when(poloPrivateApiService.cancelAllSmartOrders(request)).thenReturn(call);
        orders = poloRestClient.cancelAllSmartOrders();
        verify(poloPrivateApiService, times(1))
                .cancelAllSmartOrders(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.cancelAllSmartOrders();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetOrderHistory() throws NoSuchFieldException, IOException {
        List<Order> loo = new ArrayList<>();
        Order orderResponse = new Order();
        orderResponse.setId("test");
        loo.add(orderResponse);

        Call<List<Order>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loo));
        when(poloPrivateApiService.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT",
                58067963198046208L, "PRE", "CANCELED", 100, false, null, null))
                .thenReturn(call);
        List<Order> orders = poloRestClient.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT",
                58067963198046208L, "PRE", "CANCELED", 100, false, null, null);
        verify(poloPrivateApiService, times(1))
                .getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L,
                        "PRE", "CANCELED", 100, false, null, null);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L,
                    "PRE", "CANCELED", 100, false, null, null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetSmartOrderHistory() throws NoSuchFieldException, IOException {
        List<SmartOrder> loo = new ArrayList<>();
        SmartOrder orderResponse = new SmartOrder();
        orderResponse.setId("test");
        loo.add(orderResponse);

        Call<List<SmartOrder>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(loo));
        when(poloPrivateApiService.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT",
                null, null, "CANCELED", 1, false, null, null))
                .thenReturn(call);
        List<SmartOrder> orders = poloRestClient.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT",
                null, null, "CANCELED", 1, false, null, null);
        verify(poloPrivateApiService, times(1))
                .getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null,
                        null, "CANCELED", 1, false, null, null);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null,
                    null, "CANCELED", 1, false, null, null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetTrades() throws NoSuchFieldException, IOException {
        List<Trade> lot = new ArrayList<>();
        Trade trade = new Trade();
        trade.setId("test");
        lot.add(trade);

        Call<List<Trade>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(lot));

        when(poloPrivateApiService.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", null))
                .thenReturn(call);
        List<Trade> orders = poloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");
        verify(poloPrivateApiService, times(1))
                .getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", null);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), poloPrivateApiService);


        when(poloPrivateApiService.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT")))
                .thenReturn(call);
        orders = poloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT"));
        verify(poloPrivateApiService, times(1))
                .getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT"));
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT","ETH_USDT"));
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetUserTradesByOrderId() throws NoSuchFieldException, IOException {
        List<Trade> lot = new ArrayList<>();
        Trade trade = new Trade();
        trade.setId("test");
        lot.add(trade);

        Call<List<Trade>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(lot));
        when(poloPrivateApiService.getUserTradesByOrderId(1L)).thenReturn(call);
        List<Trade> orders = poloRestClient.getUserTradesByOrderId(1L);
        verify(poloPrivateApiService, times(1)).getUserTradesByOrderId(1L);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getUserTradesByOrderId(1L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testSetKillSwitch() throws NoSuchFieldException, IOException {
        KillSwitchRequest killSwitchRequest = new KillSwitchRequest("15");

        KillSwitchResponse killSwitchResponse = new KillSwitchResponse();
        killSwitchResponse.setStartTime(1500L);
        killSwitchResponse.setCancellationTime("3000");

        Call<KillSwitchResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(killSwitchResponse));
        when(poloPrivateApiService.setKillSwitch(killSwitchRequest)).thenReturn(call);
        KillSwitchResponse response = poloRestClient.setKillSwitch("15");
        verify(poloPrivateApiService, times(1)).setKillSwitch(ArgumentMatchers.any());
        assertEquals(killSwitchResponse.getStartTime(), response.getStartTime());
        assertEquals(killSwitchResponse.getCancellationTime(), response.getCancellationTime());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.setKillSwitch("15");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetKillSwitch() throws NoSuchFieldException, IOException {
        KillSwitchResponse killSwitchResponse = new KillSwitchResponse();
        killSwitchResponse.setStartTime(1500L);
        killSwitchResponse.setCancellationTime("3000");

        Call<KillSwitchResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(killSwitchResponse));
        when(poloPrivateApiService.getKillSwitch()).thenReturn(call);
        KillSwitchResponse response = poloRestClient.getKillSwitch();
        verify(poloPrivateApiService, times(1)).getKillSwitch();
        assertEquals(killSwitchResponse.getStartTime(), response.getStartTime());
        assertEquals(killSwitchResponse.getCancellationTime(), response.getCancellationTime());

        //exception
        FieldSetter.setField(poloRestClient, poloRestClient.getClass().getDeclaredField("poloPrivateApiService"), null);
        try {
            poloRestClient.getKillSwitch();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }
}