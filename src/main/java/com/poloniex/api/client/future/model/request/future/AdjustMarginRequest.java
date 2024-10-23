package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AdjustMarginRequest {
    @NonNull
    private String symbol; // A trading pair, e.g., BTC/USD
    @NonNull
    private String amt;    // Margin amount
    @NonNull
    private String type;   // Operation type: ADD or REDUCE

}
