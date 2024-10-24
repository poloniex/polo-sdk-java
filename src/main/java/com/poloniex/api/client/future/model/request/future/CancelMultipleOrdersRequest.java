package com.poloniex.api.client.future.model.request.future;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CancelMultipleOrdersRequest {
    private String symbol;   // 必需，表示交易对
    private List<String> ordIds;    // 可选，表示要取消的订单ID
    private List<String> clOrdIds;  // 可选，客户端指定的订单ID
}
