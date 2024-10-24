package com.poloniex.api.client.spot.rest.spot;

import com.poloniex.api.client.spot.model.request.spot.*;
import com.poloniex.api.client.spot.model.response.spot.*;
import com.poloniex.api.client.spot.model.request.spot.*;
import com.poloniex.api.client.spot.model.response.spot.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

import static com.poloniex.api.client.spot.common.PoloApiConstants.*;

/**
 * PoloPrivateApiService interface describes the API to communicate with the Poloniex exchange server.
 * <p>
 * Instances of PoloPrivateApiService are generated using Retrofit. The generated object will connect to Poloniex's
 * private REST endpoints.
 * <p>
 * Use {@link SpotPoloApiServiceGenerator} to build PoloPrivateApiService object
 */
public interface SpotPoloPrivateApiService {

    // accounts

    /**
     * Account Information
     *用户所有账户的列表。
     * @return a list of all accounts of a user.
     */
    @GET(ACCOUNTS)
    Call<List<Account>> getAccounts();

    /**
     * Account Balances by type
     *获取用户所有账户及每个账户的id、类型和余额（资产）的列表。
     * @param accountType The account type. e.g. SPOT. Passing null will show all account types.
     * @return a list of all accounts of a user with each account’s id, type and balances (assets).
     */
    @GET(ACCOUNTS_TYPE_BALANCES)
    Call<List<AccountBalance>> getAccountBalancesByType(@Query("accountType") String accountType);

    /**
     * Account Balances endpoint
     *获取用户所有账户及每个账户的id、类型和余额（资产）的列表。
     * @param accountId The account ID
     * @return get a list of all accounts of a user with each account’s id, type and balances (assets).
     */
    @GET(ACCOUNTS_ID_BALANCES)
    Call<List<AccountBalance>> getAccountBalancesById(@Path("account_id") Long accountId);

    /**
     * Accounts Transfer:
     * Transfer amount of currency from an account to another account for a user.
     *为用户从一个账户向另一个账户转移一定数量的货币。
     * @param accountsTransferRequest details for transfer
     * @return response with transferId
     */
    @POST(ACCOUNTS_TRANSFER)
    Call<AccountsTransferResponse> accountsTransfer(@Body AccountsTransferRequest accountsTransferRequest);

    /**
     * Accounts Transfer Records:
     * Get a list of transfer records of a user.
     *获取用户的转账记录列表。
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
     *按ID获取账户转账记录：
     * @param id transfer ID
     * @return transfer record
     */
    @GET(ACCOUNTS_TRANSFER_ID)
    Call<AccountsTransferRecord> getAccountsTransferById(@Path("id") Long id);

    /**
     * Account Activity:
     * Get a list of activities such as airdrop, rebates, staking, credit/debit adjustments, and other (historical adjustments).
     *获取空投、返利、质押、信用/借记调整及其他（历史调整）等活动列表。
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
     * Account Interest History:
     * Get a list of interest collection records of a user. Max interval for start and end time is 90 days. If no
     * start/end time params are specified then records for last 7 days will be returned.
     *获取用户的利息收集记录列表。开始和结束时间的最大间隔为90天。如果没有指定开始/结束时间参数，则返回最后7天的记录。
     * @param limit The max number of records could be returned. Default is 10 and max is 100 records.
     * @param from it is 'id'. The query begin at ‘from', and the default is 0.
     * @param direction PRE, NEXT, default is NEXT
     * @param startTime (milliseconds since UNIX epoch) records before start time will not be retrieved.
     * @param endTime (milliseconds since UNIX epoch) records after end time will not be retrieved.
     * @return List of account interest history
     */
    @GET(ACCOUNTS_INTEREST_HISTORY)
    Call<List<InterestHistoryResponse>> getAccountsInterestHistory(@Query("limit") Integer limit,
                                                                   @Query("from") Long from,
                                                                   @Query("direction") String direction,
                                                                   @Query("startTime") Long startTime,
                                                                   @Query("endTime") Long endTime);

