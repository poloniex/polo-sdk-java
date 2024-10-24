package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundingRateEvent {

    private String s; // Trading pair
    private Long ts; // Timestamp of data generation (in milliseconds)
    private String fR; // Funding rate
    private Long fT; // Timestamp of the most recent funding rate settlement
    private String nFR; // Predicted funding rate
    private Long nFT; // Funding time for the next period (Unix timestamp in milliseconds)

}
