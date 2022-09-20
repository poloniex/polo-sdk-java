package com.poloniex.api.client.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * New currency address request
 */
@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NewCurrencyAddressRequest {
    /**
     * the currency to use for the deposit address
     */
    private String currency;
}
