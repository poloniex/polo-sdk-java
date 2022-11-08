package com.poloniex.api.client.rest;

import com.poloniex.api.client.common.PoloApiException;
import com.poloniex.api.client.model.*;
import com.poloniex.api.client.model.request.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Client which connects to Poloniex public and private Rest endpoints
 */
public class PoloRestClient {

    /**
     * error message
     */
    public static final String AUTHENTICATION_ERROR_MESSAGE = "Client must be authenticated to use this endpoint";

    private final PoloPrivateApiService poloPrivateApiService;
    private final PoloPublicApiService poloPublicApiService;

    /**
     * creates public api service
     *
     * @param host host base url
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code PoloRestClient poloniexApiClient = new PoloRestClient("https://sandbox-api.poloniex.com");}
     *  </pre>
     */
    public PoloRestClient(String host) {
        poloPublicApiService = PoloApiServiceGenerator.createPublicService(host);
        poloPrivateApiService = null;
    }


    /**
     * creates public and private api services
     *
     * @param host host base url
     * @param apiKey Polo API Key for user
     * @param secret Polo secret for API Key
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code PoloRestClient poloniexApiClient = new PoloRestClient("https://sandbox-api.poloniex.com", "<YOUR_API_KEY>", "<YOUR_SECRET>");}
     *  </pre>
     */
    public PoloRestClient(String host, String apiKey, String secret) {
        poloPublicApiService = PoloApiServiceGenerator.createPublicService(host);
        poloPrivateApiService = PoloApiServiceGenerator.createPrivateService(host, apiKey, secret);
    }

    // public endpoints
    // reference

    /**
     * Symbol Information:
     * get all symbols and their tradeLimit info
     *
     * @return list of markets
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Market> markets = poloniexApiClient.getMarkets();}
     *  </pre>
     */
    public List<Market> getMarkets() {
        return execute(poloPublicApiService.getMarkets());
    }

    /**
     * Symbol Information by Id:
     * get symbol and tradeLimit info by Id
     *
     * @param symbol symbol name
     * @return market info
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Market> market = poloniexApiClient.getMarket("BTC_USDT");}
     *  </pre>
     */
    public List<Market> getMarket(String symbol) {
        return execute(poloPublicApiService.getMarket(symbol));
    }

    /**
     * Currency Information:
     * get all supported currencies
     *
     * @return list of currencies
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Map<String, Currency>> currencies = poloniexApiClient.getCurrencies();}
     *  </pre>
     */
    public List<Map<String, Currency>> getCurrencies() {
        return execute(poloPublicApiService.getCurrencies());
    }

    /**
     * Currency Information:
     * get all supported currencies
     *
     * @param includeMultiChainCurrencies Default is false. Indicates if multi chain currencies are included. If set to
     *                                    true, additionally adds a new row for each currency on their respective chain
     *                                    (i.e USDT, USDTETH, USDTTRON will all have entries).
     * @return list of currencies
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Map<String, Currency>> currencies = poloniexApiClient.getCurrencies(true);}
     *  </pre>
     */
    public List<Map<String, Currency>> getCurrencies(Boolean includeMultiChainCurrencies) {
        return execute(poloPublicApiService.getCurrencies(includeMultiChainCurrencies));
    }

    /**
     * Currency Information:
     * get currency
     *
     * @param currency currency name
     * @param includeMultiChainCurrencies Default is false. Indicates if multi chain currencies are included. If set to
     *                                    true, additionally adds a new row for each currency on their respective chain
     *                                    (i.e USDT, USDTETH, USDTTRON will all have entries).
     * @return list of currencies
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Map<String, Currency>> currencies = poloniexApiClient.getCurrencies("BTC", null);}
     *  </pre>
     */
    public Map<String, Currency> getCurrency(String currency, Boolean includeMultiChainCurrencies) {
        return execute(poloPublicApiService.getCurrency(currency, includeMultiChainCurrencies));
    }

