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
public class CloseAtMarketPriceRequest {
    @NonNull
    private String symbol;   // 必需，表示交易对
    private String clOrdId;  // 可选，客户端指定的订单ID
}
