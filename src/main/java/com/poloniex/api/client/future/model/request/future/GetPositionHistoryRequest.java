package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetPositionHistoryRequest {
    private String symbol;
    private String mgnMode; // Margin Mode: ISOLATED, CROSS
    private String sTime; // Starting time, Unix timestamp in milliseconds
    private String eTime; // Ending time, Unix timestamp in milliseconds
    private Long from = 0L; // Default starting ID for the query
    private Integer limit = 10; // Default pagination size
    private String direct = "NEXT"; // Default pagination direction

}
