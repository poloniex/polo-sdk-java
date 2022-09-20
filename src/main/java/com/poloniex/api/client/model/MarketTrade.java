package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Data Trade
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MarketTrade {

    /**
     * trade id
     */
    private String id;

    /**
     * trade price
     */
    private String price;

    /**
     * base units traded
     */
    private String quantity;

    /**
     * quote units traded
     */
    private String amount;

    /**
     * taker's trade side (BUY, SELL)
     */
    private String takerSide;

    /**
     * time the trade was pushed
     */
    private Long ts;

    /**
     * time the trade was created
     */
    private Long createTime;
}