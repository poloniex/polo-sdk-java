package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetFuturesRiskLimitResponse {

    private int code;
    private String msg;
    private List<GetFuturesRiskLimit> data=new ArrayList<>();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetFuturesRiskLimit {
        private String symbol;
        private String notionalCap;
    }
}
