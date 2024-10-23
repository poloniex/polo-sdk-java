package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HistoricalFundingRatesResponse {
    private int code;
    private String msg;
    private List<HistoricalFundingRates> data=new ArrayList<>();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HistoricalFundingRates {
        private String s; // A trading pair, consisting of two currencies: base currency and quote currency
        private String fR; // Current funding rate. When positive, longs pay shorts; when negative, shorts pay longs.
        private Long fT; // The current funding rate is settled in UTC time
    }
}
