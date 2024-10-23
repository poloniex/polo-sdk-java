package com.poloniex.api.client.spot.model.response.spot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Interest History
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestHistoryResponse {
    /** String: record ID */
    private String id;

    /** Long: Interest collection time */
    private Long interestAccuredTime;

    /** String: Asset name */
    private String currencyName;

    /** String: Principal */
    private String principal;

    /** String: Interest */
    private String interest;

    /** String: Interest rate */
    private String interestRate;
}