    /**
     * Fee Info:
     * Get fee rate for an account
     *获取账户的费率信息
     * @return fee info
     */
    @GET(FEE_INFO)
    Call<FeeInfo> getFeeInfo();

    // subaccounts

    /**
     * Subaccount Info:
     * Get subaccounts for a primary account
     *用户账户组内所有账户的列表
     * @return a list of all the accounts within an Account Group for a user.
     */
    @GET(SUBACCOUNTS)
    Call<List<Subaccount>> getSubaccounts();

    /**
     * * 子账户余额：
     * * 根据货币和账户类型（现货和期货）获取账户组中每个账户的余额信息。
     * * 该功能仅对主用户有效。子账户用户可以调用/accounts/balances获取现货账户类型的余额，
     * * 并通过期货API概览获取其期货余额。
     * * @return 子账户余额
     */
    @GET(SUBACCOUNTS_BALANCES)
    Call<List<Subaccount>> getSubaccountBalances();

    /**
     * 根据ID获取子账户余额：
     * 根据货币和账户类型（现货和期货）获取账户组中指定外部账户ID的余额信息。
     * @param id 外部账户ID
     * @return 子账户余额
     */
    @GET(SUBACCOUNTS_BALANCES_BY_ID)
    Call<List<Subaccount>> getSubaccountBalancesById(@Path("id") String id);

    /**
     * Subaccount Transfer:
     * Transfer amount of currency from an account and account type to another account and account type among the
     * accounts in the account group. Primary account can transfer to and from any subaccounts as well as transfer
     * between 2 subaccounts across account types. Subaccount can only transfer to the primary account across account types.
     * @return transfer respose
     * * 子账户转账：
     * * 在账户组中的账户和账户类型之间转移货币金额。主账户可以向任何子账户转账，以及在两个子账户的账户类型之间转账。
     * * 子账户只能向主账户跨账户类型转账。
     * * @return 转账响应
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
     * * 子账户转账记录：
     * * 获取用户的转账记录列表。开始和结束时间的最大间隔为6个月。如果没有指定开始/结束时间参数，则返回最近7天的记录。
     * *
     * * @param limit 返回的记录最大数量。默认是100，最大是1000记录。
     * * @param from 它是'transferId'。查询从‘from’开始，默认是0。
     * * @param direction PRE, NEXT，默认是NEXT
     * * @param currency 转账的货币，如USDT。如果未指定，默认为所有货币。
     * * @param fromAccountId 转出账户的外部UID
     * * @param fromAccountType 转出账户类型（现货或期货）
     * * @param toAccountId 转入账户的外部UID
     * * @param toAccountType 转入账户类型（现货或期货）
     * * @param startTime （自UNIX纪元以来的毫秒数）开始时间之前的转账将不会被检索。
     * * @param endTime （自UNIX纪元以来的毫秒数）结束时间之后的转账将不会被检索。
     * * @return 转账记录列表
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
     * * 根据ID获取子账户转账记录：
     * * 获取与transferId相对应的单个转账记录
     * * @param id 转账ID
     * * @return 转账记录列表
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
     * * 存款地址：
     * * 获取用户的存款地址
     * *
     * * @param currency 显示存款地址的货币。如果传递null，则会显示所有货币的存款地址。
     * * @return 以货币为键，地址为值的存款地址映射

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
     * * 钱包活动记录：
     * * 在一个范围窗口内获取用户的调整、存款和提款活动历史记录
     * *
     * * @param start 活动的开始UNIX时间戳
     * * @param end 活动的结束UNIX时间戳
     * * @param activityType 活动类型：调整、存款和提款。如果未指定活动类型，则返回所有类型的活动。
     * * @return 钱包活动记录
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
     * * 新货币地址：
     * * 为一种货币创建新地址
     * *
     * * @param newCurrencyAddressRequest 创建新地址的详细请求信息
     * * @return 新地址
     */
    @POST(WALLETS_ADDRESS)
    Call<NewCurrencyAddressResponse> addNewCurrencyAddress(@Body NewCurrencyAddressRequest newCurrencyAddressRequest);

