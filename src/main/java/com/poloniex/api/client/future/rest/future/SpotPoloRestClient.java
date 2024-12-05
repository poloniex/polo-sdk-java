package com.poloniex.api.client.future.rest.future;

import com.poloniex.api.client.future.common.PoloApiException;
import com.poloniex.api.client.future.model.request.future.*;
import com.poloniex.api.client.future.model.response.future.*;
import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
@Slf4j
/*
 * Client which connects to Poloniex public and private Rest endpoints
 * 连接到Poloniex公共和私有Rest端点的客户端
 */
public class SpotPoloRestClient {

    /**
     * error message
     */
    public static final String AUTHENTICATION_ERROR_MESSAGE = "Client must be authenticated to use this endpoint";


    private final SpotPoloPublicApiService spotPoloPublicApiService;

    private final SpotPoloPrivateApiService spotPoloPrivateApiService;
    /**
     * creates public api service
     *
     * @param host host base url
     *             <dt><b>Example:</b></dt>
     *             <pre>
     *              {@code PoloRestClient poloniexApiClient = new PoloRestClient("https://api.poloniex.com");}
     *              </pre>
     */
    public SpotPoloRestClient(String host) {
        spotPoloPublicApiService = SpotPoloApiServiceGenerator.createPublicService(host);
        spotPoloPrivateApiService = null;
    }

    public SpotPoloRestClient(String host, String apiKey, String secret) {
        spotPoloPublicApiService = SpotPoloApiServiceGenerator.createPublicService(host);
        spotPoloPrivateApiService = SpotPoloApiServiceGenerator.createPrivateService(host, apiKey, secret);
    }

    public static <T> T execute(Call<T> call) {
        try {

            Response<T> response = call.execute();

            if (response.isSuccessful()) {
                return response.body();
            } else {
                String message = null;
                if (nonNull(response.errorBody())) {
                    message = response.errorBody().string();
                }
                if (isNull(message) || message.isEmpty()) {
                    message = response.toString();
                }
                throw new PoloApiException(message);
            }
        } catch (IOException e) {
            throw new PoloApiException(e.getMessage(), e);
        }
    }


    public GetAccountBalanceResponse getAccountBalance() {
        return execute(spotPoloPrivateApiService.getAccountBalance());
    }


    public PlaceOrderResponse placeOrder(PlaceOrderRequest request) {
        return execute(spotPoloPrivateApiService.placeOrder(request));
    }


    public PlaceMultipleOrdersResponse placeMultipleOrders(List<PlaceMultipleOrdersRequest> request) {
        return execute(spotPoloPrivateApiService.placeMultipleOrders(request));
    }


    public CancelOrderResponse cancelOrder(String symbol,  String ordId, String clOrdId) {
        return execute(spotPoloPrivateApiService.cancelOrder(symbol,ordId,clOrdId));
    }


//    public CancelMultipleOrdersResponse cancelMultipleOrders( String symbol,  List<String> ordIds, List<String> clOrdIds) {
//        return execute(spotPoloPrivateApiService.cancelMultipleOrders(symbol,ordIds,clOrdIds));
//    }

public CancelMultipleOrdersResponse cancelMultipleOrders(CancelMultipleOrdersRequest request) {
    return execute(spotPoloPrivateApiService.cancelMultipleOrders(request));
}


    public CancelAllOrdersResponse cancelAllOrders(String symbol, String side) {
        return execute(spotPoloPrivateApiService.cancelAllOrders(symbol,side));
    }


    public CloseAtMarketPriceResponse closeAtMarketPrice(CloseAtMarketPriceRequest request) {
        return execute(spotPoloPrivateApiService.closeAtMarketPrice(request));
    }


    public CloseAllAtMarketPriceResponse CloseAllAtMarketPrice() {
        return execute(spotPoloPrivateApiService.CloseAllAtMarketPrice());
    }


    public GetCurrentOrdersResponse getCurrentOrders(String side,
                                                           String symbol,
                                                           String ordId,
                                                           String clOrdId,
                                                           Long from,
                                                           Integer limit,
                                                           String direct) {
        return execute(spotPoloPrivateApiService.getCurrentOrders(side, symbol, ordId, clOrdId, from, limit, direct));
    }


    public GetExecutionDetailsResponse getExecutionDetails(String side,
                                                                 String symbol,
                                                                 String ordId,
                                                                 String clOrdId,
                                                                 Long from,
                                                                 Integer limit,String sTime,String eTime,
                                                                 String direct) {
        return execute(spotPoloPrivateApiService.getExecutionDetails(side, symbol, ordId, clOrdId, sTime,eTime,from, limit, direct));
    }


    public GetOrderHistoryResponse getOrderHistory(String side,
                                                         String symbol,
                                                         String ordId,
                                                         String clOrdId,
                                                         Long from,
                                                         Integer limit,
                                                         String direct,
                                                         String state,
                                                         String type,
                                                         String sTime,
                                                         String eTime) {
        return execute(spotPoloPrivateApiService.getOrderHistory(side, symbol, ordId, clOrdId, from, limit, direct, state, type, sTime, eTime));
    }


