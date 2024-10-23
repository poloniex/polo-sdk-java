package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetExecutionInfoResponse {
    private int code;
    private String msg;
    private List<ExecutionInfo> data=new ArrayList<>();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExecutionInfo{
        private String id; // Trade ID
        private String px; // Execution price
        private String qty; // Trading unit, the quantity of the base currency, or Cont for the number of contracts.
        private String amt; // Trading unit, the quantity of the quote currency.
        private String side; // Trade side, Enumerate: BUY, SELL
        private String cT; // Timestamp of execution time
    }


}
