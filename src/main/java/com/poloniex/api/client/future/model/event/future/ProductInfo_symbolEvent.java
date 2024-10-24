package com.poloniex.api.client.future.model.event.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductInfo_symbolEvent {
    private String symbol;            // Trading pair
    private String visibleStartTime;  // Visible startTime
    private String tradableStartTime; // Tradable startTime
    private String pxScale;           // Price precision
    private Integer lotSz;            // Order size precision
    private Integer minSz;            // Minimum order size
    private String ctVal;             // Contract face value
    private String status;            // Trading status
    private String maxPx;             // Maximum price of an order
    private String minPx;             // Minimum price of an order
    private String maxQty;            // Maximum size of an order
    private String minQty;            // Minimum size of an order
    private String maxLever;          // Maximum leverage
    private String lever;              // Default leverage
    private String ctType;            // Contract type
    private String alias;             // Alias for contract
    private String bAsset;            // Underlying asset
    private String bCcy;              // Base currency
    private String qCcy;              // Quote currency
    private String sCcy;              // Settlement currency
    private String tSz;               // Tick size
    private String oDate;             // Listing date
    private String iM;                // Initial margin rate
    private String mM;                // Maintenance margin rate
    private String mR;                // Maximum risk limit
}
