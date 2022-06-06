# Web3 Playground Level 2 & Level 3

## Description
A Rest APIs to interact with BEP-20 contract on the BNB Chain (formerly BSC) testnet using java, springboot, web3j and swagger api


## Feature
### 1. create an account using a mnemonic phrase
 * In order to pay gas, need to get BNB BNB Chain Testnet Faucet after creaete account
### 2. issue testnet DSTT tokens to default account
### 3. transfer DSTT from default account
### 4. generate mnemoic phrase and create account
### 5. transfer DSTT to other account with same mnemoic phrase

### P.S. Swagger api document
http://localhost:8080/swagger-ui/index.html#/

## Setup
1. download abi from a deployed contract

    https://testnet.bscscan.com/address/0x715696b3AEA58920E1F5A4cF161e843405D2d384#code
2. use web3j cli to generate abi wrappered class
```
web3j generate solidity \
-a=src/main/resources/DrafMeme.abi \
-o src/main/java \
-p com.laed.demo
```

## How To Start Server
1. package jar file
```shell
$ mvn clean package
```
2. execute jar
```
$ java -jar target/demo-0.0.1-SNAPSHOT.jar com.laed.demo.Web3Application
```



