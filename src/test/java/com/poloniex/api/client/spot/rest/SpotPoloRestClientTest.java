package com.poloniex.api.client.spot.rest;

import com.poloniex.api.client.spot.common.PoloApiException;
import com.poloniex.api.client.spot.model.request.spot.*;
import com.poloniex.api.client.spot.model.response.spot.*;
import com.poloniex.api.client.spot.rest.spot.SpotPoloPrivateApiService;
import com.poloniex.api.client.spot.rest.spot.SpotPoloPublicApiService;
import com.poloniex.api.client.spot.rest.spot.SpotPoloRestClient;
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
class SpotPoloRestClientTest {
    @Mock
    private SpotPoloPrivateApiService spotPoloPrivateApiService;
    @Mock
    private SpotPoloPublicApiService spotPoloPublicApiService;

    SpotPoloRestClient spotPoloRestClient;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        spotPoloRestClient = new SpotPoloRestClient("https://api.poloniex.com/");

        spotPoloRestClient = new SpotPoloRestClient(
                "https://api.poloniex.com/",
                "",
                "");

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), spotPoloPrivateApiService);
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPublicApiService"), spotPoloPublicApiService);
    }


    @Test
        // 使用JUnit测试框架的注解，标记这是一个测试方法
    void testGetMarkets() throws IOException { // 定义测试方法，名称为testGetMarkets
        List<Market> markets = new ArrayList<>(); // 创建一个空的市场列表
        Market market = new Market(); // 创建一个新的市场对象
        market.setDisplayName("test"); // 设置市场对象的显示名称为"test"
        markets.add(market); // 将市场对象添加到市场列表中
        Call<List<Market>> call = mock(Call.class); // 使用Mockito创建Call对象的模拟
        when(call.execute()).thenReturn(Response.success(markets)); // 配置模拟对象在执行时返回成功响应，包含之前创建的市场列表

        when(spotPoloPublicApiService.getMarkets()).thenReturn(call); // 配置模拟的API服务在调用getMarkets时返回上面配置的模拟Call对象
        List<Market> lom = spotPoloRestClient.getMarkets(); // 调用实际的REST客户端方法获取市场列表
        verify(spotPoloPublicApiService, times(1)).getMarkets(); // 验证API服务的getMarkets方法确实被调用了一次
        assertEquals("test", lom.get(0).getDisplayName()); // 断言返回的市场列表中第一个市场的显示名称为"test"

        // 测试执行响应不成功的情况
        ResponseBody rb = ResponseBody.create(null, "test"); // 创建一个响应体，内容为"test"
        Response<List<Market>> response = Response.error(400, rb); // 创建一个错误响应，状态码为400，响应体为上面创建的rb
        when(call.execute()).thenReturn(response); // 配置模拟的Call对象在执行时返回上面创建的错误响应
        when(spotPoloPublicApiService.getMarkets()).thenReturn(call); // 配置模拟的API服务在调用getMarkets时返回上面配置的模拟Call对象

        try {
            spotPoloRestClient.getMarkets(); // 尝试通过REST客户端获取市场列表，期望这里抛出异常
            fail("Exception not thrown"); // 如果没有抛出异常，则测试失败
        } catch (PoloApiException e) {
            assertEquals("test", e.getMessage()); // 捕获到PoloApiException异常，断言异常消息为"test"
        }

        // 测试执行时抛出异常的情况
        when(call.execute()).thenThrow(new IOException("ERROR")); // 配置模拟的Call对象在执行时抛出IOException
        when(spotPoloPublicApiService.getMarkets()).thenReturn(call); // 配置模拟的API服务在调用getMarkets时返回上面配置的模拟Call对象

        try {
            spotPoloRestClient.getMarkets(); // 尝试通过REST客户端获取市场列表，期望这里抛出异常
            fail("Exception not thrown"); // 如果没有抛出异常，则测试失败
        } catch (PoloApiException e) {
            assertEquals("ERROR", e.getMessage()); // 捕获到PoloApiException异常，断言异常消息为"ERROR"
        }
    }

    //    path类型
    @Test
    void testGetMarket() throws IOException {
        List<Market> markets = new ArrayList<>();
        Market market = new Market();
        market.setDisplayName("test");
        markets.add(market);
        Call<List<Market>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(markets));

        when(spotPoloPublicApiService.getMarket("BTC_USDT")).thenReturn(call);
        List<Market> lom = spotPoloRestClient.getMarket("BTC_USDT");
        verify(spotPoloPublicApiService, times(1)).getMarket("BTC_USDT");
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
        when(spotPoloPublicApiService.getCurrencies()).thenReturn(call);
        List<Map<String, Currency>> lom = spotPoloRestClient.getCurrencies();
        verify(spotPoloPublicApiService, times(1)).getCurrencies();
        assertEquals(1, lom.get(0).get("id1").getId());

        //1 argument
        when(spotPoloPublicApiService.getCurrencies(true)).thenReturn(call);
        lom = spotPoloRestClient.getCurrencies(true);
        verify(spotPoloPublicApiService, times(1)).getCurrencies(true);
        assertEquals(1, lom.get(0).get("id1").getId());
    }

    @Test
    void testGetCurrenciesV2() throws IOException {
        List<CurrencyV2> currencies = new ArrayList<>();
        CurrencyV2 currencyV2 = new CurrencyV2();
        currencyV2.setId(1);
        currencies.add(currencyV2);
        Call<List<CurrencyV2>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(currencies));

        when(spotPoloPublicApiService.getCurrenciesV2()).thenReturn(call);
        List<CurrencyV2> lom = spotPoloRestClient.getCurrenciesV2();
        verify(spotPoloPublicApiService, times(1)).getCurrenciesV2();
        assertEquals(1, lom.get(0).getId());
    }

    @Test
    void testGetCurrency() throws IOException {
        Currency currency = new Currency();
        currency.setId(1);
        Map<String, Currency> map = new HashMap<>();
        map.put("id1", currency);
        Call<Map<String, Currency>> call = mock(Call.class);

        when(call.execute()).thenReturn(Response.success(map));
        when(spotPoloPublicApiService.getCurrency("test", true)).thenReturn(call);

        Map<String, Currency> hash = spotPoloRestClient.getCurrency("test", true);
        verify(spotPoloPublicApiService, times(1)).getCurrency("test", true);
        assertEquals(1, hash.get("id1").getId());
    }

    @Test
    void testGetCurrencyV2() throws IOException {
//        List<CurrencyV2> currencies = new ArrayList<>();
        CurrencyV2 currencyV2 = new CurrencyV2();
        currencyV2.setId(1);
//        currencies.add(currencyV2);

        Call<CurrencyV2> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(currencyV2));

        when(spotPoloPublicApiService.getCurrencyV2("BTC")).thenReturn(call);
        CurrencyV2 lom = spotPoloRestClient.getCurrencyV2("BTC");
        verify(spotPoloPublicApiService, times(1)).getCurrencyV2("BTC");
        assertEquals(1, lom.getId());
    }

    @Test
    void testGetTimestamp() throws IOException {
        Timestamp ts = new Timestamp();
        ts.setServerTime(0L);
        Call<Timestamp> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(ts));
        when(spotPoloPublicApiService.getTimestamp()).thenReturn(call);

        Timestamp timestamp = spotPoloRestClient.getTimestamp();
        verify(spotPoloPublicApiService, times(1)).getTimestamp();
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
        when(spotPoloPublicApiService.getPrices()).thenReturn(call);

        List<Price> prices = spotPoloRestClient.getPrices();
        verify(spotPoloPublicApiService, times(1)).getPrices();
        assertEquals(0L, prices.get(0).getTs());
    }

    @Test
    void testGetPrice() throws IOException {
        Price price = new Price();
        price.setTs(0L);
        Call<Price> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(price));
        when(spotPoloPublicApiService.getPrice("BTC_USDT")).thenReturn(call);

        Price prices = spotPoloRestClient.getPrice("BTC_USDT");
        verify(spotPoloPublicApiService, times(1)).getPrice("BTC_USDT");
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
        when(spotPoloPublicApiService.getMarkPrices()).thenReturn(call);

        List<MarkPrice> markPriceResponse = spotPoloRestClient.getMarkPrices();
        verify(spotPoloPublicApiService, times(1)).getMarkPrices();
        assertEquals("20000", markPriceResponse.get(1).getMarkPrice());
    }

    @Test
        // 标记这个方法为JUnit测试方法
    void testGetMarkPriceComponents() throws IOException { // 定义一个测试方法，可能抛出IOException
        MarkPriceComponents markPriceComponents = new MarkPriceComponents(); // 创建一个MarkPriceComponents实例
        String symbol = "BTC_USDT"; // 定义一个字符串变量symbol，表示货币对
        markPriceComponents.setSymbol(symbol); // 设置MarkPriceComponents的symbol属性
        Component component1 = new Component(); // 创建一个Component实例
        component1.setSymbol(symbol); // 设置component1的symbol属性
        component1.setExchange("POLONIEX"); // 设置component1的exchange属性
        component1.setSymbolPrice("17000"); // 设置component1的symbolPrice属性，表示价格

        Component component2 = new Component(); // 创建另一个Component实例
        component2.setSymbol(symbol); // 设置component2的symbol属性
        component2.setExchange("OTHER_EXCHANGE"); // 设置component2的exchange属性
        component2.setSymbolPrice("16999"); // 设置component2的symbolPrice属性，表示价格
        markPriceComponents.setComponents(List.of(component1, component2)); // 将两个组件设置到MarkPriceComponents的components属性中

        Call<MarkPriceComponents> call = mock(Call.class); // 使用Mockito创建一个Call的模拟对象
        when(call.execute()).thenReturn(Response.success(markPriceComponents)); // 配置模拟对象在执行时返回一个成功响应，包含之前设置的MarkPriceComponents实例
        when(spotPoloPublicApiService.getMarketPriceComponents(symbol)).thenReturn(call); // 配置spotPoloPublicApiService的getMarketPriceComponents方法，使其返回上面的模拟Call对象

        MarkPriceComponents markPriceComponentsResponse = spotPoloRestClient.getMarketPriceComponents(symbol); // 调用spotPoloRestClient的getMarketPriceComponents方法，获取MarkPriceComponents实例
        verify(spotPoloPublicApiService, times(1)).getMarketPriceComponents(symbol); // 验证spotPoloPublicApiService的getMarketPriceComponents方法是否被调用了一次
        assertEquals("OTHER_EXCHANGE", markPriceComponentsResponse.getComponents().get(1).getExchange()); // 断言返回的MarkPriceComponents中的第二个组件的交易所名称是否为"OTHER_EXCHANGE"
    }

    @Test
    void testGetMarkPrice() throws IOException {
        MarkPrice markPrice = new MarkPrice();
        markPrice.setSymbol("TRX_USDT");
        markPrice.setMarkPrice("1.0");
        Call<MarkPrice> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(markPrice));
        when(spotPoloPublicApiService.getMarkPrice("BTC_USDT")).thenReturn(call);

        MarkPrice markPriceResponse = spotPoloRestClient.getMarkPrice("BTC_USDT");
        verify(spotPoloPublicApiService, times(1)).getMarkPrice("BTC_USDT");
        assertEquals("1.0", markPriceResponse.getMarkPrice());
    }

    @Test
    void testGetOrderBook() throws IOException {
        OrderBook orderBook = new OrderBook();
        orderBook.setTs(0L);
        Call<OrderBook> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(orderBook));
        when(spotPoloPublicApiService.getOrderBook("BTC_USDT", "10", 10)).thenReturn(call);

        OrderBook ob = spotPoloRestClient.getOrderBook("BTC_USDT", "10", 10);
        verify(spotPoloPublicApiService, times(1)).getOrderBook("BTC_USDT", "10", 10);
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
        when(spotPoloPublicApiService.getCandles("ETH_USDT",
                "MINUTE_5", 5, 0L, 1L)).thenReturn(call);
        List<List<String>> candles = spotPoloRestClient.getCandles("ETH_USDT", "MINUTE_5", 5, 0L, 1L);
        verify(spotPoloPublicApiService, times(1))
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
        when(spotPoloPublicApiService.getMarketTrades("BTC_USDT", 2)).thenReturn(call);
        List<MarketTrade> marketTrades = spotPoloRestClient.getMarketTrades("BTC_USDT", 2);
        verify(spotPoloPublicApiService, times(1))
                .getMarketTrades("BTC_USDT", 2);
        assertEquals(0L, marketTrades.get(0).getTs());
    }

    @Test
    void testGetTicker24h() throws IOException {
        Ticker24h ticker = new Ticker24h();
        ticker.setTs(2L);
        Call<Ticker24h> call = mock(Call.class);

        when(call.execute()).thenReturn(Response.success(ticker));
        when(spotPoloPublicApiService.getTicker24h("Ticker24h")).thenReturn(call);
        Ticker24h ticker24h = spotPoloRestClient.getTicker24h("Ticker24h");
        verify(spotPoloPublicApiService, times(1)).getTicker24h("Ticker24h");
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
        when(spotPoloPublicApiService.getTicker24hAll()).thenReturn(call);
        List<Ticker24h> ticker24hAll = spotPoloRestClient.getTicker24hAll();
        verify(spotPoloPublicApiService, times(1)).getTicker24hAll();
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
        when(spotPoloPublicApiService.getCollateralInfo()).thenReturn(call);

        List<CollateralInfo> collateralInfoResponse = spotPoloRestClient.getCollateralInfo();
        verify(spotPoloPublicApiService, times(1)).getCollateralInfo();
        assertEquals("0.95", collateralInfoResponse.get(0).getCollateralRate());
    }

    @Test
    void testGetCollateralInfoByCurrency() throws IOException {
        CollateralInfo c1 = new CollateralInfo();
        c1.setCurrency("BTC");
        c1.setCollateralRate("0.95");
        Call<CollateralInfo> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(c1));
        when(spotPoloPublicApiService.getCollateralInfo("BTC")).thenReturn(call);
        CollateralInfo collateralInfoResponse = spotPoloRestClient.getCollateralInfo("BTC");
        verify(spotPoloPublicApiService, times(1)).getCollateralInfo("BTC");
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
        when(spotPoloPublicApiService.getBorrowRatesInfo()).thenReturn(call);

        List<BorrowRateTier> borrowRatesInfo = spotPoloRestClient.getBorrowRatesInfo();
        verify(spotPoloPublicApiService, times(1)).getBorrowRatesInfo();
        assertEquals("TIER1", borrowRatesInfo.get(0).getTier());
        assertEquals("100", borrowRatesInfo.get(0).getRates().get(1).getBorrowLimit());
    }

    @Test
        // 标记这个方法为JUnit测试方法
    void testGetAccounts() throws NoSuchFieldException, IOException { // 测试方法声明，可能抛出NoSuchFieldException或IOException
        List<Account> loa = new ArrayList<>(); // 创建一个空的Account列表
        Account account = new Account(); // 创建一个新的Account实例
        account.setAccountId("test"); // 设置Account实例的accountId属性为"test"
        loa.add(account); // 将Account实例添加到列表中

        Call<List<Account>> call = mock(Call.class); // 使用Mockito创建Call类的模拟对象
        when(call.execute()).thenReturn(Response.success(loa)); // 配置模拟对象在执行时返回成功响应，包含之前创建的Account列表
        when(spotPoloPrivateApiService.getAccounts()).thenReturn(call); // 配置spotPoloPrivateApiService的getAccounts方法返回模拟的Call对象

        List<Account> accounts = spotPoloRestClient.getAccounts(); // 调用spotPoloRestClient的getAccounts方法，获取账户列表
        verify(spotPoloPrivateApiService, times(1)).getAccounts(); // 验证spotPoloPrivateApiService的getAccounts方法是否被调用了一次
        assertEquals("test", accounts.get(0).getAccountId()); // 断言返回的账户列表中第一个账户的accountId是否为"test"

        // 测试异常情况
        // 使用FieldSetter工具来设置spotPoloRestClient内的spotPoloPrivateApiService字段为null，模拟客户端未认证的情况
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccounts(); // 尝试获取账户，期待这里会抛出异常
            fail("Exception not thrown"); // 如果没有抛出异常，则测试失败，因为预期应该抛出异常
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage()); // 验证捕获的异常消息是否符合预期
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
        when(spotPoloPrivateApiService.getAccountBalancesByType("SPOT")).thenReturn(call);
        List<AccountBalance> accounts = spotPoloRestClient.getAccountBalancesByType("SPOT");
        verify(spotPoloPrivateApiService, times(1)).getAccountBalancesByType("SPOT");
        assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountBalancesByType("SPOT");
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
        when(spotPoloPrivateApiService.getAccountBalancesById(1L)).thenReturn(call);
        List<AccountBalance> accounts = spotPoloRestClient.getAccountBalancesById(1L);
        verify(spotPoloPrivateApiService, times(1)).getAccountBalancesById(1L);
        assertEquals("test", accounts.get(0).getAccountId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountBalancesById(1L);
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
        when(spotPoloPrivateApiService.accountsTransfer(any(AccountsTransferRequest.class))).thenReturn(call);
        AccountsTransferResponse response = spotPoloRestClient.accountsTransfer(request);
        verify(spotPoloPrivateApiService, times(1)).accountsTransfer(request);
        assertEquals("test", response.getTransferId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.accountsTransfer(request);
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
        when(spotPoloPrivateApiService.getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), isNull(), isNull())).thenReturn(call);
        List<AccountsTransferRecord> records = spotPoloRestClient.getAccountsTransfers(1, 1L, "te", "st");
        verify(spotPoloPrivateApiService, times(1))
                .getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), isNull(), isNull());
        assertEquals("test", records.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountsTransfers(1, 1L, "te", "st");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), spotPoloPrivateApiService);

        when(spotPoloPrivateApiService.getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), anyLong(), anyLong())).thenReturn(call);
        records = spotPoloRestClient.getAccountsTransfers(1, 1L, "te", "st", 1000L, 2000L);
        verify(spotPoloPrivateApiService, times(1))
                .getAccountsTransfers(anyInt(), anyLong(), anyString(), anyString(), anyLong(), anyLong());
        assertEquals("test", records.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountsTransfers(1, 1L, "te", "st", 1000L, 2000L);
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
        when(spotPoloPrivateApiService.getAccountsTransferById(1L)).thenReturn(call);
        AccountsTransferRecord record = spotPoloRestClient.getAccountsTransferById(1L);
        verify(spotPoloPrivateApiService, times(1))
                .getAccountsTransferById(1L);
        assertEquals("test", record.getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountsTransferById(1L);
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
        when(spotPoloPrivateApiService.getAccountsActivity(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyLong(), ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString())).thenReturn(call);
        List<ActivityResponse> response = spotPoloRestClient.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");
        verify(spotPoloPrivateApiService, times(1))
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
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testGetAccountsInterestHistory() throws IOException, NoSuchFieldException {
        List<InterestHistoryResponse> interestHistory = new ArrayList<>();
        InterestHistoryResponse interestHistoryResponse = new InterestHistoryResponse();
        interestHistoryResponse.setId("1");
        interestHistory.add(interestHistoryResponse);
        Call<List<InterestHistoryResponse>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(interestHistory));

        when(spotPoloPrivateApiService.getAccountsInterestHistory(null, null, null, null, null)).thenReturn(call);
        List<InterestHistoryResponse> lom = spotPoloRestClient.getAccountsInterestHistory(null, null, null, null, null);
        verify(spotPoloPrivateApiService, times(1)).getAccountsInterestHistory(null, null, null, null, null);
        assertEquals("1", lom.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountsInterestHistory(null, null, null, null, null);
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
        when(spotPoloPrivateApiService.getFeeInfo()).thenReturn(call);
        FeeInfo feeInfo = spotPoloRestClient.getFeeInfo();
        verify(spotPoloPrivateApiService, times(1))
                .getFeeInfo();
        assertEquals(true, feeInfo.getTrxDiscount());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getFeeInfo();
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

        when(spotPoloPrivateApiService.getSubaccounts()).thenReturn(call);

        List<Subaccount> subaccountsResult = spotPoloRestClient.getSubaccounts();
        verify(spotPoloPrivateApiService, times(1)).getSubaccounts();
        assertEquals("true", subaccountsResult.get(0).getIsPrimary());
        assertEquals("false", subaccountsResult.get(1).getIsPrimary());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.getSubaccounts();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.getSubaccounts();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSubaccounts();
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
        subaccount.setBalances(List.of(b2, b3));
        List<Subaccount> subaccountsResponse = List.of(primary, subaccount);

        Call<List<Subaccount>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountsResponse));

        when(spotPoloPrivateApiService.getSubaccountBalances()).thenReturn(call);

        List<Subaccount> subaccountsResult = spotPoloRestClient.getSubaccountBalances();
        verify(spotPoloPrivateApiService, times(1)).getSubaccountBalances();
        assertEquals("1000", subaccountsResult.get(0).getBalances().get(0).getAvailable());
        assertEquals(2, subaccountsResult.get(1).getBalances().size());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.getSubaccountBalances();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSubaccountBalances();
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
        subaccount.setBalances(List.of(b2, b3));
        List<Subaccount> subaccountsResponse = List.of(subaccount);

        Call<List<Subaccount>> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(subaccountsResponse));

        when(spotPoloPrivateApiService.getSubaccountBalancesById(accountId)).thenReturn(call);

        List<Subaccount> subaccountsResult = spotPoloRestClient.getSubaccountBalancesById(accountId);
        verify(spotPoloPrivateApiService, times(1)).getSubaccountBalancesById(accountId);
        assertEquals("20000", subaccountsResult.get(0).getBalances().get(0).getAvailable());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.getSubaccountBalancesById(accountId);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSubaccountBalancesById(accountId);
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

        when(spotPoloPrivateApiService.transferForSubaccount(request)).thenReturn(call);

        SubaccountTransfer result = spotPoloRestClient.transferForSubaccount(request);
        verify(spotPoloPrivateApiService, times(1)).transferForSubaccount(request);
        assertEquals("123456", result.getTransferId());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.transferForSubaccount(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.transferForSubaccount(request);
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

        when(spotPoloPrivateApiService.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L)).thenReturn(call);

        List<SubaccountTransfer> result = spotPoloRestClient.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
        verify(spotPoloPrivateApiService, times(1)).getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
        assertEquals("111", result.get(0).getAmount());
        assertEquals("SUCCESS", result.get(1).getState());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSubaccountTransferRecords(10, 1000L, "NEXT", null, null, "SPOT", null, "FUTURES", 1670000000L, 1680000000L);
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

        when(spotPoloPrivateApiService.getSubaccountTransferRecordsById(id)).thenReturn(call);

        List<SubaccountTransfer> result = spotPoloRestClient.getSubaccountTransferRecordsById(id);
        verify(spotPoloPrivateApiService, times(1)).getSubaccountTransferRecordsById(id);
        assertEquals("111", result.get(0).getAmount());

        when(call.execute()).thenReturn(Response.error(400, ResponseBody.create(MediaType.get("application/json"), "error")));
        try {
            spotPoloRestClient.getSubaccountTransferRecordsById(id);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("error", e.getMessage());
        }

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSubaccountTransferRecordsById(id);
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
        when(spotPoloPrivateApiService.getDepositAddressesByCurrency(null)).thenReturn(call);
        Map<String, String> addresses = spotPoloRestClient.getDepositAddresses();
        verify(spotPoloPrivateApiService, times(1))
                .getDepositAddressesByCurrency(null);
        assertEquals("test", addresses.get("1"));

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getDepositAddresses();
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
        when(spotPoloPrivateApiService.getDepositAddressesByCurrency("USDT")).thenReturn(call);
        Map<String, String> addresses = spotPoloRestClient.getDepositAddressesByCurrency("USDT");
        verify(spotPoloPrivateApiService, times(1))
                .getDepositAddressesByCurrency("USDT");
        assertEquals("test", addresses.get("1"));

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getDepositAddressesByCurrency("USDT");
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
        when(spotPoloPrivateApiService.getWalletsActivities(1L, 1L, "deposit")).thenReturn(call);
        WalletsActivities walletsActivities = spotPoloRestClient.getWalletsActivities(1L, 1L, "deposit");
        verify(spotPoloPrivateApiService, times(1))
                .getWalletsActivities(1L, 1L, "deposit");
        assertEquals("1000", walletsActivities.getDeposits().get(0).getAmount());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getWalletsActivities(1L, 1L, "deposit");
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
        when(spotPoloPrivateApiService.addNewCurrencyAddress(request)).thenReturn(call);
        NewCurrencyAddressResponse address = spotPoloRestClient.addNewCurrencyAddress(request);
        verify(spotPoloPrivateApiService, times(1))
                .addNewCurrencyAddress(request);
        assertEquals("test", address.getAddress());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.addNewCurrencyAddress(request);
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
        when(spotPoloPrivateApiService.withdrawCurrency(request)).thenReturn(call);
        WithdrawCurrencyResponse address = spotPoloRestClient.withdrawCurrency(request);
        verify(spotPoloPrivateApiService, times(1))
                .withdrawCurrency(request);
        assertEquals(1L, address.getWithdrawalRequestsId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.withdrawCurrency(request);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }

    @Test
    void testWithdrawCurrencyV2() throws NoSuchFieldException, IOException {
        WithdrawCurrencyV2Request request = WithdrawCurrencyV2Request.builder().build();
        WithdrawCurrencyResponse wcr = new WithdrawCurrencyResponse();
        wcr.setWithdrawalRequestsId(1L);
        Call<WithdrawCurrencyResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(wcr));
        when(spotPoloPrivateApiService.withdrawCurrencyV2(request)).thenReturn(call);
        WithdrawCurrencyResponse address = spotPoloRestClient.withdrawCurrencyV2(request);
        verify(spotPoloPrivateApiService, times(1))
                .withdrawCurrencyV2(request);
        assertEquals(1L, address.getWithdrawalRequestsId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.withdrawCurrencyV2(request);
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
        when(spotPoloPrivateApiService.getAccountMargin("SPOT")).thenReturn(call);
        AccountMargin accountMarginResponse = spotPoloRestClient.getAccountMargin("SPOT");
        verify(spotPoloPrivateApiService, times(1)).getAccountMargin("SPOT");
        assertEquals("10000", accountMarginResponse.getTotalMargin());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getAccountMargin("SPOT");
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
        when(spotPoloPrivateApiService.getBorrowStatus(null)).thenReturn(call);
        List<BorrowStatus> borrowStatuses = spotPoloRestClient.getBorrowStatus(null);
        verify(spotPoloPrivateApiService, times(1)).getBorrowStatus(null);
        assertEquals("-10.0", borrowStatuses.get(0).getBorrowed());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getBorrowStatus(null);
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
        when(spotPoloPrivateApiService.getMaxSize("BTC_USDT")).thenReturn(call);
        MaxSize maxSizeResponse = spotPoloRestClient.getMaxSize("BTC_USDT");
        verify(spotPoloPrivateApiService, times(1)).getMaxSize("BTC_USDT");
        assertEquals(3, maxSizeResponse.getMaxLeverage());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getMaxSize("BTC_USDT");
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
        when(spotPoloPrivateApiService.placeOrder(request)).thenReturn(call);
        Order order = spotPoloRestClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                "20640", "1", "", "T_D_UP_");
        verify(spotPoloPrivateApiService, times(1))
                .placeOrder(request);
        assertEquals("test", order.getId());

        order = spotPoloRestClient.placeOrder(request);
        verify(spotPoloPrivateApiService, times(2))
                .placeOrder(request);
        assertEquals("test", order.getId());

        //exceptions
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                    "20640", "1", "", "T_D_UP_");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        try {
            spotPoloRestClient.placeOrder(request);
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
        when(spotPoloPrivateApiService.placeOrders(orderRequests)).thenReturn(call);
        List<Order> orders = spotPoloRestClient.placeOrders(orderRequests);
        verify(spotPoloPrivateApiService, times(1))
                .placeOrders(orderRequests);
        assertEquals("test1", orders.get(0).getId());
        assertEquals("test2", orders.get(1).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.placeOrders(orderRequests);
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
        when(spotPoloPrivateApiService.cancelReplaceOrderById(anyString(), any(OrderRequest.class))).thenReturn(call);

        Order cancelReplaceResponse = spotPoloRestClient.cancelReplaceOrderById(id, clientOrderId, "18000", null,
                null, null, null, null, null);

        verify(spotPoloPrivateApiService, times(1)).cancelReplaceOrderById(anyString(), any(OrderRequest.class));
        assertEquals(id, cancelReplaceResponse.getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelReplaceOrderById(id, clientOrderId, "18000", null, null, null, null, null, null);
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
        when(spotPoloPrivateApiService.cancelReplaceOrderByClientOrderId(anyString(), any(OrderRequest.class))).thenReturn(call);

        Order cancelReplaceResponse = spotPoloRestClient.cancelReplaceOrderByClientOrderId(clientOrderId, "18000", null,
                null, null, null, null, null);

        verify(spotPoloPrivateApiService, times(1)).cancelReplaceOrderByClientOrderId(anyString(), any(OrderRequest.class));
        assertEquals(id, cancelReplaceResponse.getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelReplaceOrderByClientOrderId(clientOrderId, "18000", null, null, null, null, null, null);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        try {
            spotPoloRestClient.cancelReplaceOrderByClientOrderId(clientOrderId, "18000", null, null, null, null, null, null);
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

        when(spotPoloPrivateApiService.placeSmartOrder(request)).thenReturn(call);
        SmartOrder order = spotPoloRestClient.placeSmartOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
                "20640", "1", "", "T_D_UP_", "4000");
        verify(spotPoloPrivateApiService, times(1))
                .placeSmartOrder(request);
        assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.placeSmartOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT",
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
        when(spotPoloPrivateApiService.getOrderByOrderId("request")).thenReturn(call);
        Order order = spotPoloRestClient.getOrderByOrderId("request");
        verify(spotPoloPrivateApiService, times(1))
                .getOrderByOrderId("request");
        assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getOrderByOrderId("request");
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
        when(spotPoloPrivateApiService.getOrderByClientOrderId("request")).thenReturn(call);
        Order order = spotPoloRestClient.getOrderByClientOrderId("request");
        verify(spotPoloPrivateApiService, times(1))
                .getOrderByClientOrderId("request");
        assertEquals("test", order.getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getOrderByClientOrderId("request");
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
        when(spotPoloPrivateApiService.getOrders("ETH_USDT", "BUY", null, "NEXT", 10)).thenReturn(call);
        List<Order> orders = spotPoloRestClient.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
        verify(spotPoloPrivateApiService, times(1))
                .getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);
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
        when(spotPoloPrivateApiService.getSmartOrderByOrderId("id")).thenReturn(call);
        List<SmartOrder> orders = spotPoloRestClient.getSmartOrderByOrderId("id");
        verify(spotPoloPrivateApiService, times(1)).getSmartOrderByOrderId("id");
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSmartOrderByOrderId("id");
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
        when(spotPoloPrivateApiService.getSmartOrderByClientOrderId("id")).thenReturn(call);
        List<SmartOrder> orders = spotPoloRestClient.getSmartOrderByClientOrderId("id");
        verify(spotPoloPrivateApiService, times(1)).getSmartOrderByClientOrderId("id");
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSmartOrderByClientOrderId("id");
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
        when(spotPoloPrivateApiService.getSmartOrders(3)).thenReturn(call);
        List<SmartOrder> orders = spotPoloRestClient.getSmartOrders(3);
        verify(spotPoloPrivateApiService, times(1)).getSmartOrders(3);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSmartOrders(3);
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
        when(spotPoloPrivateApiService.cancelOrderByOrderId("request")).thenReturn(call);
        CanceledOrder order = spotPoloRestClient.cancelOrderByOrderId("request");
        verify(spotPoloPrivateApiService, times(1))
                .cancelOrderByOrderId("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelOrderByOrderId("request");
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
        when(spotPoloPrivateApiService.cancelOrderByClientOrderId("request")).thenReturn(call);
        CanceledOrder order = spotPoloRestClient.cancelOrderByClientOrderId("request");
        verify(spotPoloPrivateApiService, times(1))
                .cancelOrderByClientOrderId("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelOrderByClientOrderId("request");
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
        when(spotPoloPrivateApiService.cancelOrderByIds(request)).thenReturn(call);
        List<CanceledOrder> orders = spotPoloRestClient.cancelOrderByIds(orderIds, clientOrderIds);
        verify(spotPoloPrivateApiService, times(1))
                .cancelOrderByIds(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelOrderByIds(orderIds, clientOrderIds);
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
        when(spotPoloPrivateApiService.cancelAllOrders(request)).thenReturn(call);
        List<CanceledOrder> orders = spotPoloRestClient.cancelAllOrders(symbols, accountTypes);
        verify(spotPoloPrivateApiService, times(1))
                .cancelAllOrders(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelAllOrders(symbols, accountTypes);
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
        when(spotPoloPrivateApiService.cancelSmartOrderById("request")).thenReturn(call);
        CanceledOrder order = spotPoloRestClient.cancelSmartOrderByOrderId("request");
        verify(spotPoloPrivateApiService, times(1))
                .cancelSmartOrderById("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelSmartOrderByOrderId("request");
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
        when(spotPoloPrivateApiService.cancelSmartOrderByClientOrderId("request")).thenReturn(call);
        CanceledOrder order = spotPoloRestClient.cancelSmartOrderByClientOrderId("request");
        verify(spotPoloPrivateApiService, times(1))
                .cancelSmartOrderByClientOrderId("request");
        assertEquals("test", order.getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelSmartOrderByClientOrderId("request");
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
        when(spotPoloPrivateApiService.cancelSmartOrderByIds(request)).thenReturn(call);
        List<CanceledOrder> orders = spotPoloRestClient.cancelSmartOrderByIds(orderIds, clientOrderIds);
        verify(spotPoloPrivateApiService, times(1))
                .cancelSmartOrderByIds(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelSmartOrderByIds(orderIds, clientOrderIds);
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
        when(spotPoloPrivateApiService.cancelAllSmartOrders(request)).thenReturn(call);
        List<CanceledOrder> orders = spotPoloRestClient.cancelAllSmartOrders(symbols, accountTypes);
        verify(spotPoloPrivateApiService, times(1))
                .cancelAllSmartOrders(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelAllSmartOrders(symbols, accountTypes);
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }

        //no argument
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), spotPoloPrivateApiService);
        request = CancelAllOrdersRequest.builder().build();

        when(spotPoloPrivateApiService.cancelAllSmartOrders(request)).thenReturn(call);
        orders = spotPoloRestClient.cancelAllSmartOrders();
        verify(spotPoloPrivateApiService, times(1))
                .cancelAllSmartOrders(request);
        assertEquals("test", orders.get(0).getOrderId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.cancelAllSmartOrders();
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
        when(spotPoloPrivateApiService.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT",
                58067963198046208L, "PRE", "CANCELED", 100, false, null, null))
                .thenReturn(call);
        List<Order> orders = spotPoloRestClient.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT",
                58067963198046208L, "PRE", "CANCELED", 100, false, null, null);
        verify(spotPoloPrivateApiService, times(1))
                .getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L,
                        "PRE", "CANCELED", 100, false, null, null);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L,
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
        when(spotPoloPrivateApiService.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT",
                null, null, "CANCELED", 1, false, null, null))
                .thenReturn(call);
        List<SmartOrder> orders = spotPoloRestClient.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT",
                null, null, "CANCELED", 1, false, null, null);
        verify(spotPoloPrivateApiService, times(1))
                .getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null,
                        null, "CANCELED", 1, false, null, null);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null,
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

        when(spotPoloPrivateApiService.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", null))
                .thenReturn(call);
        List<Trade> orders = spotPoloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");
        verify(spotPoloPrivateApiService, times(1))
                .getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", null);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), spotPoloPrivateApiService);


        when(spotPoloPrivateApiService.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT", "ETH_USDT")))
                .thenReturn(call);
        orders = spotPoloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT", "ETH_USDT"));
        verify(spotPoloPrivateApiService, times(1))
                .getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT", "ETH_USDT"));
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT", List.of("BTC_USDT", "ETH_USDT"));
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
        when(spotPoloPrivateApiService.getUserTradesByOrderId(1L)).thenReturn(call);
        List<Trade> orders = spotPoloRestClient.getUserTradesByOrderId(1L);
        verify(spotPoloPrivateApiService, times(1)).getUserTradesByOrderId(1L);
        assertEquals("test", orders.get(0).getId());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getUserTradesByOrderId(1L);
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
        when(spotPoloPrivateApiService.setKillSwitch(killSwitchRequest)).thenReturn(call);
        KillSwitchResponse response = spotPoloRestClient.setKillSwitch("15");
        verify(spotPoloPrivateApiService, times(1)).setKillSwitch(ArgumentMatchers.any());
        assertEquals(killSwitchResponse.getStartTime(), response.getStartTime());
        assertEquals(killSwitchResponse.getCancellationTime(), response.getCancellationTime());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.setKillSwitch("15");
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
        when(spotPoloPrivateApiService.getKillSwitch()).thenReturn(call);
        KillSwitchResponse response = spotPoloRestClient.getKillSwitch();
        verify(spotPoloPrivateApiService, times(1)).getKillSwitch();
        assertEquals(killSwitchResponse.getStartTime(), response.getStartTime());
        assertEquals(killSwitchResponse.getCancellationTime(), response.getCancellationTime());

        //exception
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), null);
        try {
            spotPoloRestClient.getKillSwitch();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("Client must be authenticated to use this endpoint", e.getMessage());
        }
    }
}