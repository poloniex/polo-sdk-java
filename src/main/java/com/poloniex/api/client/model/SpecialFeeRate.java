package com.poloniex.api.client.model;

import lombok.Data;

/**
 * Custom fee rate for a symbol
 */
@Data
public class SpecialFeeRate {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * maker rate
     */
    private String makerRate;

    /**
     * taker rate
     */
    private String takerRate;
}
