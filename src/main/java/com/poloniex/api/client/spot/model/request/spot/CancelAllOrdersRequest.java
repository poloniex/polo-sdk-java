package com.poloniex.api.client.spot.model.request.spot;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Cancel all orders request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CancelAllOrdersRequest {

    /**
     * If symbols are specified then all orders with those symbols will be canceled. If symbols are not specified or
     * array is empty, it will cancel user's all orders for all symbols.
     */
    private String[] symbols;

    /**
     * SPOT is the default and only supported one.
     */
    private String[] accountTypes;
}
