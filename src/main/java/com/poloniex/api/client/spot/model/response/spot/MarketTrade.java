package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Data Trade
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
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