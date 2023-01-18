package com.poloniex.api.client.rest;

import com.poloniex.api.client.model.*;
import com.poloniex.api.client.model.request.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

import static com.poloniex.api.client.common.PoloApiConstants.*;

/**
 * PoloPrivateApiService interface describes the API to communicate with the Poloniex exchange server.
 * <p>
 * Instances of PoloPrivateApiService are generated using Retrofit. The generated object will connect to Poloniex's
 * private REST endpoints.
 * <p>
 * Use {@link PoloApiServiceGenerator} to build PoloPrivateApiService object
 */
public interface PoloPrivateApiService {

    // accounts

    /**
     * Account Information
     *
     * @return a list of all accounts of a user.
     */
    @GET(ACCOUNTS)
    Call<List<Account>> getAccounts();

    /**
     * Account Balances by type
     *
     * @param accountType The account type. e.g. SPOT. Passing null will show all account types.
     * @return a list of all accounts of a user with each account’s id, type and balances (assets).
     */
    @GET(ACCOUNTS_TYPE_BALANCES)
    Call<List<AccountBalance>> getAccountBalancesByType(@Query("accountType") String accountType);

    /**
     * Account Balances endpoint
     *
     * @param accountId The account ID
     * @return get a list of all accounts of a user with each account’s id, type and balances (assets).
     */
    @GET(ACCOUNTS_ID_BALANCES)
    Call<List<AccountBalance>> getAccountBalancesById(@Path("account_id") Long accountId);

    /**
     * Accounts Transfer:
     * Transfer amount of currency from an account to another account for a user.
     *
     * @param accountsTransferRequest details for transfer
     * @return response with transferId
     */
    @POST(ACCOUNTS_TRANSFER)
    Call<AccountsTransferResponse> accountsTransfer(@Body AccountsTransferRequest accountsTransferRequest);

    /**
     * Accounts Transfer Records:
     * Get a list of transfer records of a user.
     *
     * @param limit The max number of records could be returned.
     * @param from it is 'transferId'. The query begin at ‘from', and the default is 0.
     * @param direction PRE, NEXT, default is NEXT
     * @param currency The transferred currency, like USDT. Default is for all currencies, if not specified.
     * @param startTime (milliseconds since UNIX epoch) transfers before start time will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) transfers after end time will not be retrieved.
     * @return a list of transfer records of a user.
     */
    @GET(ACCOUNTS_TRANSFER)
    Call<List<AccountsTransferRecord>> getAccountsTransfers(@Query("limit") Integer limit,
                                                            @Query("from") Long from,
                                                            @Query("direction") String direction,
                                                            @Query("currency") String currency,
                                                            @Query("startTime") Long startTime,
                                                            @Query("endTime") Long endTime);

    /**
     * Accounts Transfer Records by ID
     *
     * @param id transfer ID
     * @return transfer record
     */
    @GET(ACCOUNTS_TRANSFER_ID)
    Call<AccountsTransferRecord> getAccountsTransferById(@Path("id") Long id);

    /**
     * Account Activity:
     * Get a list of activities such as airdrop, rebates, staking, credit/debit adjustments, and other (historical adjustments).
     *
     * @param startTime (milliseconds since UNIX epoch) Trades filled before startTime will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) Trades filled after endTime will not be retrieved.
     * @param activityType Type of activity: ALL: 200, AIRDROP: 201, COMMISSION_REBATE: 202, STAKING: 203,
     *                                       REFERAL_REBATE: 204, CREDIT_ADJUSTMENT: 104, DEBIT_ADJUSTMENT: 105,
     *                                       OTHER: 199
     * @param limit The max number of records could be returned. Default is 100 and max is 1000.
     * @param from It is 'id'. The query begin at ‘from', and the default is 0.
     * @param direction PRE, NEXT, default is NEXT
     * @param currency The transferred currency, like USDT. Default is for all currencies, if not specified.
     * @return List of account activity
     */
    @GET(ACCOUNTS_ACTIVITY)
    Call<List<ActivityResponse>> getAccountsActivity(@Query("startTime") Long startTime,
                                                     @Query("endTime") Long endTime,
                                                     @Query("activityType") Integer activityType,
                                                     @Query("limit") Integer limit,
                                                     @Query("from") Long from,
                                                     @Query("direction") String direction,
                                                     @Query("currency") String currency);

    /**
     * Fee Info:
     * Get fee rate for an account
     *
     * @return fee info
     */
    @GET(FEE_INFO)
    Call<FeeInfo> getFeeInfo();

    // subaccounts

