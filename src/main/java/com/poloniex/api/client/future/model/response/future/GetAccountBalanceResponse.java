package com.poloniex.api.client.future.model.response.future;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccountBalanceResponse {
    private int code;
    private String msg;
    private GetAccountBalance data= new GetAccountBalance();;

    @Data
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GetAccountBalance {
    private String state;
    private String eq;
    private String isoEq;
    private String im;
    private String mm;
    private String mmr;
    private String upl;
    private String availMgn;
    private String cTime;
    private String uTime;
    private List<AccountDetail> details;}
}
