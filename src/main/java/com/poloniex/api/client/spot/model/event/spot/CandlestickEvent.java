package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Candlestick event
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CandlestickEvent {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * quote units traded over the interval
     */
    private String amount;

    /**
     * highest price over the interval
     */
    private String high;

    /**
     * base units traded over the interval
     */
    private String quantity;

    /**
     * count of trades
     */
    private Integer tradeCount;

    /**
     * lowest price over the interval
     */
    private String low;

    /**
     * close time of interval
     */
    private Long closeTime;

    /**
     * start time of interval
     */
    private Long startTime;

    /**
     * price at the end time
     */
    private String close;

    /**
     * price at the start time
     */
    private String open;

    /**
     * time the record was pushed
     */
    private Long ts;
}
