package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Components of a Mark Price
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MarkPriceComponents {

    /**
     * current mark price
     */
    private String markPrice;

    /**
     * symbol name
     */
    private String symbol;

    /**
     * timestamp
     */
    private Long ts;

    /**
     * price and weight info for the symbol from various exchanges
     */
    private List<Component> components;
}
