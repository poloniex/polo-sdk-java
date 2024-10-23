package com.poloniex.api.client.future.common;

/**
 * Polo API Constants
 */
public class PoloApiConstants {

    public static final String HEADER_TIMESTAMP = "signTimestamp";
    public static final String HEADER_KEY = "key";
    public static final String HEADER_SIGNATURE = "signature";
    public static final String SIGNATURE_METHOD_VALUE = "HmacSHA256";
    public static final String REQUEST_BODY = "requestBody";

    public static final String EVENT_SUBSCRIBE = "subscribe";
    public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_CREATE_ORDER = "createOrder";
    public static final String EVENT_CANCEL_ORDERS = "cancelOrders";
    public static final String EVENT_CANCEL_ALL_ORDERS = "cancelAllOrders";


    public static final String CHANNEL_CANDLES_PREFIX = "candles";
    public static final String CHANNEL_TRADES = "trades";
    public static final String CHANNEL_TICKER = "ticker";
    public static final String CHANNEL_BOOK = "book";
    public static final String CHANNEL_BOOK_LV_2 = "book_lv2";
    public static final String CHANNEL_AUTH = "auth";
    public static final String CHANNEL_ORDERS = "orders";
    public static final String CHANNEL_BALANCES = "balances";
    public static final String SYMBOLS_ALL = "all";
    public static final String CHANNEL_SYMBOLS = "symbols";
    public static final String CHANNEL_CURRENCIES = "currencies";
    public static final String CHANNEL_EXCHANGE = "exchange";

    public static final String EVENT_PING = "{\"event\": \"ping\"}";
    public static final String EVENT_PONG = "{\"event\":\"pong\"}";

    public static final String SIGNATURE_PAYLOAD = "GET\n/ws\nsignTimestamp=";
    /*v3*/
    public static final String Account_Balance="/v3/account/balance";
    public static final String Place_Order="/v3/trade/order";
    public static final String Place_Multiple_Orders="/v3/trade/orders";
    public static final String Cancel_Order="/v3/trade/order";
    public static final String Cancel_Multiple_Orders="/v3/trade/batchOrders";
    public static final String Cancel_All_Orders="/v3/trade/allOrders";
    public static final String Close_At_Market_Price="/v3/trade/position";
    public static final String Close_All_At_Market_Price="/v3/trade/positionAll";
    public static final String Current_Orders="/v3/trade/order/opens";
    public static final String Execution_Details="/v3/trade/order/trades";
    public static final String Order_History="/v3/trade/order/history";

    public static final String Current_Position="/v3/trade/position/opens";
    public static final String Position_History="/v3/trade/position/history";
    public static final String Adjust_Margin="/v3/trade/position/margin";
    public static final String Switch_Cross="/v3/position/switchIsolated";
    public static final String Margin_Mode="/v3/position/marginType";
    public static final String Get_Leverage="/v3/position/leverage";
    public static final String Set_Leverage="/v3/position/leverage";

    public static final String Order_Book="/v3/market/orderBook";
    public static final String K_line_Data="/v3/market/candles";
    public static final String Execution_Info="/v3/market/trades";
    public static final String Market_Info="/v3/market/tickers";
    public static final String Index_Price="/v3/market/indexPrice";
    public static final String Index_Price_components="/v3/market/indexPriceComponents";
    public static final String Index_Price_K_line_Data="/v3/market/indexPriceCandlesticks";
    public static final String Premium_Index_K_line_Data="/v3/market/premiumIndexCandlesticks";
    public static final String Mark_Price="/v3/market/markPrice";
    public static final String Mark_Price_K_line_Data="/v3/market/markPriceCandlesticks";
    public static final String Product_Info="/v3/market/instruments";
    public static final String Current_Funding_Rate="/v3/market/fundingRate";
    public static final String Historical_Funding_Rates="/v3/market/fundingRate/history";
    public static final String Current_Open_Positions="/v3/market/openInterest";
    public static final String Query_Insurance="/v3/market/insurance";
    public static final String Futures_Risk_Limit="/v3/market/riskLimit";

    public static final String Channel_Product_Info_Symbol="symbol";
    public static final String Channel_Order_Book="book";
    public static final String Channel_Order_Book_V2="book_lv2";
    public static final String Channel_Trading_Info="trades";

    public static final String Channel_Tickers="tickers";
    public static final String Channel_Execution_Info="tickers";
    public static final String Channel_Index_Price="index_price";
    public static final String Channel_Mark_Price="mark_price";
    public static final String Channel_Funding_Rate="funding_rate";
    public static final String Channel_Positions="positions";
    public static final String Channel_Orders="orders";
    public static final String Channel_Trade="trade";
    public static final String Channel_Account_Change="account";

}