    /**
     * Subaccount Info:
     * Get subaccounts for a primary account
     *
     * @return a list of all the accounts within an Account Group for a user.
     */
    @GET(SUBACCOUNTS)
    Call<List<Subaccount>> getSubaccounts();

    /**
     * Subaccount Balances:
     * Get balances information by currency and account type (SPOT and FUTURES) for each account in the account group.
     * This is only functional for a primary user. A subaccount user can call /accounts/balances for SPOT account type
     * and the futures API overview for its FUTURES balances.
     * @return subaccount balances
     */
    @GET(SUBACCOUNTS_BALANCES)
    Call<List<Subaccount>> getSubaccountBalances();

    /**
     * Subaccount Balances by ID:
     * Get balances information by currency and account type (SPOT and FUTURES) for a given external accountId in the account group.
     * @param id external account ID
     * @return subaccount balances
     */
    @GET(SUBACCOUNTS_BALANCES_BY_ID)
    Call<List<Subaccount>> getSubaccountBalancesById(@Path("id") String id);

    /**
     * Subaccount Transfer:
     * Transfer amount of currency from an account and account type to another account and account type among the
     * accounts in the account group. Primary account can transfer to and from any subaccounts as well as transfer
     * between 2 subaccounts across account types. Subaccount can only transfer to the primary account across account types.
     * @return transfer respose
     */
    @POST(SUBACCOUNTS_TRANSFER)
    Call<SubaccountTransfer> transferForSubaccount(@Body SubaccountTransferRequest request);

    /**
     * Subaccount Transfer Records:
     * Get a list of transfer records of a user. Max interval for start and end time is 6 months. If no start/end time
     * params are specified then records for last 7 days will be returned.
     *
     * @param limit The max number of records could be returned. Default is 100 and max is 1000 records.
     * @param from it is 'transferId'. The query begin at ‘from', and the default is 0.
     * @param direction PRE, NEXT, default is NEXT
     * @param currency The transferred currency, like USDT. Default is for all currencies, if not specified.
     * @param fromAccountId external UID of the from account
     * @param fromAccountType from account type (SPOT or FUTURES)
     * @param toAccountId external UID of the to account
     * @param toAccountType to account type (SPOT or FUTURES)
     * @param startTime (milliseconds since UNIX epoch) transfers before start time will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) transfers after end time will not be retrieved.
     * @return list of transfer records
     */
    @GET(SUBACCOUNTS_TRANSFER)
    Call<List<SubaccountTransfer>> getSubaccountTransferRecords(@Query("limit") Integer limit,
                                                                @Query("from") Long from,
                                                                @Query("direction") String direction,
                                                                @Query("currency") String currency,
                                                                @Query("fromAccountId") String fromAccountId,
                                                                @Query("fromAccountType") String fromAccountType,
                                                                @Query("toAccountId") String toAccountId,
                                                                @Query("toAccountType") String toAccountType,
                                                                @Query("startTime") Long startTime,
                                                                @Query("endTime") Long endTime);

    /**
     * Subaccount Transfer Records by ID:
     * Get a single transfer record corresponding to the transferId
     * @param id transfer ID
     * @return list of transfer records
     */
    @GET(SUBACCOUNTS_TRANSFER_BY_ID)
    Call<List<SubaccountTransfer>> getSubaccountTransferRecordsById(@Path("id") Long id);

    // wallets

    /**
     * Deposit Addresses:
     * Get deposit addresses for a user
     *
     * @param currency the currency to display for the deposit address. If null is passed, the deposit addresses of all
     *                 currencies will be displayed.
     * @return map of deposit addresses with key being currency and value being address
     */
    @GET(WALLETS_ADDRESSES)
    Call<Map<String, String>> getDepositAddressesByCurrency(@Query("currency") String currency);

    /**
     * Wallets Activity Records:
     * Get adjustment, deposit, and withdrawal activity history within a range window for a user
     *
     * @param start the start UNIX timestamp of activities
     * @param end the end UNIX timestamp of activities
     * @param activityType The type of activity: adjustments, deposits and withdrawals. If no activity type is specified, activities of all types will be returned.
     * @return wallet activity records
     */
    @GET(WALLETS_ACTIVITY)
    Call<WalletsActivities> getWalletsActivities(@Query("start") Long start,
                                                 @Query("end") Long end,
                                                 @Query("activityType") String activityType);

    /**
     * New Currency Address:
     * Create a new address for a currency
     *
     * @param newCurrencyAddressRequest request with details for creating a new address
     * @return new address
     */
    @POST(WALLETS_ADDRESS)
    Call<NewCurrencyAddressResponse> addNewCurrencyAddress(@Body NewCurrencyAddressRequest newCurrencyAddressRequest);

