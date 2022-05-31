/*
100000000000000000000 wei = 100 eth
*/

const bip39 = require("bip39")
const HDWalletProvider = require("@truffle/hdwallet-provider");
const Web3 = require("web3");

const ganacheMnemonicPhrase = "cousin mean betray capital wagon include artefact gasp weekend million install brown"
const myMnemonicPhrase = "match party wagon success uphold ridge search exist sunset believe illegal bonus"
// const mnemonicPhrase = bip39.generateMnemonic()
// console.log(mnemonicPhrase)
const contractAddress = "0x715696b3AEA58920E1F5A4cF161e843405D2d384"
const contractAbi = require('./contract_abi.json')

let main = () => {
    let provider = new HDWalletProvider({
        mnemonic: {
            phrase: myMnemonicPhrase
        },
        // providerOrUrl: "https://data-seed-prebsc-1-s1.binance.org:8545/"
        providerOrUrl: "http://localhost:7545"
    });

    const web3 = new Web3(provider);

    (async () => {
        accounts = await web3.eth.getAccounts()

        /** list address & balance **/
        // await listAddressBalance(web3, accounts)

        // let tokenAddress = 'asdf';
        // let toAddress = accounts[1];
        // let fromAddress = accounts[0];
        // let decimals = web3.utils.toBN(18);
        // let amount = web3.utils.toBN(100); // 100 eth
        // let value = amount.mul(web3.utils.toBN(10).pow(decimals)); // to wei

        // /** send transaction **/
        // // let receipt = await sendTransaction(web3, fromAddress, toAddress, '10000000')
        // // console.log(receipt)

        // /** contract **/
        // let minABI = [{
        //     "constant": false,
        //     "inputs": [{
        //             "name": "_to",
        //             "type": "address"
        //         },
        //         {
        //             "name": "_value",
        //             "type": "uint256"
        //         }
        //     ],
        //     "name": "transfer",
        //     "outputs": [{
        //         "name": "",
        //         "type": "bool"
        //     }],
        //     "type": "function"
        // }];
        // const networkId = await web3.eth.net.getId()
        let contract = new web3.eth.Contract(contractAbi, contractAddress);
        console.log(contract.options)
        // contract.methods.transfer(toAddress, value)
        //     .send({
        //         from: fromAddress
        //     })
        //     .on('transactionHash', function (hash) {
        //         console.log(hash);
        //     });


    })();

    provider.engine.stop();
}

let listAddressBalance = (web3, accounts) => {
    return Promise.all(accounts.map(async (ele) => {
        balance = await web3.eth.getBalance(ele)
        web3.utils.fromWei(balance, 'ether')
        console.log(ele + " " + web3.utils.fromWei(balance, 'ether'))
    }));
}

let sendTransaction = (web3, fromAddress, toAddress, value) => {
    return web3.eth.sendTransaction({
        from: fromAddress,
        to: toAddress,
        value: value
    })
}

main()