    /**
     * Withdraw Currency:
     * Immediately places a withdrawal for a given currency, with no email confirmation.
     *
     * @param withdrawCurrencyRequest request with details for withdrawal
     * @return withdrawal number
     * * 提现货币：
     * * 立即为给定货币发起一次提现，无需电子邮件确认。
     * *
     * * @param withdrawCurrencyRequest 提现的详细请求信息
     * * @return 提现编号
     */
    @POST(WALLETS_WITHDRAW)
    Call<WithdrawCurrencyResponse> withdrawCurrency(@Body WithdrawCurrencyRequest withdrawCurrencyRequest);

    /**
     * WithdrawV2 Currency:
     * Immediately places a withdrawal for a given currency, with no email confirmation.
     *
     * @param withdrawCurrencyRequest request with details for withdrawal
     * @return withdrawal number
     * * 提币V2:
     * * 立即进行一笔给定货币的提币操作，无需电子邮件确认。
     * *
     * * @param withdrawCurrencyRequest 提币请求的详细信息
     * * @return 提币编号
     */
    @POST(WALLETS_WITHDRAWV2)
    Call<WithdrawCurrencyResponse> withdrawCurrencyV2(@Body WithdrawCurrencyV2Request withdrawCurrencyRequest);

    // margin

    /**
     * Account Margin:
     * Get account margin information
     *
     * @param accountType The account type. Currently only SPOT is supported
     * @return account margin info
     * * 账户保证金:
     * * 获取账户的保证金信息
     * *
     * * @param accountType 账户类型。目前仅支持SPOT
     * * @return 账户保证金信息
     */
    @GET(MARGIN_ACCOUNT_MARGIN)
    Call<AccountMargin> getAccountMargin(@Query("accountType") String accountType);

    /**
     * Borrow Status:
     * Get borrow status of currencies
     *
     * @param currency currency name
     * @return borrow status of currencies
     * * 借款状态:
     * * 获取各种货币的借款状态
     * *
     * * @param currency 货币名称
     * * @return 货币的借款状态
     */
    @GET(MARGIN_BORROW_STATUS)
    Call<List<BorrowStatus>> getBorrowStatus(@Query("currency") String currency);

    /**
     * Maximum Buy/Sell Amount:
     * Get maximum and available buy/sell amount for a given symbol.
     *
     * @param symbol symbol name
     * @return maximum and available buy/sell amount
     * * 最大买卖金额:
     * * 获取给定符号的最大和可用买卖金额。
     * *
     * * @param symbol 符号名称
     * * @return 最大和可用的买卖金额
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
     * * 创建订单:
     * * 为账户创建一个订单。
     * *
     * * @param orderRequest 创建订单的详细信息
     * * @return 订单详情
     */
    @POST(ORDERS)
    Call<Order> placeOrder(@Body OrderRequest orderRequest);

