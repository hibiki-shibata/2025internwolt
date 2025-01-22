# Wolt Backend Internship 2025 Project

## 📋 Prerequisites
- **[Gradle](https://gradle.org/)** (8.12 or higher)  
- **[Java](https://www.oracle.com/java/technologies/downloads/#java21)** (Version 19 or higher)

---

The **Ktor Netty Server** is configured to listen on:

- **Host**: `localhost`
- **Port**: `8000`

## 🛠️ Testing

### 1. Run Development Server
```bash
gradle run
```
### 2. Run Unit Test and Integration Test
```bash
gradle test
```

## 🚀 Build and Run

### 1. Build Jar file
```bash
gradle build
```
### 2. Run Jar file
```bash
java -jar ~/2025internWolt/app/build/libs/app.jar
```

---

## 📚 Project Specification
For more details about the project, visit the Wolt intern assignment page [Project Specification](https://github.com/woltapp/backend-internship-2025).

---

## 🔎 Source Dictionaly
```bash
app/
└── src/
    └── main/
        └── Kotlin/
            └── org/
                └── dopc/
                    ├── Index.kt                            # Entry point
                    ├── ClientServerConfig/
                    │   ├── ConfigClientServer.kt          # Server configuration
                    │   ├── IndexClientServer.kt           # Main data stream and response calculation
                    │   ├── ClientRequestSorting/
                    │   │   └── ClientReqParamValidate.kt  # Validates client request parameters
                    │   ├── VenueSauceData/
                    │   │   ├── AllVenueDataAPI.kt         # Fetches venue data from the API
                    │   │   ├── FindRequiredData.kt        # Extracts necessary data for calculations
                    │   │   ├── JsonStructureDynamic.kt    # Handles dynamic JSON structures
                    │   │   └── JsonStructureStatic.kt     # Handles static JSON structures
                    │   └── FeeCalculation/
                    │       ├── FeeCalcTotal.kt            # Calculates total fees
                    │       ├── EachFeeCalculation/
                    │           ├── MinFeeSurcharge.kt    # Calculates minimum surcharge
                    │           ├── DistanceFeeIndex.kt    # Calculates delivery distance fees
                    │           └── DistanceFee/
                    │               ├── FindDistanceFeeConfig.kt  # Finds applicable distance ranges
                    │               └── StraightDeliveryMeter.kt  # Calculates delivery distance


app/
└── src/
    └── test/
        └── kotlin/
            └── dopc/
                ├── IndextTest.kt                         # Integration tests(Make actual http request to the localhost:8000)

# Another tests(Unit Test)' files are corresponded to the structures of main dictionaly
```
Code Owner: hibiki.shibata@wolt.com
