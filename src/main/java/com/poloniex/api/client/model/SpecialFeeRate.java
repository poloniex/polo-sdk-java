package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Custom fee rate for a symbol
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
