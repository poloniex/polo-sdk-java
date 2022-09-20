package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Cancel multiple orders by id or clientOrderId request.
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CancelOrdersByIdRequest {

    /**
     * List of order id
     */
    private String[] orderIds;

    /**
     * List of clientOrderId
     */
    private String[] clientOrderIds;
}
