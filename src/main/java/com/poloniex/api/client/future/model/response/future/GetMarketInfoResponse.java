package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetMarketInfoResponse {

    private int code;
    private String msg;
    private List<GetMarketInfo> data=new ArrayList<>();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetMarketInfo{
    private String s; // A trading pair
    private String o; // Opening price
    private String l; // Lowest price in the past 24 hours
    private String h; // Highest price in the past 24 hours
    private String c; // Closing price
    private String qty; // Trading unit, the quantity of the base currency, or Cont for the number of contracts.
    private String amt; // Trading unit, the quantity of the quote currency.
    private String tC; // Trade counts
    private String sT; // Start time of the 24-hour interval
    private String cT; // End time of the 24-hour interval
    private String dC; // Daily price change in decimal
    private String bPx; // Best ask price
    private String bSz; // Quantity at the best ask price
    private String aPx; // Best bid price
    private String aSz; // Quantity at the best bid price
    private String mPx; // Mark price
}
}
