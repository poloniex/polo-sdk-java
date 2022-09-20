package com.poloniex.api.client.model.event;

import lombok.Data;

import java.util.List;

/**
 * Book event
 */
@Data
public class BookEvent {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * time the record was created
     */
    private Long createTime;

    /**
     * sell orders, in ascending order of price
     */
    private List<List<String>> asks;

    /**
     * buy orders, in descending order of price
     */
    private List<List<String>> bids;

    /**
     * id of the record (SeqId)
     */
    private Long id;

    /**
     * send timestamp
     */
    private Long ts;
}
