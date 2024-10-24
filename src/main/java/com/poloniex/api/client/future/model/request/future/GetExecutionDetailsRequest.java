package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetExecutionDetailsRequest {
    private String side; // Trade Side, BUY or SELL
    private String symbol; // A trading pair
    private String ordId; // Order ID
    private String clOrdId; // Client Order ID
    private String sTime; // Starting time, Unix timestamp in ms
    private String eTime; // Ending time, Unix timestamp in ms
    private Long from; // Query ID start
    private Integer limit; // Pagination size
    private String direct; // PREV, NEXT
}
