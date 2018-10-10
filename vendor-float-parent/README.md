# float-api

## API

### Assumptions 

* The float service is designed for GBP only

### Design 

* Initialising the float with coins at any time will replace the current float
* The float service does not persist the float in anyway, it is up to the API user to manage persistence or implement a persistent version of _FloatService_
* Coins calculated for the change are the optimum denominations, the API is not clever enough to work out that the change can be served in other denominations
* Insufficient coins in the exact denominations required will result in the float remaining unchanged and an exception thrown
* Uses a _double_ value for change issuing, the API expects the value to be at most 2 decimal places if not then it is rounded, zero or negative values are allowed but no change will be issues

### Denominations

When depositing coins to the float service you specify the number of each coin denomination that you are depositing, the supported values are:

* ONE_PENCE
* TWO_PENCE
* FIVE_PENCE
* TEN_PENCE
* TWENTY_PENCE
* FIFTY_PENCE
* ONE_POUND

## Development

The API is writtin in Java using JDK 8.  It requires Apache Maven version 3+ to build.

### Building the code

To build the application run the following from the parent pom directory

```
mvn clean verify
```

This will build and unit test the API


### Running the application/test-harness

To exercise the functionality of the Float API the API has been wired into a simple Spring based web application allowing you to interact
with the API to perform the following functions:

* Initialise the float
* Deposit money into the float
* Issue change from the float
* Display the contents of the float

The application resides under _vendor-float-app_ and can be started by running

```
mvn spring-boot:run
```

or, from the target directory running

```
java -jar vendor-float-app-0.0.1-SNAPSHOT.jar
```



The service is available on port 8081, http://localhost:8081/float-service/


### Example endpoints

**Initialise**

```
curl -X POST \
  http://localhost:8081/float-service/initialise \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 8d7615ee-0696-4746-80d0-6f42d5b3eae0' \
  -H 'cache-control: no-cache' \
  -d '{
  "coins": {
    "denominations": {
      "ONE_PENCE": 1000,
      "TWO_PENCE": 1000,
      "FIVE_PENCE": 200,
      "TEN_PENCE": 100,
      "TWENTY_PENCE": 100,
      "FIFTY_PENCE": 100,
      "ONE_POUND": 100
    }
  }
}'
```


**Deposit**

```
curl -X POST \
  http://localhost:8081/float-service/deposit \
  -H 'Content-Type: application/json' \
  -d '{
    "coins": {
        "denominations": {"ONE_PENCE": 10}
    }
}'
```

**Issue Change**

```
curl -X POST \
  http://localhost:8081/float-service/issueChange \
  -H 'Content-Type: application/json' \
  -d '{
    "value": 10.99
}'
```

**Get Float**

```
curl -X GET \
  http://localhost:8081/float-service/float \
  -H 'cache-control: no-cache'
```
