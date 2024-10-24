package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetIndexPriceK_lineDataRequest {
    private String symbol; // A trading pair
    private String interval; // Interval
    private String sTime; // Starting time of the candlestick, Unix timestamp format in milliseconds
    private String eTime; // Ending time of the candlestick, Unix timestamp format in milliseconds
    private Integer limit; // Pagination size
}
