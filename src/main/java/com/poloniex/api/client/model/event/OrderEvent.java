package com.poloniex.api.client.model.event;

import lombok.Data;

/**
 * ORder event
 */
@Data
public class OrderEvent {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * MARKET, LIMIT, LIMIT_MAKER
     */
    private String type;

    /**
     * number of base units for this order
     */
    private String quantity;

    /**
     * order id
     */
    private String orderId;

    /**
     * fee amount for the trade
     */
    private String tradeFee;

    /**
     * user specified id
     */
    private String clientOrderId;

    /**
     * SPOT
     */
    private String accountType;

    /**
     * fee currency name
     */
    private String feeCurrency;

    /**
     * place, trade, canceled
     */
    private String eventType;

    /**
     * WEB, APP, API
     */
    private String source;

    /**
     * BUY, SELL
     */
    private String side;

    /**
     * base units filled in this order
     */
    private String filledQuantity;

    /**
     * quote units filled in this order
     */
    private String filledAmount;

    /**
     * MAKER, TAKER
     */
    private String matchRole;

    /**
     * NEW, PARTIALLY_FILLED, FILLED, PENDING_CANCEL, PARTIALLY_CANCELED, CANCELED, FAILED
     */
    private String state;

    /**
     * time the trade was executed
     */
    private Long tradeTime;

    /**
     * number of quote units for a trade
     */
    private String tradeAmount;

    /**
     * number of quote units for this order
     */
    private String orderAmount;

    /**
     * time the record was created
     */
    private Long createTime;

    /**
     * set price of the order
     */
    private String price;

    /**
     * number of base units for a trade
     */
    private String tradeQty;

    /**
     * price of the trade
     */
    private String tradePrice;

    /**
     * id of the trade
     */
    private String tradeId;

    /**
     * time the record was pushed
     */
    private Long ts;
}
