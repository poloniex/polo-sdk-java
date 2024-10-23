package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetK_lineDataRequest {
    private String symbol; // Trading pair
    private String interval; // Data interval
    private Integer limit; // Number of results per request
    private Long startTime; // Start time (optional)
    private Long endTime; // End time (optional)
}
