
# Prerequisite
[Gradle](https://gradle.org/)(8.12~) and Java(ver19~) installed.

# Testing
## Unit test: 
```bash
$ gradle test
```
## Development server run:
```bash
$ gradle run
```

# Build Jar file:
```bash
$ gradle build
```
## Run jar file:
```bash
java -jar ~/2025internWolt/app/build/libs/app.jar
```

## TEST with Postman
Import `./API_testing/postman.json` into [POSTMAN](https://www.postman.com/).
-> run the folder in Postman for API tests


# Specification
https://github.com/woltapp/backend-internship-2025



# File structure
## Source directory
`./app/src/main/Kotlin/org/dopc/*`
#### Entry point: 
`./app/src/main/Kotlin/org/dopc/Index.kt`
#### Server configuration: 
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/ConfigClientServer.kt`
#### Main data stream in Client Server: 
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/IndexClientServer.kt`
#### Examine Client's request Data - if request params is in expected format:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/ClientRequestSorting/ClientReqParamValidate.kt`
#### Get All venue data from external service, using Home Assighment API:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/VenueSauceData/AllVenueDataAPI.kt`
#### Examine the data fetched in AllVenueDataAPI.kt, and take data which need for calculation:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/VenueSauceData/FindRequiredData.kt`
#### Json structure Home Assignment API - Dynamic:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/VenueSauceData/JsonStructureDynamic.kt`
#### Json structure Home Assignment API - Static
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/VenueSauceData/JsonStructureStatic.kt`
####  Calculate total prices and return all the data required to response:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/FeeCalculation/FeeCalcTotal.kt`
#### Calculation minimum surcharge:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/FeeCalculation/EachFeeCalculation/MinFeeSurcharge.kt`  
#### Calculation delivery distance fee and delivery distance in meter:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/FeeCalculation/EachFeeCalculation/DistanceFeeIndex.kt`
#### Find applied distance range for the distance, for distance based pricing:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/FeeCalculation/EachFeeCalculation/DistanceFee/FindDistanceFeeConfig.kt`
#### Calculate delivery distance(meter) from coordinates:
`./app/src/main/Kotlin/org/dopc/ClientServerConfig/FeeCalculation/EachFeeCalculation/DistanceFee/StraightDeliveryMeter.kt`

## Test directory:
`./app/src/test/kotlin/dopc/*`
`./app/src/test/kotlin/dopc/`

## Dependencies:
`./app/build.gradle.kts`

Code Owner: hibiki.shibata@wolt.com