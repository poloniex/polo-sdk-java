package com.poloniex.api.client.future.model.response.future;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetCurrentOrdersResponse {

    private int code;
    private String msg;
    private List<GetCurrentOrders> data=new ArrayList<>();

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetCurrentOrders{
    private String symbol;
    private String side;
    private String type;
    private String ordId;
    private String clOrdId;
    private String mgnMode;
    private String px;
    private String sz;
    private String state;
    private String source;
    private Boolean reduceOnly;
    private String timeInForce;
    private String avgPx;
    private String execQty;
    private String execAmt;
    private String feeCcy;
    private String feeAmt;
    private String deductCcy;
    private String deductAmt;
    private String stpMode;
    private String cTime;
    private String uTime;}

}
