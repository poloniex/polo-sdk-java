package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCurrentFundingRateResponse {
    private int code;
    private String msg;
    private GetCurrentFundingRate data=new GetCurrentFundingRate();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetCurrentFundingRate {
    private String s; // Trading pair
    private String fR; // Current funding rate
    private Long fT; // Funding time for the current rate
    private String nFR; // Next (predicted) funding rate
    private Long nFT; // Forecasted funding time for the next period
}}