    /**
     * System Timestamp:
     * Get current server time.
     *
     * @return server timestamp
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Timestamp timestamp = poloniexApiClient.getTimestamp();}
     *  </pre>
     */
    public Timestamp getTimestamp() {
        return execute(poloPublicApiService.getTimestamp());
    }

    // markets

    /**
     * Prices:
     * Get latest trade price for all symbols
     *
     * @return list of prices
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Price> prices = poloniexApiClient.getPrices();}
     *  </pre>
     */
    public List<Price> getPrices() {
        return execute(poloPublicApiService.getPrices());
    }

    /**
     * Prices:
     * Get latest trade price for a symbol
     *
     * @param symbol symbol name
     * @return price
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Price price = poloniexApiClient.getPrice("BTC_USDT");}
     *  </pre>
     */
    public Price getPrice(String symbol) {
        return execute(poloPublicApiService.getPrice(symbol));
    }

    /**
     * Order Book:
     * Get the order book for a given symbol
     *
     * @param symbol symbol name
     * @param scale controls aggregation by price
     * @param limit maximum number of records returned. The default value of limit is 10. Valid limit values are: 5, 10, 20, 50, 100, 150.
     * @return order book
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code OrderBook orderBook = poloniexApiClient.getOrderBook("BTC_USDT", "10", 10);}
     *  </pre>
     */
    public OrderBook getOrderBook(String symbol, String scale, Integer limit) {
        return execute(poloPublicApiService.getOrderBook(symbol, scale, limit));
    }

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
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<List<String>> candles = poloniexApiClient.getCandles("ETH_USDT", "MINUTE_5", 5, 0L, System.currentTimeMillis());}
     *  </pre>
     */
    public List<List<String>> getCandles(String symbol, String interval, Integer limit, Long startTime, Long endTime) {
        return execute(poloPublicApiService.getCandles(symbol, interval, limit, startTime, endTime));
    }

    /**
     * Trades:
     * Returns a list of recent trades, request param limit is optional, its default value is 500, and max value is 1000.
     *
     * @param symbol symbol name
     * @param limit maximum number of records returned
     * @return list of trades
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<MarketTrade> marketTrades = poloniexApiClient.getMarketTrades("BTC_USDT", 2);}
     *  </pre>
     */
    public List<MarketTrade> getMarketTrades(String symbol, Integer limit) {
        return execute(poloPublicApiService.getMarketTrades(symbol, limit));
    }

    /**
     * Ticker:
     * Retrieve ticker in last 24 hours fo symbols.
     *
     * @param symbol symbol name
     * @return ticker
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Ticker24h ticker24h = poloniexApiClient.getTicker24h("ETH_USDT");}
     *  </pre>
     */
    public Ticker24h getTicker24h(String symbol) {
        return execute(poloPublicApiService.getTicker24h(symbol));
    }

    /**
     * Ticker:
     * Retrieve ticker in last 24 hours for all symbols.
     * @return list of tickers
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Ticker24h> ticker24hAll = poloniexApiClient.getTicker24hAll();}
     *  </pre>
     */
    public List<Ticker24h> getTicker24hAll() {
        return execute(poloPublicApiService.getTicker24hAll());
    }

    // private endpoints
    // accounts

    /**
     * Account Information:
     *
     * @return a list of all accounts of a user.
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Account> accounts = poloniexApiClient.getAccounts();}
     *  </pre>
     */
    public List<Account> getAccounts() {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getAccounts());
    }

    /**
     * Account Balances by type:
     *
     * @param accountType The account type. e.g. SPOT. Passing null will show all account types.
     * @return a list of all accounts of a user with each account’s id, type and balances (assets).
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<AccountBalance> accountBalances = poloniexApiClient.getAccountBalancesByType("SPOT");}
     *  </pre>
     */
    public List<AccountBalance> getAccountBalancesByType(String accountType) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getAccountBalancesByType(accountType));
    }

    /**
     * Account Balances endpoint:
     *
     * @param accountId The account ID
     * @return get a list of all accounts of a user with each account’s id, type and balances (assets).
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<AccountBalance> accountBalances = poloniexApiClient.getAccountBalancesById(Long.parseLong(accounts.get(0).getAccountId()));}
     *  </pre>
     */
    public List<AccountBalance> getAccountBalancesById(Long accountId) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getAccountBalancesById(accountId));
    }

    /**
     * Accounts Transfer:
     * Transfer amount of currency from an account to another account for a user.
     *
     * @param accountsTransferRequest details for transfer
     * @return response with transferId
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code AccountsTransferResponse accountsTransferResponse = poloniexApiClient.accountsTransfer(AccountsTransferRequest.builder().currency("USDT").amount("10.5").fromAccount("SPOT").toAccount("FUTURES").build());}
     *  </pre>
     */
    public AccountsTransferResponse accountsTransfer(AccountsTransferRequest accountsTransferRequest) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.accountsTransfer(accountsTransferRequest));
    }

    /**
     * Accounts Transfer Records:
     * Get a list of transfer records of a user.
     *
     * @param limit The max number of records could be returned.
     * @param from it is 'transferId'. The query begin at ‘from', and the default is 0.
     * @param direction PRE, NEXT, default is NEXT
     * @param currency The transferred currency, like USDT. Default is for all currencies, if not specified.
     * @return a list of transfer records of a user.
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<AccountsTransferRecord> accountsTransferRecords = poloniexApiClient.getAccountsTransfers(null, null, null, null);}
     *  </pre>
     */
    public List<AccountsTransferRecord> getAccountsTransfers(Integer limit, Long from, String direction, String currency) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getAccountsTransfers(limit, from, direction, currency));
    }

    /**
     * Accounts Transfer Records by ID
     *
     * @param id transfer ID
     * @return transfer record
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code AccountsTransferRecord accountsTransferRecord = poloniexApiClient.getAccountsTransferById(123L);}
     *  </pre>
     */
    public AccountsTransferRecord getAccountsTransferById(Long id) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getAccountsTransferById(id));
    }

    /**
     * Account Activity:
     * Get a list of activities such as airdrop, rebates, staking, credit/debit adjustments, and other (historical adjustments).
     *
     * @param startTime (milliseconds since UNIX epoch) Trades filled before startTime will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) Trades filled after endTime will not be retrieved.
     * @param activityType Type of activity: ALL: 200, AIRDROP: 201, COMMISSION_REBATE: 202, STAKING: 203,
     *                                       REFERAL_REBATE: 204, CREDIT_ADJUSTMENT: 104, DEBIT_ADJUSTMENT: 105,
     *                                       OTHER: 199
     * @param limit The max number of records could be returned. Default is 100 and max is 1000.
     * @param from It is 'id'. The query begin at ‘from', and the default is 0.
     * @param direction PRE, NEXT, default is NEXT
     * @param currency The transferred currency, like USDT. Default is for all currencies, if not specified.
     * @return List of account activity
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<ActivityResponse> activityResponseList = poloniexApiClient.getAccountsActivity(1662005301209L, System.currentTimeMillis(), 200, 100, 0L, "NEXT", "");}
     *  </pre>
     */
    public List<ActivityResponse> getAccountsActivity(Long startTime, Long endTime, Integer activityType, Integer limit,
                                                      Long from, String direction, String currency) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        return execute(poloPrivateApiService.getAccountsActivity(startTime, endTime, activityType, limit, from,
                                                                 direction, currency));
    }

    /**
     * Fee Info:
     * Get fee rate for an account
     *
     * @return fee info
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code FeeInfo feeInfo = poloniexApiClient.getFeeInfo();}
     *  </pre>
     */
    public FeeInfo getFeeInfo() {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getFeeInfo());
    }

    // wallets

    /**
     * Deposit Addresses:
     * Get deposit addresses for a user
     *
     * @return map of deposit addresses with key being currency and value being address
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Map<String, String> depositAddresses = poloniexApiClient.getDepositAddresses();}
     *  </pre>
     */
    public Map<String, String> getDepositAddresses() {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getDepositAddressesByCurrency(null));
    }

    /**
     * Deposit Addresses:
     * Get deposit addresses for a user by currency
     *
     * @param currency the currency to display for the deposit address. If null is passed, the deposit addresses of all
     *                 currencies will be displayed.
     * @return map of deposit addresses with key being currency and value being address
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Map<String, String> depositAddresses = poloniexApiClient.getDepositAddressesByCurrency("USDT");}
     *  </pre>
     */
    public Map<String, String> getDepositAddressesByCurrency(String currency) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getDepositAddressesByCurrency(currency));
    }

    /**
     * Wallets Activity Records:
     * Get adjustment, deposit, and withdrawal activity history within a range window for a user
     *
     * @param start the start UNIX timestamp of activities
     * @param end the end UNIX timestamp of activities
     * @param activityType The type of activity: adjustments, deposits and withdrawals. If no activity type is specified, activities of all types will be returned.
     * @return wallet activity records
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code WalletsActivities walletsActivities = poloniexApiClient.getWalletsActivities(System.currentTimeMillis() - 10000000L, System.currentTimeMillis(), null);}
     *  </pre>
     */
    public WalletsActivities getWalletsActivities(Long start, Long end, String activityType) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getWalletsActivities(start, end, activityType));
    }

    /**
     * New Currency Address:
     * Create a new address for a currency
     *
     * @param newCurrencyAddressRequest request with details for creating a new address
     * @return new address
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code NewCurrencyAddressResponse newCurrencyAddressResponse = poloniexApiClient.addNewCurrencyAddress(NewCurrencyAddressRequest.builder().currency("USDT").build());}
     *  </pre>
     */
    public NewCurrencyAddressResponse addNewCurrencyAddress(NewCurrencyAddressRequest newCurrencyAddressRequest) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.addNewCurrencyAddress(newCurrencyAddressRequest));
    }

    /**
     * Withdraw Currency:
     * Immediately places a withdrawal for a given currency, with no email confirmation.
     *
     * @param withdrawCurrencyRequest request with details for withdrawal
     * @return withdrawal number
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code WithdrawCurrencyResponse withdrawCurrencyResponse = poloniexApiClient.withdrawCurrency(WithdrawCurrencyRequest.builder().currency("XRP").amount("1.50").address("0xbb8d").paymentID("123").build());}
     *  </pre>
     */
    public WithdrawCurrencyResponse withdrawCurrency(WithdrawCurrencyRequest withdrawCurrencyRequest) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.withdrawCurrency(withdrawCurrencyRequest));
    }

    // orders

    /**
     * Create Order:
     * Create an order for an account.
     *
     * @param symbol symbol name
     * @param side BUY, SELL
     * @param timeInForce GTC, IOC, FOK (Default: GTC)
     * @param type MARKET, LIMIT, LIMIT_MAKER (Default: MARKET)
     * @param accountType SPOT is the default and only supported one.
     * @param price Price is required for non-market orders
     * @param quantity Quantity is required for MARKET type and SELL side.
     * @param amount Amount is required for MARKET and BUY side.
     * @param clientOrderId client order id with a maximum 64-character length
     * @return order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Order order = poloniexApiClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT", "20640", "1", "", "T_D_UP_" + System.currentTimeMillis());}
     *  </pre>
     */
    public Order placeOrder(String symbol, String side, String timeInForce, String type, String accountType,
                            String price, String quantity, String amount, String clientOrderId) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        final OrderRequest request = OrderRequest.builder()
                .symbol(symbol)
                .side(side)
                .timeInForce(timeInForce)
                .type(type)
                .accountType(accountType)
                .price(price)
                .quantity(quantity)
                .amount(amount)
                .clientOrderId(clientOrderId)
                .build();
        return execute(poloPrivateApiService.placeOrder(request));
    }


    /**
     * Create Smart Order:
     * Create a smart order for an account
     *
     * @param symbol symbol name
     * @param side BUY, SELL
     * @param timeInForce GTC, IOC, FOK (Default: GTC)
     * @param type MARKET, LIMIT, LIMIT_MAKER (Default: MARKET)
     * @param accountType SPOT is the default and only supported one.
     * @param price Price is required for non-market orders
     * @param quantity Quantity is required for MARKET type and SELL side.
     * @param amount Amount is required for MARKET and BUY side.
     * @param clientOrderId client order id with a maximum 64-character length
     * @param stopPrice Price at which order is triggered
     * @return smart order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code SmartOrder smartOrder = poloniexApiClient.placeSmartOrder("ETH_USDT", "BUY", "0.1", "STOP_LIMIT", "SPOT", "FOK", "3900", "100", "T_D_UP_" + System.currentTimeMillis(), "4000");}
     *  </pre>
     */
    public SmartOrder placeSmartOrder(String symbol, String side, String timeInForce, String type, String accountType,
                                      String price, String quantity, String amount, String clientOrderId, String stopPrice) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        final SmartOrderRequest request = SmartOrderRequest.builder()
                .symbol(symbol)
                .side(side)
                .timeInForce(timeInForce)
                .type(type)
                .accountType(accountType)
                .price(price)
                .quantity(quantity)
                .amount(amount)
                .clientOrderId(clientOrderId)
                .stopPrice(stopPrice)
                .build();
        return execute(poloPrivateApiService.placeSmartOrder(request));
    }

    /**
     * Order Details:
     * Get an order’s status by specifying orderId
     *
     * @param id order id
     * @return order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Order order = poloniexApiClient.getOrderByOrderId(order1.getId());}
     *  </pre>
     */
    public Order getOrderByOrderId(String id) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getOrderByOrderId(id));
    }

    /**
     * Order Details:
     * Get an order’s status by specifying clientOrderId
     *
     * @param cid user specified order id
     * @return order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code Order order = poloniexApiClient.getOrderByClientOrderId(order2.getClientOrderId());}
     *  </pre>
     */
    public Order getOrderByClientOrderId(String cid) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getOrderByClientOrderId(cid));
    }

    /**
     * Open Orders:
     * Get a list of active orders for an account
     *
     * @param symbol The symbol to trade,like BTC_USDT. Default is for all symbols if null is passed.
     * @param side BUY, SELL
     * @param from it is 'orderId'. The query begin at ‘from', and it is 0 when you first query.
     * @param direction PRE, NEXT
     * @param limit The max number of orders could be returned.
     * @return list of orders
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Order> orders = poloniexApiClient.getOrders("ETH_USDT", "BUY", null, "NEXT", 10);}
     *  </pre>
     */
    public List<Order> getOrders(String symbol, String side, Long from, String direction, Integer limit) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getOrders(symbol, side, from, direction, limit));
    }

    /**
     * Order Details:
     * Get a smart order’s status. If smart order’s state is TRIGGERED, the response will include the triggered order’s data
     *
     * @param id smart order's id
     * @return smart order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<SmartOrder> smartOrders = poloniexApiClient.getSmartOrderByOrderId(String.valueOf(smartOrder1.getId()));}
     *  </pre>
     */
    public List<SmartOrder> getSmartOrderByOrderId(String id) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getSmartOrderByOrderId(id));
    }

    /**
     * Order Details:
     * Get a smart order’s status. If smart order’s state is TRIGGERED, the response will include the triggered order’s data
     *
     * @param cid smart order's user specified id
     * @return smart order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<SmartOrder> smartOrders = poloniexApiClient.getSmartOrderByClientOrderId(smartOrder2.getClientOrderId());}
     *  </pre>
     */
    public List<SmartOrder> getSmartOrderByClientOrderId(String cid) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getSmartOrderByClientOrderId(cid));
    }

    /**
     * Open Orders:
     * Get a list of (pending) smart orders for an account
     *
     * @param limit The max number of smart orders could be returned.
     * @return list of smart order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<SmartOrder> smartOrders = poloniexApiClient.getSmartOrders(10);}
     *  </pre>
     */
    public List<SmartOrder> getSmartOrders(Integer limit) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getSmartOrders(limit));
    }

    /**
     * Cancel Order by Id:
     * Cancel an active order by orderId
     *
     * @param id order id
     * @return order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code CanceledOrder canceledOrder = poloniexApiClient.cancelOrderByOrderId(order1.getId());}
     *  </pre>
     */
    public CanceledOrder cancelOrderByOrderId(String id) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.cancelOrderByOrderId(id));
    }

    /**
     * Cancel Order by Id:
     * Cancel an active order by clientOrderId.
     *
     * @param cid user specified order id
     * @return order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code CanceledOrder canceledOrder = poloniexApiClient.cancelOrderByClientOrderId(order2.getClientOrderId());}
     *  </pre>
     */
    public CanceledOrder cancelOrderByClientOrderId(String cid) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.cancelOrderByClientOrderId(cid));
    }

    /**
     * Cancel Multiple Orders by Ids:
     * Batch cancel one or many active orders in an account by IDs.
     *
     * @param orderIds orderIds to be cancelled
     * @param clientOrderIds clientOrderIds to be cancelled
     * @return list of order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<CanceledOrder> canceledOrders = poloniexApiClient.cancelOrderByIds(new String[]{order3.getId()},new String[]{order4.getClientOrderId()});}
     *  </pre>
     */
    public List<CanceledOrder> cancelOrderByIds(String[] orderIds, String[] clientOrderIds) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        final CancelOrdersByIdRequest request = CancelOrdersByIdRequest.builder()
                .orderIds(orderIds)
                .clientOrderIds(clientOrderIds)
                .build();
        return execute(poloPrivateApiService.cancelOrderByIds(request));
    }

    /**
     * Cancel All Orders:
     * Batch cancel all orders in an account.
     *
     * @param symbols If symbols are specified then all orders with those symbols will be canceled. If symbols are not
     *                specified or array is empty, it will cancel user's all orders for all symbols.
     * @param accountTypes SPOT is the default and only supported one.
     * @return list of order details
     *<br><br>
     *  <b>Example:</b>
     *  <pre>
     *  {@code List<CanceledOrder> canceledOrders = poloniexApiClient.cancelAllOrders(new String[]{"TRX_USDT"}, new String[]{"SPOT"});}
     *  </pre>
     */
    public List<CanceledOrder> cancelAllOrders(String[] symbols, String[] accountTypes) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        final CancelAllOrdersRequest request = CancelAllOrdersRequest.builder()
                .symbols(symbols)
                .accountTypes(accountTypes)
                .build();
        return execute(poloPrivateApiService.cancelAllOrders(request));
    }

    /**
     * Cancel Order by Id:
     * Cancel a smart order by its id.
     *
     * @param id smart order's id
     * @return smart order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code CanceledOrder canceledOrder = poloniexApiClient.cancelSmartOrderByOrderId(String.valueOf(smartOrder1.getId()));}
     *  </pre>
     */
    public CanceledOrder cancelSmartOrderByOrderId(String id) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.cancelSmartOrderById(id));
    }

    /**
     * Cancel Order by Id:
     * Cancel a smart order by its clientOrderId.
     *
     * @param cid smart order's clientOrderId
     * @return smart order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code CanceledOrder canceledOrder = poloniexApiClient.cancelSmartOrderByClientOrderId(smartOrder2.getClientOrderId());}
     *  </pre>
     */
    public CanceledOrder cancelSmartOrderByClientOrderId(String cid) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.cancelSmartOrderByClientOrderId(cid));
    }

    /**
     * Cancel Multiple Orders by Id:
     * Batch cancel one or many smart orders in an account by IDs.
     *
     * @param orderIds orderIds to be cancelled
     * @param clientOrderIds clientOrderIds to be cancelled
     * @return list of canceled order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<CanceledOrder> canceledOrders = poloniexApiClient.cancelSmartOrderByIds(new String[]{String.valueOf(smartOrder3.getId())},new String[]{smartOrder4.getClientOrderId()});}
     *  </pre>
     */
    public List<CanceledOrder> cancelSmartOrderByIds(String[] orderIds, String[] clientOrderIds) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        final CancelOrdersByIdRequest request = CancelOrdersByIdRequest.builder()
                .orderIds(orderIds)
                .clientOrderIds(clientOrderIds)
                .build();
        return execute(poloPrivateApiService.cancelSmartOrderByIds(request));
    }

    /**
     * Cancel All Orders:
     * Batch cancel all smart orders in an account
     *
     * @param symbols If symbols are specified then all smart orders with those symbols will be canceled. If symbols are
     *                not specified or array is empty, it will cancel user's all smart orders for all symbols.
     * @param accountTypes SPOT is the default and only supported one.
     * @return list of canceled order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<CanceledOrder> canceledOrders = poloniexApiClient.cancelAllSmartOrders(new String[]{"TRX_USDT"}, new String[]{"SPOT"});}
     *  </pre>
     */
    public List<CanceledOrder> cancelAllSmartOrders(String[] symbols, String[] accountTypes) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        final CancelAllOrdersRequest request = CancelAllOrdersRequest.builder()
                .symbols(symbols)
                .accountTypes(accountTypes)
                .build();
        return execute(poloPrivateApiService.cancelAllSmartOrders(request));
    }

    /**
     * Cancel All Orders:
     * Batch cancel all smart orders in an account
     *
     * @return list of canceled order details
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<CanceledOrder> canceledOrders = poloniexApiClient.cancelAllSmartOrders(new String[]{"TRX_USDT"}, new String[]{"SPOT"});}
     *  </pre>
     */
    public List<CanceledOrder> cancelAllSmartOrders() {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        return execute(poloPrivateApiService.cancelAllSmartOrders(CancelAllOrdersRequest.builder().build()));
    }

    /**
     * Orders History:
     * Get a list of historical orders in an account
     *
     * @param accountType SPOT is the default and only supported one.
     * @param type MARKET, LIMIT, LIMIT_MAKER (Default: all types)
     * @param side BUY, SELL (Default: both sides)
     * @param symbol Any supported symbol (Default: all symbols)
     * @param from An 'orderId'. The query begins at ‘from'.
     * @param direction PRE, NEXT The direction before or after ‘from'.
     * @param states FAILED, FILLED, CANCELED. PARTIALLY_CANCELED Multiple states can be specified and separated with comma. (Default: all states)
     * @param limit The max number of orders could be returned. (Default: 100)
     * @param hideCancel true or false. Whether canceled orders should not be retrieved. (Default: false)
     * @param startTime (milliseconds since UNIX epoch) orders updated before startTime will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) orders updated after endTime will not be retrieved.
     * @return list of orders
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Order> orders = poloniexApiClient.getOrderHistory("SPOT", "LIMIT", "BUY", "ETH_USDT", 58067963198046208L, "PRE", "CANCELED", 100, false, null, null);}
     *  </pre>
     */
    public List<Order> getOrderHistory(String accountType, String type, String side, String symbol, Long from,
                                       String direction, String states, Integer limit, Boolean hideCancel,
                                       Long startTime, Long endTime) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        return execute(poloPrivateApiService.getOrderHistory(accountType, type, side, symbol, from, direction, states, limit, hideCancel, startTime, endTime));
    }

    /**
     * Smart Orders History
     * Get a list of historical smart orders in an account
     *
     * @param accountType SPOT is the default and only supported one.
     * @param type STOP, STOP_LIMIT (Default: all types)
     * @param side BUY, SELL (Default: both sides)
     * @param symbol Any supported symbol (Default: all symbols)
     * @param from An 'smart orderId'. The query begins at ‘from'.
     * @param direction PRE, NEXT The direction before or after ‘from'.
     * @param states FAILED, FILLED, CANCELED. PARTIALLY_CANCELED. Multiple states can be specified and separated with comma. (Default: all states)
     * @param limit The max number of smart orders could be returned. (Default: 100)
     * @param hideCancel true or false. Whether canceled smart orders should not be retrieved. (Default: false)
     * @param startTime (milliseconds since UNIX epoch) smart orders updated before startTime will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) smart orders updated after endTime will not be retrieved.
     * @return list of smart orders
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<SmartOrder> smartOrders = poloniexApiClient.getSmartOrderHistory("SPOT", "STOP_LIMIT", "BUY", "ETH_USDT", null, null, "CANCELED", 1, false, null, null);}
     *  </pre>
     */
    public List<SmartOrder> getSmartOrderHistory(String accountType, String type, String side, String symbol, Long from,
                                                 String direction, String states, Integer limit, Boolean hideCancel,
                                                 Long startTime, Long endTime) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        return execute(poloPrivateApiService.getSmartOrderHistory(accountType, type, side, symbol, from, direction, states, limit, hideCancel, startTime, endTime));
    }

    // kill switch
    /**
     * Set Kill Switch
     * Set a timer that cancels all regular and smartorders after the timeout has expired. Timeout can be reset by
     * calling this command again with a new timeout value. A timeout value of -1 disables the timer. Timeout is defined
     * in seconds.
     *
     * @param timeout Timer value in seconds; range is -1 and 10 to 600.
     * @return kill switch response object
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code KillSwitchResponse killSwitchResponse = poloniexApiClient.setKillSwitch("15");}
     *  </pre>
     */
    public KillSwitchResponse setKillSwitch(String timeout){
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        final KillSwitchRequest request = KillSwitchRequest.builder()
                .timeout(timeout)
                .build();
        return execute(poloPrivateApiService.setKillSwitch(request));
    }

    /**
     * Get Kill Switch
     * Get status of kill switch. If there is an active kill switch then the start and cancellation time is returned.
     * If no active kill switch then an error message with code is returned.
     *
     * @return kill switch response object
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code KillSwitchResponse killSwitchResponse = poloniexApiClient.getKillSwitch();}
     *  </pre>
     */
    public KillSwitchResponse getKillSwitch() {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }

        return execute(poloPrivateApiService.getKillSwitch());
    }

    // trades

    /**
     * Trade History:
     * Get a list of all trades for an account.
     *
     * @param limit default and max value is 100.
     * @param endTime (milliseconds since UNIX epoch) trades filled after endTime will not be retrieved.
     * @param startTime (milliseconds since UNIX epoch) trades filled before startTime will not be retrieved.
     * @param from A 'trade Id'. The query begins at ‘from'.
     * @param direction PRE, NEXT The direction before or after ‘from'.
     * @return list of trades
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Trade>> trades = poloniexApiClient.getTrades(10, 1655016096000L, 1655929390000L, 1000L, "NEXT");}
     *  </pre>
     */
    public List<Trade> getTrades(Integer limit, Long endTime, Long startTime, Long from, String direction) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getTrades(limit, endTime, startTime, from, direction));
    }

    /**
     * Trades by Order Id:
     * Get a list of all trades for an order specified by its orderId.
     *
     * @param orderId the associated order's id (order's clientOrderId is not supported)
     * @return list of trades
     * <dt><b>Example:</b></dt>
     *  <pre>
     *  {@code List<Trade>> trades = poloniexApiClient.getUserTradesByOrderId(62759197997072384L);}
     *  </pre>
     */
    public List<Trade> getUserTradesByOrderId(Long orderId) {
        if (isNull(poloPrivateApiService)) {
            throw new PoloApiException(AUTHENTICATION_ERROR_MESSAGE);
        }
        return execute(poloPrivateApiService.getUserTradesByOrderId(orderId));
    }



    public static <T> T execute(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new PoloApiException(nonNull(response.errorBody()) ? response.errorBody().string() : response.toString());
            }
        } catch (IOException e) {
            throw new PoloApiException(e.getMessage(), e);
        }
    }

}
