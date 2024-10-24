package com.poloniex.api.client.future.rest.future;

import com.poloniex.api.client.future.model.request.future.*;
import com.poloniex.api.client.future.model.response.future.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

import static com.poloniex.api.client.future.common.PoloApiConstants.*;

/**
 * PoloPublicApiService interface defines the API to communicate with the Poloniex exchange server.
 * <p>
 * Instances of PoloPublicApiService are generated using Retrofit. The generated object will connect to Poloniex's
 * public REST endpoints.
 * <p>
 * Use {@link SpotPoloApiServiceGenerator} to build PoloPublicApiService object
 */

public interface SpotPoloPrivateApiService {


    @GET(Account_Balance)
    Call<GetAccountBalanceResponse> getAccountBalance();


    @POST(Place_Order)
    Call<PlaceOrderResponse>placeOrder(@Body PlaceOrderRequest request);


    @POST(Place_Multiple_Orders)
    Call<PlaceMultipleOrdersResponse> placeMultipleOrders(@Body List<PlaceMultipleOrdersRequest> request);


    @DELETE(Cancel_Order)
    Call<CancelOrderResponse> cancelOrder(@Query("symbol") String symbol,@Query("ordId") String ordId,@Query("clOrdId") String clOrdId);


    @HTTP(method = "DELETE", hasBody = true, path = "/v3/trade/batchOrders")
    Call<CancelMultipleOrdersResponse> cancelMultipleOrders(@Body CancelMultipleOrdersRequest cancelMultipleOrdersRequest);

//    @DELETE(Cancel_Multiple_Orders)
//    Call<CancelMultipleOrdersResponse> cancelMultipleOrders(
//            @Query("symbol") String symbol,
//            @Query("ordIds") List<String> ordIds,
//            @Query("clOrdIds") List<String> clOrdIds);


    @DELETE(Cancel_All_Orders)
    Call<CancelAllOrdersResponse> cancelAllOrders(@Query("symbol") String symbol,@Query("side") String side);


    @POST(Close_At_Market_Price)
    Call<CloseAtMarketPriceResponse> closeAtMarketPrice(@Body CloseAtMarketPriceRequest request);


    @POST(Close_All_At_Market_Price)
    Call<CloseAllAtMarketPriceResponse> CloseAllAtMarketPrice();


    @GET(Current_Orders)
    Call<GetCurrentOrdersResponse> getCurrentOrders(@Query("side") String side,
                                                          @Query("symbol") String symbol,
                                                          @Query("ordId") String ordId,
                                                          @Query("clOrdId") String clOrdId,
                                                          @Query("from") Long from,
                                                          @Query("limit") Integer limit,
                                                          @Query("direct") String direct);


    @GET(Execution_Details)
    Call<GetExecutionDetailsResponse> getExecutionDetails(@Query("side")  String side,
                                                             @Query("symbol")    String symbol,
                                                             @Query("ordId")   String ordId,
                                                             @Query("clOrdId")   String clOrdId,
                                                                @Query("ordId")   String sTime,
                                                                @Query("clOrdId")   String eTime,
                                                             @Query("from")    Long from,
                                                             @Query("limit")   Integer limit,
                                                             @Query("direct")  String direct);


    @GET(Order_History)
    Call<GetOrderHistoryResponse> getOrderHistory(@Query("side")  String side,
                                                        @Query("symbol")    String symbol,
                                                        @Query("ordId")   String ordId,
                                                        @Query("clOrdId")   String clOrdId,
                                                        @Query("from")    Long from,
                                                        @Query("limit")   Integer limit,
                                                        @Query("direct")  String direct,
                                                        @Query("state")   String state,
                                                        @Query("type")   String type,
                                                        @Query("sTime")   String sTime,
                                                        @Query("eTime") String eTime);


    @GET(Current_Position)
    Call<GetCurrentPositionResponse> getCurrentPosition(@Query("symbol") String symbol);


    @GET(Position_History)
    Call<GetPositionHistoryResponse> getPositionHistory(@Query("symbol")    String symbol,
                                                              @Query("mgnMode")   String mgnMode,
                                                              @Query("from")    Long from,
                                                              @Query("limit")   Integer limit,
                                                              @Query("direct")  String direct,
                                                              @Query("sTime")   String sTime,
                                                              @Query("eTime") String eTime);


