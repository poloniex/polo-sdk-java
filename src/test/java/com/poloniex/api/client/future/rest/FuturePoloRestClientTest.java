package com.poloniex.api.client.future.rest;

import com.poloniex.api.client.future.common.PoloApiException;

import com.poloniex.api.client.future.model.request.future.*;
import com.poloniex.api.client.future.model.response.future.*;
import com.poloniex.api.client.future.rest.future.SpotPoloPrivateApiService;
import com.poloniex.api.client.future.rest.future.SpotPoloPublicApiService;
import com.poloniex.api.client.future.rest.future.SpotPoloRestClient;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuturePoloRestClientTest {

    @Mock
    private SpotPoloPublicApiService spotPoloPublicApiService;
    @Mock
    private SpotPoloPrivateApiService spotPoloPrivateApiService;

    SpotPoloRestClient spotPoloRestClient;

    @BeforeEach
    void setUp() throws NoSuchFieldException {
        spotPoloRestClient = new SpotPoloRestClient("https://api.poloniex.com/v3/");
        spotPoloRestClient = new SpotPoloRestClient("https://api.poloniex.com/v3/", "", "");

        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPrivateApiService"), spotPoloPrivateApiService);
        FieldSetter.setField(spotPoloRestClient, spotPoloRestClient.getClass().getDeclaredField("spotPoloPublicApiService"), spotPoloPublicApiService);
    }

    @Test
    void testGetAccountBalance() throws IOException {
        GetAccountBalanceResponse c1 =new GetAccountBalanceResponse();
        GetAccountBalanceResponse.GetAccountBalance getAccountBalance=new GetAccountBalanceResponse.GetAccountBalance();
        c1.getData().setCTime("2001");

        Call<GetAccountBalanceResponse> call = mock(Call.class);

        when(call.execute()).thenReturn(Response.success(c1));
        when(spotPoloPrivateApiService.getAccountBalance()).thenReturn(call);
        GetAccountBalanceResponse lom = spotPoloRestClient.getAccountBalance();
        verify(spotPoloPrivateApiService, times(1)). getAccountBalance();
        assertEquals("2001", lom.getData().getCTime());
        //test execute response not successful case
        //only need to test it once since it doesn't vary across each test case
        ResponseBody rb = ResponseBody.create(null,"test");
        Response<GetAccountBalanceResponse> response = Response.error(400, rb);
        when(call.execute()).thenReturn(response);
        when(spotPoloPrivateApiService.getAccountBalance()).thenReturn(call);

        try {
            spotPoloRestClient.getAccountBalance();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("test", e.getMessage());
        }

        //test execute exceptions cases
        //only need to test it once since it doesn't vary across each test case
        when(call.execute()).thenThrow(new IOException("ERROR"));
        when(spotPoloPrivateApiService.getAccountBalance()).thenReturn(call);

        try {
            spotPoloRestClient.getAccountBalance();
            fail("Exception not thrown");
        } catch (PoloApiException e) {
            assertEquals("ERROR", e.getMessage());
        }
    }


    //返回值是一个，主体request
    @Test
    void testPlaceOrder() throws IOException {
        PlaceOrderRequest request = PlaceOrderRequest.builder().side("BUY").symbol("BTC").type("MARKET").sz("10").build();

        PlaceOrderResponse atr = new PlaceOrderResponse();
        atr.getData().setCordld("test");


        Call<PlaceOrderResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.placeOrder(request)).thenReturn(call);
        PlaceOrderResponse response = spotPoloRestClient.placeOrder(request);
        verify(spotPoloPrivateApiService, times(1)).placeOrder(request);
        assertEquals("test", response.getData().getCordld());
    }
    //返回值是一个列表，主体request
    @Test
    void testPlaceMultipleOrders() throws IOException {

        PlaceMultipleOrdersResponse response=new PlaceMultipleOrdersResponse();
        PlaceMultipleOrdersResponse.PlaceMultipleOrders placeMultipleOrders=new PlaceMultipleOrdersResponse.PlaceMultipleOrders();
        placeMultipleOrders.setCordld("test");
        response.getData().add(placeMultipleOrders);


        List<PlaceMultipleOrdersRequest>request=new ArrayList<>();

        PlaceMultipleOrdersRequest request2 = PlaceMultipleOrdersRequest.builder().symbol("BTC").side("BUY").type("MARKET").sz("10").build();
request.add(request2);

        Call< PlaceMultipleOrdersResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPrivateApiService.placeMultipleOrders(request)).thenReturn(call);
        PlaceMultipleOrdersResponse l2 = spotPoloRestClient.placeMultipleOrders(request);
        verify(spotPoloPrivateApiService, times(1)).placeMultipleOrders(request);
        assertEquals("test", l2.getData().get(0).getCordld());
    }

    @Test
    void testCancelOrder() throws IOException {


        CancelOrderResponse atr = new CancelOrderResponse();
        atr.getData().setCordld("test");

        Call<CancelOrderResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.cancelOrder("BTC_USDT_PERP",null,null)).thenReturn(call);
        CancelOrderResponse response = spotPoloRestClient.cancelOrder("BTC_USDT_PERP",null,null);
        verify(spotPoloPrivateApiService, times(1)).cancelOrder("BTC_USDT_PERP",null,null);
        assertEquals("test", response.getData().getCordld());
    }

    @Test
    void testCancelMultipleOrders() throws IOException {
        CancelMultipleOrdersRequest request= CancelMultipleOrdersRequest.builder().symbol("BTC_USDT_PERP").build();
        CancelMultipleOrdersResponse response=new CancelMultipleOrdersResponse();
        CancelMultipleOrdersResponse.CancelMultipleOrders cancelMultipleOrders=new CancelMultipleOrdersResponse.CancelMultipleOrders();
        cancelMultipleOrders.setCordld("test");
        response.getData().add(cancelMultipleOrders);

        Call<CancelMultipleOrdersResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

//        when(spotPoloPrivateApiService.cancelMultipleOrders("BTC_USDT_PERP",null,null)).thenReturn(call);
//        CancelMultipleOrdersResponse l2 = spotPoloRestClient.cancelMultipleOrders("BTC_USDT_PERP",null,null);
//        verify(spotPoloPrivateApiService, times(1)).cancelMultipleOrders("BTC_USDT_PERP",null,null);
//
        when(spotPoloPrivateApiService.cancelMultipleOrders(request)).thenReturn(call);
        CancelMultipleOrdersResponse l2 = spotPoloRestClient.cancelMultipleOrders(request);
        verify(spotPoloPrivateApiService, times(1)).cancelMultipleOrders(request);

        assertEquals("test", l2.getData().get(0).getCordld());
    }

    @Test
    void testCancelAllOrders() throws IOException {

        CancelAllOrdersResponse response=new CancelAllOrdersResponse();
        CancelAllOrdersResponse.CancelAllOrders cancelAllOrders=new CancelAllOrdersResponse.CancelAllOrders();
        cancelAllOrders.setCordld("test");
        response.getData().add(cancelAllOrders);
        Call< CancelAllOrdersResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPrivateApiService.cancelAllOrders("BTC_USDT_PERP",null)).thenReturn(call);
        CancelAllOrdersResponse l2 = spotPoloRestClient.cancelAllOrders("BTC_USDT_PERP",null);
        verify(spotPoloPrivateApiService, times(1)).cancelAllOrders("BTC_USDT_PERP",null);
        assertEquals("test", l2.getData().get(0).getCordld());
    }

    @Test
    void testcloseAtMarketPrice() throws IOException {
        CloseAtMarketPriceRequest request = CloseAtMarketPriceRequest.builder().symbol("BTC").build();

        CloseAtMarketPriceResponse atr = new CloseAtMarketPriceResponse();
        atr.getData().setCordld("test");

        Call<CloseAtMarketPriceResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.closeAtMarketPrice(request)).thenReturn(call);
        CloseAtMarketPriceResponse response = spotPoloRestClient.closeAtMarketPrice(request);
        verify(spotPoloPrivateApiService, times(1)).closeAtMarketPrice(request);
        assertEquals("test", response.getData().getCordld());
    }

    @Test
    void testCloseAllAtMarketPrice() throws IOException {

        CloseAllAtMarketPriceResponse response=new CloseAllAtMarketPriceResponse();
        CloseAllAtMarketPriceResponse.CloseAllAtMarketPrice closeAllAtMarketPrice=new CloseAllAtMarketPriceResponse.CloseAllAtMarketPrice();
        closeAllAtMarketPrice.setCordld("test");
        response.getData().add(closeAllAtMarketPrice);


//        PlaceMultipleOrdersRequest request = PlaceMultipleOrdersRequest.builder().build();


        Call<CloseAllAtMarketPriceResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPrivateApiService.CloseAllAtMarketPrice()).thenReturn(call);
        CloseAllAtMarketPriceResponse l2 = spotPoloRestClient.CloseAllAtMarketPrice();
        verify(spotPoloPrivateApiService, times(1)).CloseAllAtMarketPrice();
        assertEquals("test", l2.getData().get(0).getCordld());
    }

    //返回值是一个列表，主体query
    @Test
    void testGetCurrentOrders() throws IOException {

        GetCurrentOrdersResponse response=new GetCurrentOrdersResponse();
        GetCurrentOrdersResponse.GetCurrentOrders getCurrentOrders=new GetCurrentOrdersResponse.GetCurrentOrders();
        getCurrentOrders.setCTime("test");
        response.getData().add(getCurrentOrders);

        Call< GetCurrentOrdersResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPrivateApiService.getCurrentOrders("BUY",null,null,null,null,null,null)).thenReturn(call);
        GetCurrentOrdersResponse l2 = spotPoloRestClient.getCurrentOrders("BUY",null,null,null,null,null,null);
        verify(spotPoloPrivateApiService, times(1)).getCurrentOrders("BUY",null,null,null,null,null,null);
        assertEquals("test", l2.getData().get(0).getCTime());
    }

    @Test
    void testGetExecutionDetails() throws IOException {

        GetExecutionDetailsResponse response=new GetExecutionDetailsResponse();
        GetExecutionDetailsResponse.GetExecutionDetails getExecutionDetails=new GetExecutionDetailsResponse.GetExecutionDetails();
        getExecutionDetails.setClOrdId("test");
        response.getData().add(getExecutionDetails);
        Call< GetExecutionDetailsResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));
        when(spotPoloPrivateApiService.getExecutionDetails("BUY",null,null,null,null,null,null,null,null)).thenReturn(call);
        GetExecutionDetailsResponse l2  =spotPoloRestClient.getExecutionDetails("BUY",null,null,null,null,null,null,null,null);
        verify(spotPoloPrivateApiService, times(1)).getExecutionDetails("BUY",null,null,null,null,null,null,null,null);
        assertEquals("test", l2.getData().get(0).getClOrdId());
    }
    @Test
    void testGetOrderHistory() throws IOException {

        GetOrderHistoryResponse response=new GetOrderHistoryResponse();
        GetOrderHistoryResponse.GetOrderHistory getOrderHistory=new GetOrderHistoryResponse.GetOrderHistory();
        getOrderHistory.setClOrdId("test");
        response.getData().add(getOrderHistory);

        Call< GetOrderHistoryResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPrivateApiService.getOrderHistory("BUY",null,null,null,null,null,null,null,null,null,null)).thenReturn(call);
        GetOrderHistoryResponse l2  =spotPoloRestClient.getOrderHistory("BUY",null,null,null,null,null,null,null,null,null,null);
        verify(spotPoloPrivateApiService, times(1)).getOrderHistory("BUY",null,null,null,null,null,null,null,null,null,null);
        assertEquals("test", l2.getData().get(0).getClOrdId());
    }

    @Test
    void testgetPositionHistory() throws IOException {
        GetPositionHistoryResponse response = new GetPositionHistoryResponse();
        GetPositionHistoryResponse.GetPositionHistory getPositionHistory=new GetPositionHistoryResponse.GetPositionHistory();


        // 设置 CTime
        getPositionHistory.setCTime("test");

        // 将 getPositionHistory 添加到 data 列表中
        response.getData().add(getPositionHistory);
        Call< GetPositionHistoryResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));
        when(spotPoloPrivateApiService.getPositionHistory("BTC_USDT_PERP",null,null,null,null,null,null)).thenReturn(call);
        GetPositionHistoryResponse l2  =spotPoloRestClient.getPositionHistory("BTC_USDT_PERP",null,null,null,null,null,null);
        verify(spotPoloPrivateApiService, times(1)).getPositionHistory("BTC_USDT_PERP",null,null,null,null,null,null);
        assertEquals("test", l2.getData().get(0).getCTime());
    }
    @Test
    void testgetCurrentPosition() throws IOException {

        GetCurrentPositionResponse response=new GetCurrentPositionResponse();
        GetCurrentPositionResponse.GetCurrentPosition getCurrentPosition=new GetCurrentPositionResponse.GetCurrentPosition();
        getCurrentPosition.setCTime("test");
        response.getData().add(getCurrentPosition);
        Call<GetCurrentPositionResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPrivateApiService.getCurrentPosition("BTC_USDT_PERP")).thenReturn(call);
        GetCurrentPositionResponse  l2=spotPoloRestClient.getCurrentPosition("BTC_USDT_PERP");
        verify(spotPoloPrivateApiService, times(1)).getCurrentPosition("BTC_USDT_PERP");
        assertEquals("test", l2.getData().get(0).getCTime());
    }

    @Test
    void testAdjustMargin() throws IOException {
        AdjustMarginRequest request = AdjustMarginRequest.builder().symbol("BTC").type("ADD").amt("10").build();

        AdjustMarginResponse atr = new AdjustMarginResponse();
        atr.getData().setSymbol("test");

        Call<AdjustMarginResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.adjustMargin(request)).thenReturn(call);
        AdjustMarginResponse response = spotPoloRestClient.adjustMargin(request);
        verify(spotPoloPrivateApiService, times(1)).adjustMargin(request);
        assertEquals("test", response.getData().getSymbol());
    }
    @Test
    void testSwitchCross() throws IOException {
        SwitchCrossRequest request = SwitchCrossRequest.builder().symbol("BTC").mgnMode("CROSS").build();

        SwitchCrossResponse atr = new SwitchCrossResponse();
        atr.getData().setSymbol("test");

        Call<SwitchCrossResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.switchCross(request)).thenReturn(call);
        SwitchCrossResponse response = spotPoloRestClient.switchCross(request);
        verify(spotPoloPrivateApiService, times(1)).switchCross(request);
        assertEquals("test", response.getData().getSymbol());
    }

    @Test
    void testGetMarginMode() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetMarginModeResponse atr = new GetMarginModeResponse();
        atr.getData().setSymbol("test");

        Call<GetMarginModeResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.getMarginMode("symbol")).thenReturn(call);
        GetMarginModeResponse response = spotPoloRestClient.getMarginMode("symbol");
        verify(spotPoloPrivateApiService, times(1)).getMarginMode("symbol");
        assertEquals("test", response.getData().getSymbol());
    }

    @Test
    void testGetLeverage() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetLeverageResponse atr = new GetLeverageResponse();
        atr.getData().setSymbol("test");

        Call<GetLeverageResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.getLeverage("symbol")).thenReturn(call);
        GetLeverageResponse response = spotPoloRestClient.getLeverage("symbol");
        verify(spotPoloPrivateApiService, times(1)).getLeverage("symbol");
        assertEquals("test", response.getData().getSymbol());
    }

    @Test
    void testSetLeverage() throws IOException {
        SetLeverageRequest request = SetLeverageRequest.builder().symbol("BTC").lever("7").build();

        SetLeverageResponse atr = new SetLeverageResponse();
        atr.getData().setSymbol("test");

        Call<SetLeverageResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPrivateApiService.setLeverage(request)).thenReturn(call);
        SetLeverageResponse response = spotPoloRestClient.setLeverage(request);
        verify(spotPoloPrivateApiService, times(1)).setLeverage(request);
        assertEquals("test", response.getData().getSymbol());
    }

    @Test
    void testGetOrderBook() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetOrderBookResponse atr = new GetOrderBookResponse();
        atr.getData().setTs("test");

        Call<GetOrderBookResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getOrderBook("symbol",null,null)).thenReturn(call);
        GetOrderBookResponse response = spotPoloRestClient.getOrderBook("symbol",null,null);
        verify(spotPoloPublicApiService, times(1)).getOrderBook("symbol",null,null);
        assertEquals("test", response.getData().getTs());
    }

    @Test
    void testGetKlineData() throws IOException {

        GetK_lineDataResponse response=new GetK_lineDataResponse();
        GetK_lineDataResponse.GetK_lineData getK_lineData=new GetK_lineDataResponse.GetK_lineData();
        getK_lineData.setC("test");
        response.getData().add(getK_lineData);
        Call< GetK_lineDataResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPublicApiService.getKlineData("BTC_USDT_PERP",null,null,null,null)).thenReturn(call);
        GetK_lineDataResponse  l2 = spotPoloRestClient.getKlineData("BTC_USDT_PERP",null,null,null,null);
        verify(spotPoloPublicApiService, times(1)).getKlineData("BTC_USDT_PERP",null,null,null,null);
        assertEquals("test", l2.getData().get(0).getC());
    }

    @Test
    void testGetExecutionInfo() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetExecutionInfoResponse atr = new GetExecutionInfoResponse();
        GetExecutionInfoResponse.ExecutionInfo executionInfo=new GetExecutionInfoResponse.ExecutionInfo();
        executionInfo.setAmt("test");
        atr.getData().add(executionInfo);


        Call<GetExecutionInfoResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getExecutionInfo("BTC_USDT_PERP",10)).thenReturn(call);
         GetExecutionInfoResponse l2 = spotPoloRestClient.getExecutionInfo("BTC_USDT_PERP",10);
        verify(spotPoloPublicApiService, times(1)).getExecutionInfo("BTC_USDT_PERP",10);
        assertEquals("test", l2.getData().get(0).getAmt());
    }

    @Test
    void testGetMarketInfo() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetMarketInfoResponse atr = new GetMarketInfoResponse();
        GetMarketInfoResponse.GetMarketInfo getMarketInfo=new GetMarketInfoResponse.GetMarketInfo();
        getMarketInfo.setAmt("test");
        atr.getData().add(getMarketInfo);
        Call<GetMarketInfoResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getMarketInfo()).thenReturn(call);
        GetMarketInfoResponse l2 = spotPoloRestClient.getMarketInfo();
        verify(spotPoloPublicApiService, times(1)).getMarketInfo();
        assertEquals("test", l2.getData().get(0).getAmt());
    }

    @Test
    void testGetIndexPrice() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetIndexPriceResponse atr = new GetIndexPriceResponse();
        atr.getData().setS("test");

        Call<GetIndexPriceResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getIndexPrice("BTC_USDT_PERP")).thenReturn(call);
        GetIndexPriceResponse response = spotPoloRestClient.getIndexPrice("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).getIndexPrice("BTC_USDT_PERP");
        assertEquals("test", response.getData().getS());
    }

    @Test
    void testGetIndexPricecomponents() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetIndexPriceComponentsResponse atr = new GetIndexPriceComponentsResponse();
        GetIndexPriceComponentsResponse.GetIndexPriceComponents component = new GetIndexPriceComponentsResponse.GetIndexPriceComponents();
        atr.getData().add(component); // 添加实例到 data 列表
        component.setS("test");
        Call<GetIndexPriceComponentsResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getIndexPricecomponents("BTC_USDT_PERP")).thenReturn(call);
        GetIndexPriceComponentsResponse l2 = spotPoloRestClient.getIndexPricecomponents("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).getIndexPricecomponents("BTC_USDT_PERP");
        assertEquals("test", l2.getData().get(0).getS());
    }

    @Test
    void testGetIndexPriceK_lineData() throws IOException {

        GetIndexPriceK_lineDataResponse response = new GetIndexPriceK_lineDataResponse();
        GetIndexPriceK_lineDataResponse.GetIndexPriceK_lineData getIndexPriceK_lineData=new GetIndexPriceK_lineDataResponse.GetIndexPriceK_lineData();
        getIndexPriceK_lineData.setC("test");
        response.getData().add(getIndexPriceK_lineData);
        Call<GetIndexPriceK_lineDataResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPublicApiService.getIndexPriceK_lineData("BTC_USDT_PERP", null, null, null, null)).thenReturn(call);
        GetIndexPriceK_lineDataResponse l2 = spotPoloRestClient.getIndexPriceK_lineData("BTC_USDT_PERP", null, null, null, null);
        verify(spotPoloPublicApiService, times(1)).getIndexPriceK_lineData("BTC_USDT_PERP", null, null, null, null);
        assertEquals("test", l2.getData().get(0).getC());
    }

    @Test
    void testGetPremiumIndexK_lineData() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetPremiumIndexK_lineDataResponse atr = new GetPremiumIndexK_lineDataResponse();
        GetPremiumIndexK_lineDataResponse.GetPremiumIndexK_lineData getPremiumIndexK_lineData=new GetPremiumIndexK_lineDataResponse.GetPremiumIndexK_lineData();
        getPremiumIndexK_lineData.setC("test");
        atr.getData().add(getPremiumIndexK_lineData);
        Call<GetPremiumIndexK_lineDataResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getPremiumIndexK_lineData("BTC_USDT_PERP",null,null,null,null)).thenReturn(call);
        GetPremiumIndexK_lineDataResponse response = spotPoloRestClient.getPremiumIndexK_lineData("BTC_USDT_PERP",null,null,null,null);
        verify(spotPoloPublicApiService, times(1)).getPremiumIndexK_lineData("BTC_USDT_PERP",null,null,null,null);
        assertEquals("test", response.getData().get(0).getC());
    }

    @Test
    void testGetMarkPrice() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetMarkPriceResponse atr = new GetMarkPriceResponse();
        atr.getData().setS("test");

        Call<GetMarkPriceResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getMarkPrice("BTC_USDT_PERP")).thenReturn(call);
        GetMarkPriceResponse response = spotPoloRestClient.getMarkPrice("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).getMarkPrice("BTC_USDT_PERP");
        assertEquals("test", response.getData().getS());
    }


    @Test
    void testGetMarkPriceK_lineData() throws IOException {
        GetMarkPriceK_LineResponse response=new GetMarkPriceK_LineResponse();
        GetMarkPriceK_LineResponse.GetMarkPriceK_Line getMarkPriceK_line=new GetMarkPriceK_LineResponse.GetMarkPriceK_Line();
        getMarkPriceK_line.setC("test");
        response.getData().add(getMarkPriceK_line);
        Call< GetMarkPriceK_LineResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPublicApiService.getMarkPriceK_lineData("BTC_USDT_PERP",null,null,null,null)).thenReturn(call);
        GetMarkPriceK_LineResponse l2 = spotPoloRestClient.getMarkPriceK_lineData("BTC_USDT_PERP",null,null,null,null);
        verify(spotPoloPublicApiService, times(1)).getMarkPriceK_lineData("BTC_USDT_PERP",null,null,null,null);
        assertEquals("test", l2.getData().get(0).getC());
    }

    @Test
    void testGetProductInfo() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetProductInfoResponse atr = new GetProductInfoResponse();
        atr.getData().setAlias("test");

        Call<GetProductInfoResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getProductInfo("BTC_USDT_PERP")).thenReturn(call);
        GetProductInfoResponse response = spotPoloRestClient.getProductInfo("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).getProductInfo("BTC_USDT_PERP");
        assertEquals("test", response.getData().getAlias());
    }

    @Test
    void testGetCurrentFundingRate() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetCurrentFundingRateResponse atr = new GetCurrentFundingRateResponse();
        atr.getData().setS("test");

        Call<GetCurrentFundingRateResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getCurrentFundingRate("BTC_USDT_PERP")).thenReturn(call);
        GetCurrentFundingRateResponse response = spotPoloRestClient.getCurrentFundingRate("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).getCurrentFundingRate("BTC_USDT_PERP");
        assertEquals("test", response.getData().getS());
    }


    @Test
    void testGetHistoricalFundingRates() throws IOException {

        HistoricalFundingRatesResponse response=new HistoricalFundingRatesResponse();
        HistoricalFundingRatesResponse.HistoricalFundingRates historicalFundingRates=new HistoricalFundingRatesResponse.HistoricalFundingRates();
        historicalFundingRates.setS("test");
        response.getData().add(historicalFundingRates);


        Call< HistoricalFundingRatesResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(response));

        when(spotPoloPublicApiService.getHistoricalFundingRates("BTC_USDT_PERP",null,null,null)).thenReturn(call);
        HistoricalFundingRatesResponse l2 = spotPoloRestClient.getHistoricalFundingRates("BTC_USDT_PERP",null,null,null);
        verify(spotPoloPublicApiService, times(1)).getHistoricalFundingRates("BTC_USDT_PERP",null,null,null);
        assertEquals("test", l2.getData().get(0).getS());
    }

    @Test
    void testCurrentOpenpositions() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        CurrentOpenpositionsResponse atr = new CurrentOpenpositionsResponse();
        atr.getData().setS("test");

        Call<CurrentOpenpositionsResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.currentOpenpositions("BTC_USDT_PERP")).thenReturn(call);
        CurrentOpenpositionsResponse response = spotPoloRestClient.currentOpenpositions("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).currentOpenpositions("BTC_USDT_PERP");
        assertEquals("test", response.getData().getS());
    }

    @Test
    void testQueryInsurance() throws IOException {

        QueryInsuranceResponse atr = new QueryInsuranceResponse();
        QueryInsuranceResponse.QueryInsurance queryInsurance=new QueryInsuranceResponse.QueryInsurance();
        queryInsurance.setAmt("test");
        atr.getData().add(queryInsurance);
        Call<QueryInsuranceResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.queryInsurance()).thenReturn(call);
        QueryInsuranceResponse l2 = spotPoloRestClient.queryInsurance();
        verify(spotPoloPublicApiService, times(1)).queryInsurance();
        assertEquals("test", l2.getData().get(0).getAmt());
    }

    @Test
    void testGetFuturesRiskLimit() throws IOException {
//        SwitchCrossRequest request = SwitchCrossRequest.builder().build();

        GetFuturesRiskLimitResponse atr = new GetFuturesRiskLimitResponse();
        GetFuturesRiskLimitResponse.GetFuturesRiskLimit getFuturesRiskLimit=new GetFuturesRiskLimitResponse.GetFuturesRiskLimit();
        getFuturesRiskLimit.setSymbol("test");
        atr.getData().add(getFuturesRiskLimit);

        Call<GetFuturesRiskLimitResponse> call = mock(Call.class);
        when(call.execute()).thenReturn(Response.success(atr));

        when(spotPoloPublicApiService.getFuturesRiskLimit("BTC_USDT_PERP")).thenReturn(call);
        GetFuturesRiskLimitResponse l2 = spotPoloRestClient.getFuturesRiskLimit("BTC_USDT_PERP");
        verify(spotPoloPublicApiService, times(1)).getFuturesRiskLimit("BTC_USDT_PERP");
        assertEquals("test", l2.getData().get(0).getSymbol());
    }



}
