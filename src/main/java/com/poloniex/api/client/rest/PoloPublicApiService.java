package com.poloniex.api.client.rest;

import com.poloniex.api.client.model.*;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.Map;

import static com.poloniex.api.client.common.PoloApiConstants.*;

/**
 * PoloPublicApiService interface defines the API to communicate with the Poloniex exchange server.
 * <p>
 * Instances of PoloPublicApiService are generated using Retrofit. The generated object will connect to Poloniex's
 * public REST endpoints.
 * <p>
 * Use {@link PoloApiServiceGenerator} to build PoloPublicApiService object
 */

public interface PoloPublicApiService {

    // reference

    /**
     * Symbol Information:
     * get all symbols and their tradeLimit info
     *
     * @return list of markets
     */
    @GET(MARKETS)
    Call<List<Market>> getMarkets();

    /**
     * Symbol Information by Id:
     * get symbol and tradeLimit info by Id
     *
     * @param symbol symbol name
     * @return market info
     */
    @GET(MARKET)
    Call<List<Market>> getMarket(@Path("symbol") String symbol);

    /**
     * Currency Information:
     * get all supported currencies
     *
     * @return list of currencies
     */
    @GET(CURRENCIES)
    Call<List<Map<String,Currency>>> getCurrencies();

    /**
     * Currency Information:
     * get all supported currencies
     *
     * @param includeMultiChainCurrencies Default is false. Indicates if multi chain currencies are included. If set to
     *                                    true, additionally adds a new row for each currency on their respective chain
     *                                    (i.e USDT, USDTETH, USDTTRON will all have entries).
     * @return list of currencies
     */
    @GET(CURRENCIES)
    Call<List<Map<String, Currency>>> getCurrencies(@Query("includeMultiChainCurrencies") Boolean includeMultiChainCurrencies);

    /**
     * Currency Information:
     * get currency
     *
     * @param currency currency name
     * @param includeMultiChainCurrencies Default is false. Indicates if multi chain currencies are included. If set to
     *                                    true, additionally adds a new row for each currency on their respective chain
     *                                    (i.e USDT, USDTETH, USDTTRON will all have entries).
     * @return currency info
     */
    @GET(CURRENCY)
    Call<Map<String, Currency>> getCurrency(@Path("currency") String currency,
                                     @Query("includeMultiChainCurrencies") Boolean includeMultiChainCurrencies);

    /**
     * System Timestamp:
     * Get current server time.
     *
     * @return server timestamp
     */
    @GET(TIMESTAMP)
    Call<Timestamp> getTimestamp();


    // markets

    /**
     * Prices:
     * Get latest trade price for all symbols
     *
     * @return list of prices
     */
    @GET(MARKETS_PRICE_ALL)
    Call<List<Price>> getPrices();

    /**
     * Prices:
     * Get latest trade price for a symbol
     *
     * @param symbol symbol name
     * @return price
     */
    @GET(MARKETS_PRICE)
    Call<Price> getPrice(@Path("symbol") String symbol);

    /**
     * Mark Prices:
     * Get latest mark price for all cross margin symbols.
     *
     * @return all mark prices
     */
    @GET(MARKETS_MARK_PRICE_ALL)
    Call<List<MarkPrice>> getMarkPrices();

    /**
     * Mark Price:
     * Get latest mark price for a single cross margin symbol.
     *
     * @param symbol symbol name
     * @return mark price
     */
    @GET(MARKETS_MARK_PRICE)
    Call<MarkPrice> getMarkPrice(@Path("symbol") String symbol);

    /**
     * Market Price Components:
     * Get components of the mark price for a given symbol
     *
     * @param symbol symbol name
     * @return market price components
     */
    @GET(MARKETS_MARK_PRICE_COMPONENTS)
    Call<MarkPriceComponents> getMarketPriceComponents(@Path("symbol") String symbol);

    /**
     * Order Book:
     * Get the order book for a given symbol
     *
     * @param symbol symbol name
     * @param scale controls aggregation by price
     * @param limit maximum number of records returned. The default value of limit is 10. Valid limit values are: 5, 10, 20, 50, 100, 150.
     * @return order book
     */
    @GET(MARKETS_ORDERBOOK)
    Call<OrderBook> getOrderBook(@Path("symbol") String symbol,
                                 @Query("scale") String scale,
                                 @Query("limit") Integer limit);

    /**
     * Candles:
     * Returns OHLC for a symbol at given timeframe (interval).
     *
     * @param symbol symbol name
     * @param interval the unit of time to aggregate data by. Valid values: MINUTE_1, MINUTE_5, MINUTE_10, MINUTE_15,
     *                 MINUTE_30, HOUR_1, HOUR_2, HOUR_4, HOUR_6, HOUR_12, DAY_1, DAY_3, WEEK_1 and MONTH_1
     * @param limit maximum number of records returned. The default value is 100 and the max value is 500.
     * @param startTime filters by time. The default value is 0.
     * @param endTime filters by time. The default value is current time
     * @return a list of candles
     */
    @GET(MARKETS_CANDLES)
    Call<List<List<String>>> getCandles(@Path("symbol") String symbol,
                                               @Query("interval") String interval,
                                               @Query("limit") Integer limit,
                                               @Query("startTime") Long startTime,
                                               @Query("endTime") Long endTime);

    /**
     * Trades:
     * Returns a list of recent trades, request param limit is optional, its default value is 500, and max value is 1000.
     *
     * @param symbol symbol name
     * @param limit maximum number of records returned
     * @return list of trades
     */
    @GET(MARKETS_TRADES)
    Call<List<MarketTrade>> getMarketTrades(@Path("symbol") String symbol,
                                            @Query("limit") Integer limit);

    /**
     * Ticker:
     * Retrieve ticker in last 24 hours fo symbols.
     *
     * @param symbol symbol name
     * @return ticker
     */
    @GET(MARKETS_TICKER24H)
    Call<Ticker24h> getTicker24h(@Path("symbol") String symbol);

    /**
     * Ticker:
     * Retrieve ticker in last 24 hours for all symbols.
     * @return list of tickers
     */
    @GET(MARKETS_TICKER24H_ALL)
    Call<List<Ticker24h>> getTicker24hAll();

    /**
     * Collateral Info:
     * Get collateral information for all currencies.
     *
     * @return list of collateral information
     */
    @GET(MARGIN_COLLATERAL)
    Call<List<CollateralInfo>> getCollateralInfo();

    /**
     * Collateral Info:
     * Get collateral information for a single currency.
     *
     * @return collateral information
     */
    @GET(MARGIN_COLLATERAL_BY_CURRENCY)
    Call<CollateralInfo> getCollateralInfo(@Path("currency") String currency);

    /**
     * Borrow Rates Info
     * Get borrow rates information for all tiers and currencies.
     *
     * @return borrow rate tiers
     */
    @GET(MARGIN_BORROW_RATES)
    Call<List<BorrowRateTier>> getBorrowRatesInfo();
}
