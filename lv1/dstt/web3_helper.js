/**
 * 1 eth = 1^18 gwei
 * 1 dstt = 1^18 gwei
 */

const HDWalletProvider = require("@truffle/hdwallet-provider");
const Web3 = require("web3");

const contractAddress = "0x715696b3AEA58920E1F5A4cF161e843405D2d384"
const contractAbi = require('./contract_abi.json')
const provderUrl = "https://data-seed-prebsc-1-s1.binance.org:8545/"

exports.initWeb3 = (mnemonicPhrase) => {
    let provider = new HDWalletProvider({
        mnemonic: {
            phrase: mnemonicPhrase
        },
        providerOrUrl: provderUrl
    });

    return {
        web3: new Web3(provider),
        provider
    }
}

exports.listAddressBalance = async (web3, accounts) => {
    await Promise.all(accounts.map(async (ele) => {
        balance = await web3.eth.getBalance(ele)
        web3.utils.fromWei(balance, 'ether')
        console.log(ele + " eth:" + web3.utils.fromWei(balance, 'ether'))
    }));
}

exports.transfer = (contract, fromAddress, toAddress, value) => {
    try {
        return contract.methods
            .transfer(toAddress, value)
            .send({
                from: fromAddress
            })
    } catch (error) {
        console.log(`Transfer fail: ${error}`)
    }
}

exports.mintToken = async (contract, account, value) => {
    try {
        const res5 = await contract.methods
            .mint(account, value)
            .send({
                from: account
            })
    } catch (error) {
        console.log("Cannot get token")
    }
}

exports.getContract = (web3) => {
    return new web3.eth.Contract(contractAbi, contractAddress);
}

exports.getFirstAccount = async (web3) => {
    accounts = await web3.eth.getAccounts()
    return accounts[0]
}
