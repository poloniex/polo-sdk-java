package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * Order Book
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderBook {

    /**
     * time the record was created
     */
    private Long time;

    /**
     * controls aggregation by price
     */
    private String scale;

    /**
     * list of asks
     */
    private List<String> asks;

    /**
     * list of bids
     */
    private List<String> bids;

    /**
     * time the record was pushed
     */
    private Long ts;
}
