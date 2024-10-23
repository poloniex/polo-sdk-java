package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HistoricalFundingRatesRequest {
    private String symbol; // Trading pair symbol
    private Long sT; // Starting time in Unix timestamp seconds
    private Long eT; // Ending time in Unix timestamp seconds
    private Integer limit; // Limit on the number of records
}
