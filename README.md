# File structure
## Source directory
`./app/src/main/Kotlin/org/dopc/*`
#### Entry point: 
`./app/src/main/Kotlin/org/dopc/Index.kt`
#### Server configuration: 
`./app/src/main/kotlin/woltDeliveryFee/Server.kt`
#### Verify requested values are valid:
`./app/src/main/kotlin/woltDeliveryFee/ReqDataVerify.kt`
#### Verify response values are valid:
`./app/src/main/kotlin/woltDeliveryFee/ResDataVerify.kt`
#### Calculation process:
`./app/src/main/kotlin/woltDeliveryFee/deliveryFeeCalculations/CalculateTotalDeliveryFee.kt`

## Test directory:
`./app/src/test/kotlin/woltDeliveryFee/*`
`./app/src/test/kotlin/dopc/`


## Dependencies:
`./app/build.gradle.kts`

# Specification
https://github.com/woltapp/backend-internship-2025

# Prerequisite
[Gradle](https://gradle.org/)(8.12~) and Java(ver19~) installed.

# Testing
## Unit test: 
```bash
$ gradle test
```
## API running:
```bash
$ gradle run
```
Import `./API_testing/postman.json` into [POSTMAN](https://www.postman.com/).
and run the folder in Postman

# Build Jar file
```bash
$ gradle build
```
To run the Built jar file, you can use this command
```bash
java -jar ~/woltDeliveryFee/app/build/libs/app.jar
```

Author: hibiki.shibata@wolt.com
