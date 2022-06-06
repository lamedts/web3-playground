# Web3 Playground Level 1

## Description
A Interactive script to interact with BEP-20 contract on the BNB Chain (formerly BSC) testnet using web3js ad javascript.



## Setup
```shell
$ cd ./web3-playground/lv1
$ yarn
```
## Features
1. Create a account and get the account address by mnemonic phrase.

    Then get some BNB from Faucet which used to pay BEP20 token transaction fees (gas).

    BNB Chain Testnet Faucet url: https://testnet.binance.org/faucet-smart
```shell
$ lv1 git:(main) ✗ node main.js
? What do you want to do? Get Account Address
? What is mnemonic phrase? <phrase>
Account address: 0x90773fA98Fae792Fe2b08bD4CC8BA3503Df3F795
Finish
```

2. Get DSTT from a deployed contract

    https://testnet.bscscan.com/address/0x715696b3AEA58920E1F5A4cF161e843405D2d384#code
```shell
$ lv1 git:(main) ✗ node main.js
? What do you want to do? Issue token
? What is mnemonic phrase? <phrase>
? What amount (DSTT)? 1000
Finish
```

2. transfer 1000 DSTT tokens

    Receiver address: 0x3126081ee598F6658eF6b1aA6A067484759DE4cA
```shell
➜  lv1 git:(main) ✗ node main.js
? What do you want to do? Transfer
? What is mnemonic phrase? <phrase>
? What amount (DSTT)? 1000
? To Address:  0x3126081ee598F6658eF6b1aA6A067484759DE4cA
Finish
```
