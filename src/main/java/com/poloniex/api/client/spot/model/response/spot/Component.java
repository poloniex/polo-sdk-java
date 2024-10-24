package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Component of Mark Price
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Component {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * symbol price
     */
    private String symbolPrice;

    /**
     * weight assigned to the exchange price
     */
    private String weight;

    /**
     * symbol price converted to index
     */
    private String convertPrice;

    /**
     * name of exchange
     */
    private String exchange;
}
