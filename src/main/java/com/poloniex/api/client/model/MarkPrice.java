package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Market Data Mark Price
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkPrice {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * current mark price
     */
    private String markPrice;

    /**
     * time the record was created
     */
    private Long time;
}