    /**
     * Create Multiple Orders:
     * Create multiple orders via a single request. Max limit of 20 orders. Request parameter is an array of json objects with order details.
     *
     * @param orderRequests list of requests with details for creating an order
     * @return list of order details
     * * 创建多个订单:
     * * 通过单个请求创建多个订单。最大限制为20个订单。请求参数是包含订单详情的json对象数组。
     * *
     * * @param orderRequests 创建订单的详细信息列表
     * * @return 订单详情列表
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
     * * 替换取消订单:
     * * 取消一个现有的活跃订单（新订单或部分成交），并在相同的符号上根据现有订单的详情（除非通过新参数修改）下一个新订单。替换的订单可以修改价格、数量、金额、
     * * 类型、时间有效性以及允许借款字段。在路径中指定现有订单id；如果id是客户订单id，则以cid为前缀：例如cid:myId-1。proceedOnFailure标志旨在指定如果现有订单的取消失败是否继续下新订单。
     * *
     * * @param id 订单id
     * * @param orderRequest 替换订单的详细信息
     * * @return 订单详情
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
     * * 替换取消订单:
     * * 取消一个现有的活跃订单（新订单或部分成交），并在相同的符号上根据现有订单的详情（除非通过新参数修改）下一个新订单。替换的订单可以修改价格、数量、金额、
     * * 类型、时间有效性以及允许借款字段。在路径中指定现有订单id；如果id是客户订单id，则以cid为前缀：例如cid:myId-1。proceedOnFailure标志旨在指定如果现有订单的取消失败是否继续下新订单。
     * *
     * * @param clientOrderId 用户指定的订单id
     * * @param orderRequest 替换订单的详细信息
     * * @return 订单详情
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
     * * 获取活跃订单:
     * * 获取账户中的活跃订单列表
     * *
     * * @param symbol 交易的符号，如BTC_USDT。如果传递null，默认为所有符号。
     * * @param side 买卖方向
     * * @param from 它是'orderId'。查询从‘from’开始，在您第一次查询时它是0。
     * * @param direction PRE, NEXT
     * * @param limit 返回的最大订单数。
     * * @return 订单列表
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
     * * 订单详情:
     * * 通过指定orderId获取订单的状态
     * *
     * * @param id 订单id
     * * @return 订单详情
     */
    @GET(ORDERS_BY_ID)
    Call<Order> getOrderByOrderId(@Path("id") String id);

    /**
     * Order Details:
     * Get an order’s status by specifying clientOrderId
     *
     * @param clientOrderId user specified order id
     * @return order details
     * / * *
     * *订购详情:
     * *通过指定clientOrderId获取订单的状态  1
     * ＊
     * *@param clientOrderId用户指定的订单id
     * * @返回订单详情
     */

    @GET(ORDERS_BY_CID)
    Call<Order> getOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Cancel Order by Id:
     * Cancel an active order by orderId
     *
     * @param id order id
     * @return order details
     * *按Id取消订单:
     * *通过orderId取消已激活的订单
     * ＊ @param id订单id
     * * @返回订单详情
     */
    @DELETE(ORDERS_BY_ID)
    Call<CanceledOrder> cancelOrderByOrderId(@Path("id") String id);

    /**
     * Cancel Order by Id:
     * Cancel an active order by clientOrderId.
     *
     * @param clientOrderId user specified order id
     * @return order details
     * *按Id取消订单:
     * *取消clientOrderId的有效订单。
     * ＊
     * *@param clientOrderId用户指定的订单id
     * * @返回订单详情
     */
    @DELETE(ORDERS_BY_CID)
    Call<CanceledOrder> cancelOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Cancel Multiple Orders by Ids:
     * Batch cancel one or many active orders in an account by IDs.
     *
     * @param cancelOrdersByIdRequest request with orderIds and clientOrderIds to be cancelled
     * @return list of order details
     * *按id取消多个订单:
     * *批量取消一个或多个活跃的订单在一个帐户的id。
     * ＊
     * * @param cancelOrdersByIdRequest请求与orderid和clientorderid被取消
     * * @返回订单详细信息列表
     */
    @HTTP(method = "DELETE", path = ORDERS_CANCEL_BY_IDS, hasBody = true)
    Call<List<CanceledOrder>> cancelOrderByIds(@Body CancelOrdersByIdRequest cancelOrdersByIdRequest);