    @POST(Adjust_Margin)
    Call<AdjustMarginResponse> adjustMargin(@Body AdjustMarginRequest  request);


    @POST(Switch_Cross)
    Call<SwitchCrossResponse> switchCross(@Body SwitchCrossRequest request);


    @GET(Margin_Mode)
    Call<GetMarginModeResponse> getMarginMode(@Query("symbol") String symbol);

    @GET(Get_Leverage)
    Call<GetLeverageResponse> getLeverage(@Query("symbol") String symbol);


    @POST(Set_Leverage)
    Call<SetLeverageResponse> setLeverage(@Body SetLeverageRequest request);


   /* @GET(Order_Book)
    Call<GetOrderBookResponse> getOrderBook(@Query("symbol")    String symbol,
                                            @Query("scale")   String scale,
                                            @Query("limit")    Integer limit);

    @GET(K_line_Data)
    Call<List<GetK_lineDataResponse>> getKlineData(@Query("symbol")    String symbol,
                                      @Query("interval")   String interval,

                                      @Query("limit")   Integer limit,

                                      @Query("startTime")  Long startTime,
                                      @Query("endTime") Long endTime);


    @GET(Execution_Info)
    Call<List<GetExecutionInfoResponse>> getExecutionInfo(@Query("symbol")    String symbol, @Query("limit")   Integer limit);

    @GET(Market_Info)
    Call<List<GetMarketInfoResponse>> getMarketInfo();

    @GET(Index_Price)
    Call<GetIndexPriceResponse> getIndexPrice(@Query("symbol")    String symbol);

    @GET(Index_Price_components)
    Call<List<GetIndexPriceComponentsResponse>> getIndexPricecomponents(@Query("symbol")String symbol);

    @GET(Index_Price_K_line_Data)
    Call<List<GetIndexPriceK_lineDataResponse>> getIndexPriceK_lineData(@Query("symbol")    String symbol,
                                                                        @Query("interval")   String interval,

                                                                        @Query("limit")   Integer limit,

                                                                        @Query("sTime")  String sTime,
                                                                        @Query("eTime") String eTime);

    @GET(Premium_Index_K_line_Data)
    Call<GetPremiumIndexK_lineDataResponse> getPremiumIndexK_lineData(@Query("symbol")    String symbol,
                                                          @Query("interval")   String interval,

                                                          @Query("limit")   Integer limit,

                                                          @Query("sTime")  String sTime,
                                                          @Query("eTime") String eTime);

    @GET(Mark_Price)
    Call<GetMarkPriceResponse> getMarkPrice(@Query("symbol") String symbol);

    @GET(Mark_Price_K_line_Data)
    Call<List<GetMarkPriceK_LineResponse>> getMarkPriceK_lineData(@Query("symbol")    String symbol,
                                                                  @Query("interval")   String interval,

                                                                  @Query("limit")   Integer limit,

                                                                  @Query("sTime")  String sTime,
                                                                  @Query("eTime") String eTime);

    @GET(Product_Info)
    Call<GetProductInfoResponse> getProductInfo(@Query("symbol")    String symbol);

    @GET(Current_Funding_Rate)
    Call<GetCurrentFundingRateResponse> getCurrentFundingRate(@Query("symbol")    String symbol);

    @GET(Historical_Funding_Rates)
    Call<List<HistoricalFundingRatesResponse> >getHistoricalFundingRates(@Query("symbol")    String symbol,
                                                                         @Query("sT")   String sT,
                                                                         @Query("eT")   String eT,
                                                                         @Query("limit")   Integer limit);

    @GET(Current_Open_Positions)
    Call<CurrentOpenpositionsResponse> currentOpenpositions(@Query("symbol")    String symbol);

    @GET(Query_Insurance)
    Call<List<QueryInsuranceResponse>> queryInsurance();

    @GET(Futures_Risk_Limit)
    Call<List<GetFuturesRiskLimitResponse> >getFuturesRiskLimit(@Query("symbol")    String symbol);*/

}
