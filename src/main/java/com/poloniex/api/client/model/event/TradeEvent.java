package com.poloniex.api.client.model.event;

import lombok.Data;

/**
 * Trade event
 */
@Data
public class TradeEvent {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * quote units traded
     */
    private String amount;

    /**
     * trade side (buy, sell)
     */
    private String takerSide;

    /**
     * base units traded
     */
    private String quantity;

    /**
     * time the trade was created
     */
    private Long createTime;

    /**
     * trade price
     */
    private String price;

    /**
     * trade id
     */
    private Long id;

    /**
     * time the record was pushed
     */
    private Long ts;
}