    public GetCurrentPositionResponse getCurrentPosition(String symbol) {
        return execute(spotPoloPrivateApiService.getCurrentPosition(symbol));
    }

    public GetLeveragesResponse getLeverages(String symbol,String mgnModel) {
        return execute(spotPoloPrivateApiService.getLeverages(symbol,mgnModel));
    }

    public GetPositionHistoryResponse getPositionHistory(String symbol,
                                                               String mgnMode,
                                                               Long from,
                                                               Integer limit,
                                                               String direct,
                                                               String sTime,
                                                               String eTime) {
        return execute(spotPoloPrivateApiService.getPositionHistory(symbol, mgnMode, from, limit, direct, sTime, eTime, null));
    }


    public AdjustMarginResponse adjustMargin(AdjustMarginRequest request) {
        return execute(spotPoloPrivateApiService.adjustMargin(request));
    }


//    public SwitchCrossResponse switchCross(SwitchCrossRequest request) {
//        return execute(spotPoloPrivateApiService.switchCross(request));
//    }
//
//
//    public GetMarginModeResponse getMarginMode( String symbol) {
//        return execute(spotPoloPrivateApiService.getMarginMode(symbol));
//    }
//
//
//    public GetLeverageResponse getLeverage(String symbol) {
//        return execute(spotPoloPrivateApiService.getLeverage(symbol));
//    }


    public SetLeverageResponse setLeverage(SetLeverageRequest request) {
        return execute(spotPoloPrivateApiService.setLeverage(request));
    }

    public GetModeResponse getPositionMode() {
        return execute(spotPoloPrivateApiService.getPositionMode());
    }

    public GetOrderBookResponse getOrderBook(String symbol,
                                             String scale,
                                             Integer limit) {
        return execute(spotPoloPublicApiService.getOrderBook(symbol, scale, limit));
    }


    public GetK_lineDataResponse getKlineData( String symbol,
                                                    String interval,

                                                    Integer limit,

                                                    Long startTime,
                                                    Long endTime) {
        return  execute(spotPoloPublicApiService.getKlineData(symbol, interval, limit, startTime, endTime));

    }


    public GetExecutionInfoResponse getExecutionInfo(String symbol, Integer limit) {
        return execute(spotPoloPublicApiService.getExecutionInfo(symbol, limit));
    }


    public GetMarketInfoResponse getMarketInfo() {
        return execute(spotPoloPublicApiService.getMarketInfo());
    }


    public GetIndexPriceResponse getIndexPrice(String symbol) {

        return execute(spotPoloPublicApiService.getIndexPrice(symbol));
    }
    public GetIndexPriceComponentsResponse getIndexPricecomponents(String symbol) {
        return execute(spotPoloPublicApiService.getIndexPricecomponents(symbol));
    }


    public GetIndexPriceK_lineDataResponse  getIndexPriceK_lineData(String symbol,
                                                                         String interval,

                                                                         Integer limit,

                                                                         String sTime,
                                                                         String eTime) {
        return execute(spotPoloPublicApiService.getIndexPriceK_lineData(symbol,interval,limit,sTime,eTime));
    }


    public GetPremiumIndexK_lineDataResponse getPremiumIndexK_lineData(String symbol,
                                                                       String interval,

                                                                       Integer limit,

                                                                       String sTime,
                                                                       String eTime) {

        return execute(spotPoloPublicApiService.getPremiumIndexK_lineData(symbol,interval,limit,sTime,eTime));
    }
    public GetMarkPriceResponse getMarkPrice(String symbol) {
        return execute(spotPoloPublicApiService.getMarkPrice(symbol));
    }


    public GetMarkPriceK_LineResponse getMarkPriceK_lineData(String symbol,
                                                                   String interval,

                                                                   Integer limit,

                                                                   String sTime,
                                                                   String eTime) {

        return execute(spotPoloPublicApiService.getMarkPriceK_lineData(symbol,interval,limit,sTime,eTime));
    }
    public GetProductInfoResponse getProductInfo(String symbol) {
        return execute(spotPoloPublicApiService.getProductInfo(symbol));
    }
    public GetCurrentFundingRateResponse getCurrentFundingRate(String symbol) {
        return execute(spotPoloPublicApiService.getCurrentFundingRate(symbol));
    }


    public CurrentOpenpositionsResponse currentOpenpositions(String symbol) {

        return execute(spotPoloPublicApiService.currentOpenpositions(symbol));
    }
    public QueryInsuranceResponse queryInsurance() {

        return execute(spotPoloPublicApiService.queryInsurance());
    }
    public HistoricalFundingRatesResponse getHistoricalFundingRates(String symbol, String sT, String eT, Integer limit) {

        return execute(spotPoloPublicApiService.getHistoricalFundingRates(symbol,sT,eT,limit));
    }
    public GetFuturesRiskLimitResponse getFuturesRiskLimit(String symbol) {
        return execute(spotPoloPublicApiService.getFuturesRiskLimit(symbol));
    }



}