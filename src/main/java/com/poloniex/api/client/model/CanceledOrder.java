package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Canceled order
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CanceledOrder {

    /**
     * the order id
     */
    private String orderId;

    /**
     * clientOrderId of the order
     */
    private String clientOrderId;

    /**
     * order's state (PENDING_CANCEL)
     */
    private String state;

    /**
     * response code
     */
    private Integer code;

    /**
     * response message
     */
    private String message;
}