    /**
     * Cancel All Orders:
     * Batch cancel all orders in an account.
     *
     * @param cancelAllOrdersRequest request with details for batch cancel
     * @return list of order details
     * *取消所有订单:
     * *批量取消帐户中的所有订单。
     * ＊
     * * @param cancelAllOrdersRequest请求批量取消的详细信息
     * * @返回订单详细信息列表
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
     * *订单历史:
     * *获得历史订单在一个帐户的列表
     * ＊
     * * @param accountType SPOT是默认的，也是唯一支持的。
     * * @参数类型MARKET, LIMIT, LIMIT_MAKER(默认:所有类型)
     * * @参数方买入，卖出(默认:双方)
     * * @param symbol任何支持的符号(默认:所有符号)
     * * @param从一个'orderId'。查询从“from”开始。
     * * @param方向PRE, NEXT“from”之前或之后的方向。
     * * @param表示失败，已填写，已取消。PARTIALLY_CANCELED可以指定多个状态，用逗号分隔。(默认:所有状态)
     * * @param limit可以返回的订单的最大数量。(默认:100)
     * * @param hideCancel为真或假。是否不应该检索取消的订单。(默认值:false)
     * @param startTime(自UNIX纪元以来的毫秒)在startTime之前更新的订单将不会被检索。
     * * @param endTime(从UNIX纪元开始的毫秒)在endTime之后更新的订单将不会被检索。
     * * @返回订单列表
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
     * *设置死亡开关:
     * *设置一个定时器，取消所有常规和智能订单后，超时已过期。
     * ＊
     * * @param killSwitchRequest请求与超时详情创建终止开关
     * * @返回终止开关详细信息
     */
    @POST(KILL_SWITCH_POST)
    Call<KillSwitchResponse> setKillSwitch(@Body KillSwitchRequest killSwitchRequest);

    /**
     * Get Kill Switch:
     * Get status of kill switch.
     *
     * @return kill switch details
     * *获取Kill Switch:
     * *获取终止开关状态。
     * ＊
     * * @返回终止开关详细信息
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
     * *创建智能订单:
     * *为账户创建智能订单
     * ＊
     * * @param smartOrderRequest请求与创建智能订单的详细信息
     * * @返回智能订单详细信息
     */
    @POST(SMARTORDERS)
    Call<SmartOrder> placeSmartOrder(@Body SmartOrderRequest smartOrderRequest);

    /**
     * Order Details:
     * Get a smart order’s status. If smart order’s state is TRIGGERED, the response will include the triggered order’s data
     *
     * @param id smart order's id
     * @return smart order details
     * *订购详情:
     * *获取智能订单状态。如果智能订单的状态是触发的，响应将包含触发订单的数据
     * ＊
     * * @param id智能订单的id
     * * @返回智能订单详细信息
     */
    @GET(SMARTORDERS_BY_ID)
    Call<List<SmartOrder>> getSmartOrderByOrderId(@Path("id") String id);

    /**
     * Order Details:
     * Get a smart order’s status. If smart order’s state is TRIGGERED, the response will include the triggered order’s data
     *
     * @param clientOrderId smart order's user specified id
     * @return smart order details
     * *订购详情:
     * *获取智能订单状态。如果智能订单的状态是触发的，响应将包含触发订单的数据
     * ＊
     * * @param clientOrderId智能订单的用户指定id
     * * @返回智能订单详细信息
     */
    @GET(SMARTORDERS_BY_CID)
    Call<List<SmartOrder>> getSmartOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Open Orders:
     * Get a list of (pending) smart orders for an account
     *
     * @param limit The max number of smart orders could be returned.
     * @return list of smart order details
     * *未完成订单:
     * *获取一个账户的(未决)智能订单列表
     * ＊
     * * @param limit可以返回的智能订单的最大数量。
     * * @返回智能订单详细信息列表
     */
    @GET(SMARTORDERS)
    Call<List<SmartOrder>> getSmartOrders(@Query("limit") Integer limit);

    /**
     * Cancel Order by Id:
     * Cancel a smart order by its id.
     *
     * @param id smart order's id
     * @return smart order details
     * *按Id取消订单:
     * *取消智能订单的id。
     * ＊
     * *@param id智能订单的id
     * * @返回智能订单详细信息
     */
    @DELETE(SMARTORDERS_BY_ID)
    Call<CanceledOrder> cancelSmartOrderById(@Path("id") String id);

