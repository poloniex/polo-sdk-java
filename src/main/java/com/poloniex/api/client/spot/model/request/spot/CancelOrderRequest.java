package com.poloniex.api.client.spot.model.request.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Cancel Multiple Orders request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CancelOrderRequest {
    /**
     * List of order IDs.
     * This field is not present unless clientOrderIds is null or empty.
     */
    private List<String> orderIds;

    /**
     * List of client-provided order IDs.
     * This field is not present unless orderIds is null or empty.
     */
    private List<String> clientOrderIds;
}
