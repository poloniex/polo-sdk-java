package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * Book event
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
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
