package com.poloniex.api.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Accounts transfer response
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountsTransferResponse {
    /**
     * transfer ID
     */
    private String transferId;
}
