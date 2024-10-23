package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetOrderHistoryRequest {
    private String symbol;
    private String side;
    private String ordId;
    private String clOrdId;
    private String state;
    private String type;
    private String sTime;
    private String eTime;
    private Long from;
    private Integer limit;
    private String direct;
}
