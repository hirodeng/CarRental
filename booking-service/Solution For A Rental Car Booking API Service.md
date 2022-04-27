## **Solution For A Rental Car Booking API Service**

### 1. **Process Of Car Booking**

#### Sign In

First of all, the customer should have a account to access the APIs. Therefore, we should provide APIs for getting sign up, sign in and sign out. 

#### Make An Order

After signing in, the customer will check all the available cars in the period he/she selects. For practical purpose, we only allow the customers to reserve car within the future 3 months. If there is an available car, he/she can make an order, making the car unavailable for the selected period. 

#### Pay The Order

After making an order, the customer should continue to pay the order. Once the order is paid, the customer is able to pick up the car on the booking day. If the customer canceled the order, the selected car will become available again. 

#### Complete The Order

On the return day, the customer should return the car to the company. The order will then complete.



### 2. How To Access The Service

The service is deployed on Azure. Please visit the website: https://booking-service-1650978274481.azurewebsites.net/login.

You should register and login before accessing other APIs.

After login, you may browse and test all APIs with swagger: https://booking-service-1650978274481.azurewebsites.net/swagger-ui.html.

The code of the service is located at: https://github.com/hirodeng/CarRental/tree/main/booking-service



### 3. **API Specifications**

All APIs have a common response json format:

```json
{
    "code": 0, // 0 - normal, 1 - error
    "errMsg": "", // show error msg when error happens
    "data": null // the response content of the API
}
```

All APIs with post method accept json as  the request body format.



#### User

**Url**: /users/signup

**Description**: Create a new user, return the user name

**Method**: POST

**Authorization**: No authorization

**Request body**:

```json
{
  "name": "username",
  "password": "password"
}
```

**Response content:**

```json
"username"
```



**Url**: /users/signin

**Description**: Sign in, return the user name

**Method**: POST

**Authorization**: No authorization

**Request body**:

```json
{
  "name": "username",
  "password": "password"
}
```

**Response content:**

```json
"username"
```



#### Inventory

**Url**: /inventory/availableCars

**Description**: Query all inventories at the selected period

**Method**: POST

**Authorization**: Need authorization

**Request body**:

```json
{
  "carModel": "BMW 650",
  "endDate": "2022-05-03T20:47:20.904Z",
  "startDate": "2022-05-01T20:47:20.904Z"
}
```

**Response content:**

```json
[
    {
      "carModel": "BMW 650",
      "date": "2022-05-02T00:00:00.000Z",
      "id": 1,
      "numInStock": 2,
      "price": 400,
      "updatedAt": "2022-04-27T20:47:20.916Z"
    },
    {
      "carModel": "BMW 650",
      "date": "2022-05-03T00:00:00.000Z",
      "id": 2,
      "numInStock": 2,
      "price": 400,
      "updatedAt": "2022-04-27T20:47:20.916Z"
    }
]
```



#### Order

**Url**: /orders/

**Description**: Query all your orders

**Method**: GET

**Authorization**: Need authorization

**Response content:**

```json
[
    {
      "id": 1,
      "userId": 2,
      "carModel": "BMW 650",
      "startDate": "2022-04-27T20:51:37.000+00:00",
      "endDate": "2022-04-28T20:51:37.000+00:00",
      "status": 100,
      "amount": 40000,
      "createdAt": "2022-04-27T20:52:07.000+00:00",
      "updatedAt": "2022-04-27T20:52:07.000+00:00"
    }
  ]
```



**Url**: /orders/{orderId}/createOrder

**Description**: Create an order, return the created order

**Method**: POST

**Authorization**: Need authorization

**Request body**:

```json
{
  "carModel": "BMW 650",
  "endDate": "2022-04-28T20:51:37.227Z",
  "startDate": "2022-04-27T20:51:37.227Z"
}
```

**Response content:**

```json
[
   {
        "id": 1,
        "userId": 2,
        "carModel": "BMW 650",
        "startDate": "2022-04-27T20:51:37.227+00:00",
        "endDate": "2022-04-28T20:51:37.227+00:00",
        "status": 100,
        "amount": 40000,
        "createdAt": "2022-04-27T20:52:06.712+00:00",
        "updatedAt": "2022-04-27T20:52:06.712+00:00"
  }
]
```



**Url**: /orders/{orderId}/payOrder

**Description**: Pay an order, return the paid order

**Method**: POST

**Authorization**: Need authorization

**Response content:**

```json
{
    "id": 1,
    "userId": 2,
    "carModel": "BMW 650",
    "startDate": "2022-04-27T20:51:37.000+00:00",
    "endDate": "2022-04-28T20:51:37.000+00:00",
    "status": 120,
    "amount": 40000,
    "createdAt": "2022-04-27T20:52:07.000+00:00",
    "updatedAt": "2022-04-27T20:57:13.318+00:00"
}
```



**Url**: /orders/{orderId}/cancelOrder

**Description**: Cancel an order, return the canceled order

**Method**: POST

**Authorization**: Need authorization

**Response content:**

```json
{
    "id": 1,
    "userId": 2,
    "carModel": "BMW 650",
    "startDate": "2022-04-27T20:51:37.000+00:00",
    "endDate": "2022-04-28T20:51:37.000+00:00",
    "status": 50,
    "amount": 40000,
    "createdAt": "2022-04-27T20:52:07.000+00:00",
    "updatedAt": "2022-04-27T20:57:13.318+00:00"
}
```



**Url**: /orders/{orderId}/completeOrder

**Description**: Complete an order, return the completed order

**Method**: POST

**Authorization**: Need authorization

**Response content:**

```json
{
    "id": 1,
    "userId": 2,
    "carModel": "BMW 650",
    "startDate": "2022-04-27T20:51:37.000+00:00",
    "endDate": "2022-04-28T20:51:37.000+00:00",
    "status": 200,
    "amount": 40000,
    "createdAt": "2022-04-27T20:52:07.000+00:00",
    "updatedAt": "2022-04-27T20:57:13.318+00:00"
}
```



 

 

 

 