### How to start the application?

An application for calculating rewards per transaction.
For starting this application please import the application in any of the preferred IDE (Intellij, Eclipse, STS, etc.)

Application can be started by executing the main method present in class CustomerRewardsApplication.


### API Specification

All the APIs are available on swagger via the link : `http://localhost:8080/swagger-ui.html`

Applications health can be monitored at : `http://localhost:8080/actuator/health`

APIs for fetching the customer specific rewards :
```
curl -X 'GET' \
  'http://localhost:8080/api/rewards?customerId=1' \
  -H 'accept: */*'
  
Response:

{
  "customerId": 1,
  "rewards": {
    "Aug-2022": 190,
    "Jul-2022": 180,
    "Sep-2022": 130
  },
  "totalRewards": 500
}
```

APIs for fetching rewards for all transaction :
```
curl -X 'GET' \
  'http://localhost:8080/api/rewards/all' \
  -H 'accept: */*'
  
Response:

[
  {
    "customerId": 1,
    "rewards": {
      "Aug-2022": 190,
      "Jul-2022": 180,
      "Sep-2022": 130
    },
    "totalRewards": 500
  },
  {
    "customerId": 2,
    "rewards": {
      "Oct-2022": 50
    },
    "totalRewards": 50
  }
]
```

Create a new transaction:

```
curl -X 'POST' \
  'http://localhost:8080/api/transaction' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
    "customerId":2,
    "productName":"Some product",
    "amount":100.0
}'

```