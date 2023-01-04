package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Order Details
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    /**
     * order id
     */
    private String id;

    /**
     * clientOrderId user specifies in request or an empty string.
     */
    private String clientOrderId;

    /**
     * The symbol to trade,like BTC_USDT
     */
    private String symbol;

    /**
     * order state: NEW,PARTIALLY_FILLED
     */
    private String state;

    /**
     * SPOT
     */
    private String accountType;

    /**
     * BUY, SELL
     */
    private String side;

    /**
     * MARKET, LIMIT, LIMIT_MAKER
     */
    private String type;

    /**
     * GTC, IOC, FOK
     */
    private String timeInForce;

    /**
     * base units for the order
     */
    private BigDecimal quantity;

    /**
     * price
     */
    private BigDecimal price;

    /**
     * avgPrice = filledAmunt/filledQuantity
     */
    private BigDecimal avgPrice;

    /**
     * quote units for the order
     */
    private BigDecimal amount;

    /**
     * filled quantity
     */
    private BigDecimal filledQuantity;

    /**
     * filled amount
     */
    private BigDecimal filledAmount;

    /**
     * API, APP, WEB
     */
    private String orderSource;

    /**
     * create time
     */
    private Long createTime;

    /**
     * update time
     */
    private Long updateTime;
}