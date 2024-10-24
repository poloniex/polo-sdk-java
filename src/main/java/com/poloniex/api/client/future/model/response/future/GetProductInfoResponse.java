package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetProductInfoResponse {
    private int code;
    private String msg;
    private GetProductInfo data=new GetProductInfo();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetProductInfo {
        private String symbol; // A trading pair
        private String bAsset; // Underlying asset
        private String bCcy; // Base currency
        private String qCcy; // Quote currency
        private String visibleStartTime; // Visible start time
        private String tradableStartTime; // Tradable start time
        private String sCcy; // Settlement currency
        private String tSz; // Tick size
        private String pxScale; // Price precision
        private Integer lotSz; // Order size precision
        private Integer minSz; // Minimum order size
        private String ctVal; // Contract face value
        private String status; // Trading status
        private String oDate; // Listing date
        private String maxPx; // Maximum order price
        private String minPx; // Minimum order price
        private String maxQty; // Maximum size of an order
        private String minQty; // Minimum size of an order
        private String maxLever; // Maximum leverage
        private String lever; // Default leverage
        private String ctType; // Contract type
        private String alias; // Contract alias
        private String tFee; // Taker fee
        private String mFee; // Maker fee
        private String iM; // Initial margin rate
        private String mM; // Maintenance margin rate
        private String mR; // Maximum risk limit
    }
}
