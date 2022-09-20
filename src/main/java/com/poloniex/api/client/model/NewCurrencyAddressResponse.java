package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * New currency address response
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NewCurrencyAddressResponse {
    /**
     * the newly created address
     */
    private String address;
}
