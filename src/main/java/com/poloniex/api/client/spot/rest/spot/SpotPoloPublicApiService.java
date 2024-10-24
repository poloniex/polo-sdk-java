package com.poloniex.api.client.spot.rest.spot;

import com.poloniex.api.client.spot.model.response.spot.*;
import com.poloniex.api.client.spot.common.PoloApiConstants;
import com.poloniex.api.client.spot.model.response.spot.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

/**
 * PoloPublicApiService interface defines the API to communicate with the Poloniex exchange server.
 * <p>
 * Instances of PoloPublicApiService are generated using Retrofit. The generated object will connect to Poloniex's
 * public REST endpoints.
 * <p>
 * Use {@link SpotPoloApiServiceGenerator} to build PoloPublicApiService object
 */

public interface SpotPoloPublicApiService {

    // reference

    /**
     * Symbol Information:
     * get all symbols and their tradeLimit info
     *
     * @return list of markets
     * // 获取所有交易对及其交易限制信息
     */
    @GET(PoloApiConstants.MARKETS)
    Call<List<Market>> getMarkets();

    /**
     * Symbol Information by Id:
     * get symbol and tradeLimit info by Id
     *获取所有交易对及其交易限制信息
     * @param symbol symbol name
     * @return market info
     */
    @GET(PoloApiConstants.MARKET)
    Call<List<Market>> getMarket(@Path("symbol") String symbol);

    /**
     * Currency Information:
     * get all supported currencies
     *获取所有支持的货币信息
     * @return list of currencies
     */
    @GET(PoloApiConstants.CURRENCIES)
    Call<List<Map<String,Currency>>> getCurrencies();

    /**
     * Currency Information:
     * get all supported currencies
     *获取所有支持的货币信息，可选择是否包含多链货币
     * @param includeMultiChainCurrencies Default is false. Indicates if multi chain currencies are included. If set to
     *                                    true, additionally adds a new row for each currency on their respective chain
     *                                    (i.e USDT, USDTETH, USDTTRON will all have entries).
     * @return list of currencies
     */
    @GET(PoloApiConstants.CURRENCIES)
    Call<List<Map<String, Currency>>> getCurrencies(@Query("includeMultiChainCurrencies") Boolean includeMultiChainCurrencies);

    /**
     * CurrencyV2 Information:
     * get all supported currencies
     *获取所有支持的货币信息，使用CurrencyV2格式
     * @return list of currencies
     */
    @GET(PoloApiConstants.CURRENCIESV2)
    Call<List<CurrencyV2>> getCurrenciesV2();

    /**
     * Currency Information:
     * get currency
     *
     * @param currency currency name
     * @param includeMultiChainCurrencies Default is false. Indicates if multi chain currencies are included. If set to
     *                                    true, additionally adds a new row for each currency on their respective chain
     *                                    (i.e USDT, USDTETH, USDTTRON will all have entries).
     *                                    获取指定货币的信息，可选择是否包含多链货币
     * @return currency info
     */
    @GET(PoloApiConstants.CURRENCY)
    Call<Map<String, Currency>> getCurrency(@Path("currency") String currency,
                                     @Query("includeMultiChainCurrencies") Boolean includeMultiChainCurrencies);

    /**
     * CurrencyV2 Information:
     * get currency
     *获取指定货币的信息，使用CurrencyV2格式
     * @param currency currency name
     * @return currency info
     */
    @GET(PoloApiConstants.CURRENCYV2)
    Call<CurrencyV2> getCurrencyV2(@Path("currency") String currency);

    /**
     * System Timestamp:
     * Get current server time.
     *获取当前服务器时间
     * @return server timestamp
     */
    @GET(PoloApiConstants.TIMESTAMP)
    Call<Timestamp> getTimestamp();


    // markets

    /**
     * Prices:
     * Get latest trade price for all symbols
     *获取所有交易对的最新交易价格
     * @return list of prices
     */
    @GET(PoloApiConstants.MARKETS_PRICE_ALL)
    Call<List<Price>> getPrices();

    /**
     * Prices:
     * Get latest trade price for a symbol
     *获取指定交易对的最新交易价格
     * @param symbol symbol name
     * @return price
     */
    @GET(PoloApiConstants.MARKETS_PRICE)
    Call<Price> getPrice(@Path("symbol") String symbol);

    /**
     * Mark Prices:
     * Get latest mark price for all cross margin symbols.
     *获取所有跨保证金交易对的最新标记价格
     * @return all mark prices
     */
    @GET(PoloApiConstants.MARKETS_MARK_PRICE_ALL)
    Call<List<MarkPrice>> getMarkPrices();

