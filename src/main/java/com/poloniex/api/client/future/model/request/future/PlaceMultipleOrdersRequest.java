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
public class PlaceMultipleOrdersRequest {
    @NonNull
    private String symbol;
    @NonNull
    private String side;
    @NonNull
    private String type;
    private String clOrdId; // 可选
    private String px; // 可选
    @NonNull
    private String sz;
    private Boolean reduceOnly; // 可选
    private String timeInForce; // 可选，默认值GTC
    private String stpMode; // 可选，默认值NONE
}