    /**
     * Withdraw Currency:
     * Immediately places a withdrawal for a given currency, with no email confirmation.
     *
     * @param withdrawCurrencyRequest request with details for withdrawal
     * @return withdrawal number
     */
    @POST(WALLETS_WITHDRAW)
    Call<WithdrawCurrencyResponse> withdrawCurrency(@Body WithdrawCurrencyRequest withdrawCurrencyRequest);

    // margin

    /**
     * Account Margin:
     * Get account margin information
     *
     * @param accountType The account type. Currently only SPOT is supported
     * @return account margin info
     */
    @GET(MARGIN_ACCOUNT_MARGIN)
    Call<AccountMargin> getAccountMargin(@Query("accountType") String accountType);

    /**
     * Borrow Status:
     * Get borrow status of currencies
     *
     * @param currency currency name
     * @return borrow status of currencies
     */
    @GET(MARGIN_BORROW_STATUS)
    Call<List<BorrowStatus>> getBorrowStatus(@Query("currency") String currency);

    /**
     * Maximum Buy/Sell Amount:
     * Get maximum and available buy/sell amount for a given symbol.
     *
     * @param symbol symbol name
     * @return maximum and available buy/sell amount
     */
    @GET(MARGIN_MAX_SIZE)
    Call<MaxSize> getMaxSize(@Query("symbol") String symbol);

    // orders

    /**
     * Create Order:
     * Create an order for an account.
     *
     * @param orderRequest request with details for creating an order
     * @return order details
     */
    @POST(ORDERS)
    Call<Order> placeOrder(@Body OrderRequest orderRequest);

    /**
     * Create Multiple Orders:
     * Create multiple orders via a single request. Max limit of 20 orders. Request parameter is an array of json objects with order details.
     *
     * @param orderRequests list of requests with details for creating an order
     * @return list of order details
     */
    @POST(ORDERS_BATCH)
    Call<List<Order>> placeOrders(@Body List<OrderRequest> orderRequests);

    /**
     * Cancel Replace Order:
     * Cancel an existing active order, new or partially filled, and place a new order on the same symbol with details
     * from existing order unless amended by new parameters. The replacement order can amend price, quantity, amount,
     * type, timeInForce, and allowBorrow fields. Specify the existing order id in the path; if id is a clientOrderId,
     * prefix with cid: e.g. cid:myId-1. The proceedOnFailure flag is intended to specify whether to continue with new
     * order placement in case cancelation of the existing order fails.
     *
     * @param id order id
     * @param orderRequest request with details for replacing an order
     * @return order details
     */
    @PUT(ORDERS_BY_ID)
    Call<Order> cancelReplaceOrderById(@Path("id") String id, @Body OrderRequest orderRequest);

    /**
     * Cancel Replace Order:
     * Cancel an existing active order, new or partially filled, and place a new order on the same symbol with details
     * from existing order unless amended by new parameters. The replacement order can amend price, quantity, amount,
     * type, timeInForce, and allowBorrow fields. Specify the existing order id in the path; if id is a clientOrderId,
     * prefix with cid: e.g. cid:myId-1. The proceedOnFailure flag is intended to specify whether to continue with new
     * order placement in case cancelation of the existing order fails.
     *
     * @param clientOrderId user specified order id
     * @param orderRequest request with details for replacing an order
     * @return order details
     */
    @PUT(ORDERS_BY_CID)
    Call<Order> cancelReplaceOrderByClientOrderId(@Path("client_order_id") String clientOrderId, @Body OrderRequest orderRequest);

    /**
     * Open Orders:
     * Get a list of active orders for an account
     *
     * @param symbol The symbol to trade,like BTC_USDT. Default is for all symbols if null is passed.
     * @param side BUY, SELL
     * @param from it is 'orderId'. The query begin at ‘from', and it is 0 when you first query.
     * @param direction PRE, NEXT
     * @param limit The max number of orders could be returned.
     * @return list of orders
     */
    @GET(ORDERS)
    Call<List<Order>> getOrders(@Query("symbol") String symbol,
                                @Query("side") String side,
                                @Query("from") Long from,
                                @Query("direction") String direction,
                                @Query("limit") Integer limit);

    /**
     * Order Details:
     * Get an order’s status by specifying orderId
     *
     * @param id order id
     * @return order details
     */
    @GET(ORDERS_BY_ID)
    Call<Order> getOrderByOrderId(@Path("id") String id);

