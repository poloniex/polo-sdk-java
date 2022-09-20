package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Timestamp
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Timestamp {
    /**
     * server time
     */
    private Long serverTime;
}
