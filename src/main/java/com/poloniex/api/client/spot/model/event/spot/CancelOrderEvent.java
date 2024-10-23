package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Cancel Orders
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CancelOrderEvent {
    /**
     * The order ID returned by the server.
     */
    private String orderId;

    /**
     * The client-specified order ID associated with the order.
     */
    private String clientOrderId;

    /**
     * The response code.
     * - 200 indicates that the operation was successful (OK).
     */
    private Integer code;

    /**
     * The response message associated with the operation.
     */
    private String message;
}