    /**
     * Cancel Order by Id:
     * Cancel a smart order by its clientOrderId.
     *
     * @param clientOrderId smart order's clientOrderId
     * @return smart order details
     * *按Id取消订单:
     * *取消智能订单的clientOrderId。
     * ＊
     * * @param clientOrderId智能订单的clientOrderId
     * * @返回智能订单详细信息
     */
    @DELETE(SMARTORDERS_BY_CID)
    Call<CanceledOrder> cancelSmartOrderByClientOrderId(@Path("client_order_id") String clientOrderId);

    /**
     * Cancel Multiple Orders by Id:
     * Batch cancel one or many smart orders in an account by IDs.
     *
     * @param cancelOrdersByIdRequest request with orderIds and clientOrderIds to
     * @return list of canceled order details
     * *按Id取消多个订单:
     * *批量取消一个或多个智能订单在一个帐户的id。
     * ＊
     * * @param cancelOrdersByIdRequest请求与orderid和clientorderid到
     * * @返回取消订单的详细信息列表
     */
    @HTTP(method = "DELETE", path = SMARTORDERS_CANCEL_BY_IDS, hasBody = true)
    Call<List<CanceledOrder>> cancelSmartOrderByIds(@Body CancelOrdersByIdRequest cancelOrdersByIdRequest);

    /**
     * Cancel All Orders:
     * Batch cancel all smart orders in an account
     *
     * @param cancelAllOrdersRequest request with details for batch canceling orders
     * @return list of canceled order details
     * *取消所有订单:
     * *批量取消所有智能订单在一个帐户
     * ＊
     * * @param cancelAllOrdersRequest请求批量取消订单的详细信息
     * * @返回取消订单的详细信息列表
     */
    @HTTP(method = "DELETE", path = SMARTORDERS, hasBody = true)
    Call<List<CanceledOrder>> cancelAllSmartOrders(@Body CancelAllOrdersRequest cancelAllOrdersRequest);

    /**
     * Cancel All Orders:
     * Batch cancel all smart orders in an account
     *
     * @return list of canceled order details
     * *取消所有订单:
     * *批量取消所有智能订单在一个帐户
     * ＊
     * * @返回取消订单的详细信息列表
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
     * *智能订单历史:
     * *获得历史智能订单在一个帐户的列表
     * ＊
     * * @param accountType SPOT是默认的，也是唯一支持的。
     * * @参数类型停止，STOP_LIMIT(默认:所有类型)
     * * @参数方买入，卖出(默认:双方)
     * * @param symbol任何支持的符号(默认:所有符号)
     * * @param从一个'智能订单'。查询从“from”开始。
     * * @param方向PRE, NEXT“from”之前或之后的方向。
     * * @param表示失败，已填写，已取消。PARTIALLY_CANCELED。可以指定多个状态，用逗号分隔。(默认:所有状态)
     * * @param limit可以返回的智能订单的最大数量。(默认:100)
     * * @param hideCancel为真或假。是否不应该检索取消的智能订单。(默认值:false)
     * * @param startTime(自UNIX纪元以来的毫秒)在startTime之前更新的智能订单将不会被检索。
     * * @param endTime(自UNIX纪元以来的毫秒)在endTime之后更新的智能订单将不会被检索。
     * * @返回智能订单列表
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
     * *贸易历史:
     * *获取一个账户的所有交易列表。
     * ＊
     * * @param限制默认值和最大值为100。
     * * @param结束时间(毫秒自UNIX纪元)交易结束时间后填充将不会被检索。
     * @param startTime(自UNIX纪元以来的毫秒)在startTime之前填充的交易将不会被检索。
     * * @param从一个“交易Id”。查询从“from”开始。
     * * @param方向PRE, NEXT“from”之前或之后的方向。
     * * @param符号用逗号分隔的一个或多个符号
     * * @交易返回列表
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
     * *按订单编号进行交易:
     * *获取由订单的orderId指定的所有交易的列表。
     * ＊
     * * @param orderId关联订单的id(订单的clientOrderId不支持)
     * * @交易返回列表
     */
    @GET(TRADES_BY_ID)
    Call<List<Trade>> getUserTradesByOrderId(@Path("orderId") Long orderId);

}
