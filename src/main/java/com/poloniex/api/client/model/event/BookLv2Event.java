package com.poloniex.api.client.model.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Book Level 2
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookLv2Event {

    /**
     * symbol name
     */
    private String symbol;

    /**
     * sell orders, in ascending order of price
     */
    private List<List<String>> asks;

    /**
     * buy orders, in descending order of price
     */
    private List<List<String>> bids;

    /**
     * time the record was created
     */
    private Long createTime;

    /**
     * the id of the previous message
     */
    private Long lastId;

    /**
     * id of the record (SeqId)
     */
    private Long id;

    /**
     * send timestamp
     */
    private Long ts;
}
