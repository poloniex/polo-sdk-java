package com.poloniex.api.client.spot.model.event.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Create Order
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateOrderEvent {
    /**
     * Order ID returned by the server
     */
    private String orderId;

    /**
     * Client-provided order ID specified in the request, or an empty string if not provided
     */
    private String clientOrderId;

    /**
     * Message associated with the response
     */
    private String message;

    /**
     * Status code of the response:
     * - 200 indicates success (OK)
     * - Any other value indicates an error occurred
     */
    private int code;
}
