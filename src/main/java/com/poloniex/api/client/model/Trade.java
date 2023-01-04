package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Account Trade
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {

    /**
     * trade id
     */
    private String id;

    /**
     * The trading symbol, like BTC_USDT
     */
    private String symbol;

    /**
     * SPOT
     */
    private String accountType;

    /**
     * the associated order's id
     */
    private String orderId;

    /**
     * order's side: BUY, SELL
     */
    private String side;

    /**
     * order's type: LIMIT, MARKET, LIMIT_MAKER
     */
    private String type;

    /**
     * MAKER, TAKER
     */
    private String matchRole;

    /**
     * trade create time
     */
    private Long createTime;

    /**
     * price for the trade
     */
    private String price;

    /**
     * base units for the trade
     */
    private String quantity;

    /**
     * quote units for the trade
     */
    private String amount;

    /**
     * fee currency name
     */
    private String feeCurrency;

    /**
     * fee amount
     */
    private String feeAmount;

    /**
     * A globally unique trade Id that can be used as query param in 'from' field
     */
    private String pageId;

    /**
     * order's clientOrderId
     */
    private String clientOrderId;
}