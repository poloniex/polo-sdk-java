package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetPremiumIndexK_lineDataRequest {
    private String symbol; // Required: A trading pair
    private String interval; // Required: Time interval for the candlestick
    private String sTime; // Optional: Starting time of the candlestick, Unix timestamp in milliseconds
    private String eTime; // Optional: Ending time of the candlestick, Unix timestamp in milliseconds
    private Integer limit; // Optional: Pagination size, defaults to 10, max limit of 100

}