    /**
     * Order Details:
     * Get an order’s status by specifying clientOrderId
     *
     * @param clientOrderId user specified order id
     * @return order details
     */
    @GET(ORDERS_BY_CID)
    Call<Order> getOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Cancel Order by Id:
     * Cancel an active order by orderId
     *
     * @param id order id
     * @return order details
     */
    @DELETE(ORDERS_BY_ID)
    Call<CanceledOrder> cancelOrderByOrderId(@Path("id") String id);

    /**
     * Cancel Order by Id:
     * Cancel an active order by clientOrderId.
     *
     * @param clientOrderId user specified order id
     * @return order details
     */
    @DELETE(ORDERS_BY_CID)
    Call<CanceledOrder> cancelOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Cancel Multiple Orders by Ids:
     * Batch cancel one or many active orders in an account by IDs.
     *
     * @param cancelOrdersByIdRequest request with orderIds and clientOrderIds to be cancelled
     * @return list of order details
     */
    @HTTP(method = "DELETE", path = ORDERS_CANCEL_BY_IDS, hasBody = true)
    Call<List<CanceledOrder>> cancelOrderByIds(@Body CancelOrdersByIdRequest cancelOrdersByIdRequest);

    /**
     * Cancel All Orders:
     * Batch cancel all orders in an account.
     *
     * @param cancelAllOrdersRequest request with details for batch cancel
     * @return list of order details
     */
    @HTTP(method = "DELETE", path = ORDERS, hasBody = true)
    Call<List<CanceledOrder>> cancelAllOrders(@Body CancelAllOrdersRequest cancelAllOrdersRequest);

    /**
     * Orders History:
     * Get a list of historical orders in an account
     *
     * @param accountType SPOT is the default and only supported one.
     * @param type MARKET, LIMIT, LIMIT_MAKER (Default: all types)
     * @param side BUY, SELL (Default: both sides)
     * @param symbol Any supported symbol (Default: all symbols)
     * @param from An 'orderId'. The query begins at ‘from'.
     * @param direction PRE, NEXT The direction before or after ‘from'.
     * @param states FAILED, FILLED, CANCELED. PARTIALLY_CANCELED Multiple states can be specified and separated with comma. (Default: all states)
     * @param limit The max number of orders could be returned. (Default: 100)
     * @param hideCancel true or false. Whether canceled orders should not be retrieved. (Default: false)
     * @param startTime (milliseconds since UNIX epoch) orders updated before startTime will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) orders updated after endTime will not be retrieved.
     * @return list of orders
     */
    @GET(ORDERS_HISTORY)
    Call<List<Order>> getOrderHistory(@Query("accountType") String accountType,
                                      @Query("type") String type,
                                      @Query("side") String side,
                                      @Query("symbol") String symbol,
                                      @Query("from") Long from,
                                      @Query("direction") String direction,
                                      @Query("states") String states,
                                      @Query("limit") Integer limit,
                                      @Query("hideCancel") Boolean hideCancel,
                                      @Query("startTime") Long startTime,
                                      @Query("endTime") Long endTime);

    /**
     * Set Kill Switch:
     * Set a timer that cancels all regular and smartorders after the timeout has expired.
     *
     * @param killSwitchRequest request with timeout details for creating kill switch
     * @return kill switch details
     */
    @POST(KILL_SWITCH_POST)
    Call<KillSwitchResponse> setKillSwitch(@Body KillSwitchRequest killSwitchRequest);

    /**
     * Get Kill Switch:
     * Get status of kill switch.
     *
     * @return kill switch details
     */
    @GET(KILL_SWITCH_GET)
    Call<KillSwitchResponse> getKillSwitch();

    // smart orders

    /**
     * Create Smart Order:
     * Create a smart order for an account
     *
     * @param smartOrderRequest request with details for creating smart order
     * @return smart order details
     */
    @POST(SMARTORDERS)
    Call<SmartOrder> placeSmartOrder(@Body SmartOrderRequest smartOrderRequest);

    /**
     * Order Details:
     * Get a smart order’s status. If smart order’s state is TRIGGERED, the response will include the triggered order’s data
     *
     * @param id smart order's id
     * @return smart order details
     */
    @GET(SMARTORDERS_BY_ID)
    Call<List<SmartOrder>> getSmartOrderByOrderId(@Path("id") String id);

    /**
     * Order Details:
     * Get a smart order’s status. If smart order’s state is TRIGGERED, the response will include the triggered order’s data
     *
     * @param clientOrderId smart order's user specified id
     * @return smart order details
     */
    @GET(SMARTORDERS_BY_CID)
    Call<List<SmartOrder>> getSmartOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Open Orders:
     * Get a list of (pending) smart orders for an account
     *
     * @param limit The max number of smart orders could be returned.
     * @return list of smart order details
     */
    @GET(SMARTORDERS)
    Call<List<SmartOrder>> getSmartOrders(@Query("limit") Integer limit);

