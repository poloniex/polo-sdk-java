package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Data Price
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * symbol name
     */
    private String price;

    /**
     * symbol name
     */
    private Long time;

    /**
     * symbol name
     */
    private String dailyChange;

    /**
     * symbol name
     */
    private Long ts;
}