    /**
     * Mark Price:
     * Get latest mark price for a single cross margin symbol.
     *获取单个跨保证金交易对的最新标记价格
     * @param symbol symbol name
     * @return mark price
     */
    @GET(PoloApiConstants.MARKETS_MARK_PRICE)
    Call<MarkPrice> getMarkPrice(@Path("symbol") String symbol);

    /**
     * Market Price Components:
     * Get components of the mark price for a given symbol
     *获取指定交易对的标记价格组成部分
     * @param symbol symbol name
     * @return market price components
     */
    @GET(PoloApiConstants.MARKETS_MARK_PRICE_COMPONENTS)
    Call<MarkPriceComponents> getMarketPriceComponents(@Path("symbol") String symbol);

    /**
     * Order Book:
     * Get the order book for a given symbol
     *获取指定交易对的订单簿
     * @param symbol symbol name
     * @param scale controls aggregation by price
     * @param limit maximum number of records returned. The default value of limit is 10. Valid limit values are: 5, 10, 20, 50, 100, 150.
     * @return order book
     */
    @GET(PoloApiConstants.MARKETS_ORDERBOOK)
    Call<OrderBook> getOrderBook(@Path("symbol") String symbol,
                                 @Query("scale") String scale,
                                 @Query("limit") Integer limit);

    /**
     * Candles:
     * Returns OHLC for a symbol at given timeframe (interval).
     *返回给定时间帧（间隔）内的一个符号的开盘价、最高价、最低价和收盘价（OHLC）。
     * @param symbol symbol name
     * @param interval the unit of time to aggregate data by. Valid values: MINUTE_1, MINUTE_5, MINUTE_10, MINUTE_15,
     *                 MINUTE_30, HOUR_1, HOUR_2, HOUR_4, HOUR_6, HOUR_12, DAY_1, DAY_3, WEEK_1 and MONTH_1
     * @param limit maximum number of records returned. The default value is 100 and the max value is 500.
     * @param startTime filters by time. The default value is 0.
     * @param endTime filters by time. The default value is current time
     * @return a list of candles
     */
    @GET(PoloApiConstants.MARKETS_CANDLES)
    Call<List<List<String>>> getCandles(@Path("symbol") String symbol,
                                               @Query("interval") String interval,
                                               @Query("limit") Integer limit,
                                               @Query("startTime") Long startTime,
                                               @Query("endTime") Long endTime);

    /**
     * Trades:
     * Returns a list of recent trades, request param limit is optional, its default value is 500, and max value is 1000.
     *返回最近的交易记录，请求参数limit是可选的，默认值是500，最大值是1000。
     * @param symbol symbol name
     * @param limit maximum number of records returned
     * @return list of trades
     */
    @GET(PoloApiConstants.MARKETS_TRADES)
    Call<List<MarketTrade>> getMarketTrades(@Path("symbol") String symbol,
                                            @Query("limit") Integer limit);

    /**
     * Ticker:
     * Retrieve ticker in last 24 hours fo symbols.
     *获取过去24小时内的行情数据。
     * @param symbol symbol name
     * @return ticker
     */
    @GET(PoloApiConstants.MARKETS_TICKER24H)
    Call<Ticker24h> getTicker24h(@Path("symbol") String symbol);

    /**
     * Ticker:
     * Retrieve ticker in last 24 hours for all symbols.
     * 获取过去24小时内所有符号的行情数据。
     * @return list of tickers
     */
    @GET(PoloApiConstants.MARKETS_TICKER24H_ALL)
    Call<List<Ticker24h>> getTicker24hAll();

    /**
     * Collateral Info:
     * Get collateral information for all currencies.
     *获取所有货币的抵押信息。
     * @return list of collateral information
     */
    @GET(PoloApiConstants.MARGIN_COLLATERAL)
    Call<List<CollateralInfo>> getCollateralInfo();

    /**
     * Collateral Info:
     * Get collateral information for a single currency.
     *获取单一货币的抵押信息。
     * @return collateral information
     */
    @GET(PoloApiConstants.MARGIN_COLLATERAL_BY_CURRENCY)
    Call<CollateralInfo> getCollateralInfo(@Path("currency") String currency);

    /**
     * Borrow Rates Info
     * Get borrow rates information for all tiers and currencies.
     *获取级和货币的借贷利率信息。
     * @return borrow rate tiers
     */
    @GET(PoloApiConstants.MARGIN_BORROW_RATES)
    Call<List<BorrowRateTier>> getBorrowRatesInfo();
}
