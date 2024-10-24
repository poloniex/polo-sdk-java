# polo-sdk-java
###test
Java wrapper for Poloniex Exchange

DISCLAIMER:
```
USE AT YOUR OWN RISK. You should not use this code in production unless you fully understand its limitations. 
Even if you understand the code and its limitations, you may incur losses when using this code for trading. 
When used in combination with your own code, the combination may not function as intended, and as a result you may incur losses. 
Poloniex is not responsible for any losses you may incur when using this code.
```

## Features

- Support for REST and websocket endpoints
- Simple handling of authentication
- Response exception handling

## Getting Started

- Register an account with [Poloniex](<https://www.poloniex.com/signup>).
- Generate an [API Key](<https://poloniex.com/apiKeys>).
- [Get the source files](#source).
- See code [sample](src/test/java/com/poloniex/api/client/PoloClientSample.java).
- Refer to the [Javadocs](https://poloniex.github.io/polo-sdk-java/).
- For license refer to [LICENSE file](./LICENSE)

<a name="source"></a>Get the code files with git.

Clone the repo into the path you will be using:
```bash
git clone https://github.com/poloniex/polo-sdk-java
```

## Installation

### Using Maven
Add the following dependency to your Maven pom.xml file:
```xml
<dependency>
  <groupId>com.poloniex.api</groupId>
  <artifactId>polo-sdk-java</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Using Source Code
Clone the repo into the path you will be using:
```bash
git clone https://github.com/poloniex/polo-sdk-java
```
Build the jar and add to your local Maven repository using:
```bash
mvn install
```
## Usage
### Build Rest Client
| **Environment** | **Host URL** |
| -------- | -------- |
| *Production* | https://api.poloniex.com |
| *Sandbox* | https://sandbox-api.poloniex.com |
#### Public
```java
PoloRestClient poloniexApiClient = new PoloRestClient(POLO_HOST_URL);
```
#### Authenticated
```java
PoloRestClient poloniexApiClient = new PoloRestClient(POLO_HOST_URL, API_KEY, SECRET);
```
### Build Websocket
| **Environment** | **Type**  | **Host URL** |
| -------- | -------- | -------- |
| Production | Public | wss://ws.poloniex.com/ws/public |
| Production | Authenticated | wss://ws.poloniex.com/ws/private |
| Sandbox | Public | wss://sandbox-ws.poloniex.com/ws/public |
| Sandbox | Authenticated | wss://sandbox-ws.poloniex.com/ws/private |
#### Public
```java
OkHttpClient httpClient = new OkHttpClient.Builder().build();
PoloPublicWebsocket publicWebsocket = new PoloPublicWebsocket(httpClient, POLO_PUBLIC_WS_URL);
```
If you don't pass in an OkHttpClient, one will be created for you
```java
PoloPublicWebsocket publicWebsocket = new PoloPublicWebsocket(POLO_PUBLIC_WS_URL);
```
#### Authenticated
```java
OkHttpClient httpClient = new OkHttpClient.Builder().build();
PoloPrivateWebsocket privateWebsocket = new PoloPrivateWebsocket(httpClient, POLO_PRIVATE_WS_URL, API_KEY, SECRET);
```
If you don't pass in an OkHttpClient, one will be created for you
```java
PoloPrivateWebsocket publicWebsocket = new PoloPrivateWebsocket(POLO_PRIVATE_WS_URL);
```

## Examples

### REST API
#### Public Endpoint
```java
// get all markets
List<Market> allMarkets = poloniexApiClient.getMarkets();

// get info about BTC_USDT market
poloniexApiClient.getMarket("BTC_USDT")

// get current price of BTC_USDT
Price btcUsdtPrice = poloniexApiClient.getPrice("BTC_USDT");
```

#### Authenticated Endpoint
```java
// place buy order for BTC_USDT
Order order = poloniexApiClient.placeOrder("BTC_USDT", "BUY", "GTC", "LIMIT", "SPOT", "30000", "1", "", System.currentTimeMillis());

// get order id
String orderId = order.getId();

// get order info by id
Order returnedOrder = poloniexApiClient.getOrderByOrderId(orderId);

// cancel order by id
CanceledOrder canceledOrder = poloniexApiClient.cancelOrderByOrderId(orderId);
```

### Websockets
#### Public Channel
```java
PoloApiCallback<TickerEvent> callback = new PoloApiCallback<>() {
        @Override
        public void onResponse(PoloEvent<TickerEvent> response) {
            // logic to process ticker data here
            log.info(response.getData().get(0).getClose());
        }
        
        @Override
        public void onFailure(Throwable t) {
            log.warn("error",t);
        }
    };
    publicWebsocket.onTickerEvent(List.of("TRX_USDT"), callback);
}
```
Using callback class:
```java
publicWebsocket.onTickerEvent(List.of("TRX_USDT"), new PoloLoggingCallback<>());
```

#### Authenticated Channel
```java
privateWebsocket.onOrderEvent(List.of("all"), new PoloLoggingCallback<>());
```