    /**
     * Cancel Order by Id:
     * Cancel a smart order by its id.
     *
     * @param id smart order's id
     * @return smart order details
     */
    @DELETE(SMARTORDERS_BY_ID)
    Call<CanceledOrder> cancelSmartOrderById(@Path("id") String id);

    /**
     * Cancel Order by Id:
     * Cancel a smart order by its clientOrderId.
     *
     * @param clientOrderId smart order's clientOrderId
     * @return smart order details
     */
    @DELETE(SMARTORDERS_BY_CID)
    Call<CanceledOrder> cancelSmartOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Cancel Multiple Orders by Id:
     * Batch cancel one or many smart orders in an account by IDs.
     *
     * @param cancelOrdersByIdRequest request with orderIds and clientOrderIds to
     * @return list of canceled order details
     */
    @HTTP(method = "DELETE", path = SMARTORDERS_CANCEL_BY_IDS, hasBody = true)
    Call<List<CanceledOrder>> cancelSmartOrderByIds(@Body CancelOrdersByIdRequest cancelOrdersByIdRequest);

    /**
     * Cancel All Orders:
     * Batch cancel all smart orders in an account
     *
     * @param cancelAllOrdersRequest request with details for batch canceling orders
     * @return list of canceled order details
     */
    @HTTP(method = "DELETE", path = SMARTORDERS, hasBody = true)
    Call<List<CanceledOrder>> cancelAllSmartOrders(@Body CancelAllOrdersRequest cancelAllOrdersRequest);

    /**
     * Cancel All Orders:
     * Batch cancel all smart orders in an account
     *
     * @return list of canceled order details
     */
    @HTTP(method = "DELETE", path = SMARTORDERS, hasBody = true)
    Call<List<CanceledOrder>> cancelAllSmartOrders();


    /**
     * Smart Orders History:
     * Get a list of historical smart orders in an account
     *
     * @param accountType SPOT is the default and only supported one.
     * @param type STOP, STOP_LIMIT (Default: all types)
     * @param side BUY, SELL (Default: both sides)
     * @param symbol Any supported symbol (Default: all symbols)
     * @param from An 'smart orderId'. The query begins at ‘from'.
     * @param direction PRE, NEXT The direction before or after ‘from'.
     * @param states FAILED, FILLED, CANCELED. PARTIALLY_CANCELED. Multiple states can be specified and separated with comma. (Default: all states)
     * @param limit The max number of smart orders could be returned. (Default: 100)
     * @param hideCancel true or false. Whether canceled smart orders should not be retrieved. (Default: false)
     * @param startTime (milliseconds since UNIX epoch) smart orders updated before startTime will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) smart orders updated after endTime will not be retrieved.
     * @return list of smart orders
     */
    @GET(SMARTORDERS_HISTORY)
    Call<List<SmartOrder>> getSmartOrderHistory(@Query("accountType") String accountType,
                                                @Query("type") String type,
                                                @Query("side") String side,
                                                @Query("symbol") String symbol,
                                                @Query("from") Long from,
                                                @Query("direction") String direction,
                                                @Query("states") String states,
                                                @Query("limit") Integer limit,
                                                @Query("hideCancel") Boolean hideCancel,
                                                @Query("startTime") Long startTime,
                                                @Query("endTime") Long endTime);

    // trades

    /**
     * Trade History:
     * Get a list of all trades for an account.
     *
     * @param limit default and max value is 100.
     * @param endTime (milliseconds since UNIX epoch) trades filled after endTime will not be retrieved.
     * @param startTime (milliseconds since UNIX epoch) trades filled before startTime will not be retrieved.
     * @param from A 'trade Id'. The query begins at ‘from'.
     * @param direction PRE, NEXT The direction before or after ‘from'.
     * @param symbols one or multiple symbols separated by comma
     * @return list of trades
     */
    @GET(TRADES)
    Call<List<Trade>> getTrades(@Query("limit") Integer limit,
                                @Query("endTime") Long endTime,
                                @Query("startTime") Long startTime,
                                @Query("from") Long from,
                                @Query("direction") String direction,
                                @Query("symbols") List<String> symbols);

    /**
     * Trades by Order Id:
     * Get a list of all trades for an order specified by its orderId.
     *
     * @param orderId the associated order's id (order's clientOrderId is not supported)
     * @return list of trades
     */
    @GET(TRADES_BY_ID)
    Call<List<Trade>> getUserTradesByOrderId(@Path("orderId") Long orderId);

}
