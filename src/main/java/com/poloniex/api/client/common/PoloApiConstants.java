package com.poloniex.api.client.common;

/**
 * Polo API Constants
 */
public class PoloApiConstants {

    public static final String TIMESTAMP = "/timestamp";
    public static final String MARKETS_PRICE = "/markets/{symbol}/price";
    public static final String MARKETS_PRICE_ALL = "/markets/price";
    public static final String MARKETS_ORDERBOOK = "/markets/{symbol}/orderBook";
    public static final String MARKETS_CANDLES = "/markets/{symbol}/candles";
    public static final String MARKETS_TRADES = "/markets/{symbol}/trades";
    public static final String MARKETS_TICKER24H = "/markets/{symbol}/ticker24h";
    public static final String MARKETS_TICKER24H_ALL = "/markets/ticker24h";
    public static final String MARKETS = "/markets/";
    public static final String MARKET = "/markets/{symbol}";
    public static final String CURRENCIES = "/currencies/";
    public static final String CURRENCY = "/currencies/{currency}";

    public static final String ACCOUNTS = "/accounts/";
    public static final String ACCOUNTS_TYPE_BALANCES = "/accounts/balances";
    public static final String ACCOUNTS_ID_BALANCES = "/accounts/{account_id}/balances";
    public static final String ACCOUNTS_TRANSFER = "/accounts/transfer";
    public static final String ACCOUNTS_TRANSFER_ID = "/accounts/transfer/{id}";
    public static final String ACCOUNTS_ACTIVITY = "/accounts/activity";
    public static final String FEE_INFO = "/feeinfo/";

    public static final String WALLETS_ADDRESSES = "/wallets/addresses";
    public static final String WALLETS_ACTIVITY = "/wallets/activity";
    public static final String WALLETS_ADDRESS = "/wallets/address";
    public static final String WALLETS_WITHDRAW = "/wallets/withdraw";

    public static final String ORDERS = "/orders";
    public static final String ORDERS_BY_ID = "/orders/{id}";
    public static final String ORDERS_BY_CID = "/orders/cid:{client_order_id}";
    public static final String ORDERS_CANCEL_BY_IDS = "/orders/cancelByIds";
    public static final String ORDERS_HISTORY = "/orders/history";

    public static final String KILL_SWITCH_POST = "/orders/killSwitch";
    public static final String KILL_SWITCH_GET = "/orders/killSwitchStatus";

    public static final String SMARTORDERS = "/smartorders";
    public static final String SMARTORDERS_BY_ID = "/smartorders/{id}";
    public static final String SMARTORDERS_BY_CID = "/smartorders/cid:{client_order_id}";
    public static final String SMARTORDERS_CANCEL_BY_IDS = "/smartorders/cancelByIds";
    public static final String SMARTORDERS_HISTORY = "/smartorders/history";

    public static final String TRADES = "/trades";
    public static final String TRADES_BY_ID = "/orders/{orderId}/trades";

    public static final String HEADER_TIMESTAMP = "signTimestamp";
    public static final String HEADER_KEY = "key";
    public static final String HEADER_SIGNATURE = "signature";
    public static final String SIGNATURE_METHOD_VALUE = "HmacSHA256";
    public static final String REQUEST_BODY = "requestBody";

    public static final String EVENT_SUBSCRIBE = "subscribe";
    public static final String EVENT_UNSUBSCRIBE = "unsubscribe";

    public static final String SIGNATURE_PAYLOAD = "GET\n/ws\nsignTimestamp=";
    public static final String CHANNEL_CANDLES_PREFIX = "candles";
    public static final String CHANNEL_TRADES = "trades";
    public static final String CHANNEL_TICKER = "ticker";
    public static final String CHANNEL_BOOK = "book";
    public static final String CHANNEL_BOOK_LV_2 = "book_lv2";
    public static final String CHANNEL_AUTH = "auth";
    public static final String CHANNEL_ORDERS = "orders";
    public static final String CHANNEL_BALANCES = "balances";
    public static final String SYMBOLS_ALL = "all";

    public static final String EVENT_PING = "{\"event\": \"ping\"}";
    public static final String EVENT_PONG = "{\"event\":\"pong\"}";

}