package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetMarkPriceK_LineRequest {

    private String symbol; // The trading pair, e.g., "BTCUSD"
    private String interval; // Enum for the interval
    private String sTime; // Optional starting time in milliseconds
    private String eTime; // Optional ending time in milliseconds
    private Integer limit; // Optional limit on the number of results
}
