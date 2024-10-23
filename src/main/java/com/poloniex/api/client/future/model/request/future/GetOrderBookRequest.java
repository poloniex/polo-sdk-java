package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetOrderBookRequest {
    private String symbol; // Mandatory: A trading pair
    private String scale; // Optional: Level of market depth
    private Integer limit; // Optional: Number of results per request
}
