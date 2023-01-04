package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Smart order
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmartOrder {

    /**
     * smart order id
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
     * order state
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
     * STOP, STOP_LIMIT
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
     * quote units for the order
     */
    private BigDecimal amount;

    /**
     * stop price
     */
    private BigDecimal stopPrice;

    /**
     * create time
     */
    private Long createTime;

    /**
     * update time
     */
    private Long updateTime;

    /**
     * The triggered order's data. This will only be displayed when the smart order's state is TRIGGERED.
     */
    private Order triggeredOrder;
}