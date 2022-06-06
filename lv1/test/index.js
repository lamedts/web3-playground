/*
100000000000000000000 wei = 100 eth
*/

const bip39 = require("bip39")
const HDWalletProvider = require("@truffle/hdwallet-provider");
const Web3 = require("web3");

const ganacheMnemonicPhrase = "cousin mean betray capital wagon include artefact gasp weekend million install brown"
const myMnemonicPhrase = "match party wagon success uphold ridge search exist sunset believe illegal bonus"
const mnemonic2 = "tourist wolf nut grocery venture pistol tool subway dove error negative oyster"
// const mnemonicPhrase = bip39.generateMnemonic()
// console.log(mnemonicPhrase)
const contractAddress = "0x715696b3AEA58920E1F5A4cF161e843405D2d384"
const contractAbi = require('../dstt-contract/contract_abi.json')

const targetAddress = "0x3126081ee598F6658eF6b1aA6A067484759DE4cA"


let main = () => {
    let provider = new HDWalletProvider({
        mnemonic: {
            phrase: mnemonic2
        },
        providerOrUrl: "https://data-seed-prebsc-1-s1.binance.org:8545/"
        // providerOrUrl: "http://localhost:7545"
    });

    const web3 = new Web3(provider);

    (async () => {
        accounts = await web3.eth.getAccounts()

        let account2 = accounts[1];
        let account1 = accounts[0];
        const defaultAmount = web3.utils.toWei("1000", 'ether') // to wei

        /**
         * list address & balance
         **/
        listAddressBalance(web3, [account1, account2])



        /**
         * send transaction
         **/
        // let receipt = await sendTransaction(web3, fromAddress, toAddress, '10000000')
        // console.log(receipt)

        /**
         * contract
         **/
        try {
            let contract = new web3.eth.Contract(contractAbi, contractAddress);

            const res = await contract.methods.totalSupply().call();
            console.log(res)

            const res2 = await contract.methods.balanceOf(account1).call();
            console.log(res2)

            // const res3 = await contract.methods
            //     .allowance(toAddress, contractAddress)
            //     .call();
            // console.log(res3)

            // const res4 = await contract.methods
            //     .increaseAllowance(toAddress, 1000)
            //     .send({
            //         from: fromAddress
            //     });
            // console.log(res4)

            // const res2 = await contract.methods
            //     .mint(toAddress, amount)
            //     .send({
            //         from: fromAddress
            //     });
            // console.log(res2)

            // const res5 = await contract.methods
            //     .mint(account1, "1000")
            //     .send({from: account1})
            // console.log(res5)

            mintToken(contract, account1, defaultAmount)

            // const res6 = await sendTransaction(contract, account1, targetAddress, defaultAmount)
            // console.log(res6)
        } catch (error) {
            console.log(error)
        }

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

let listAddressBalance = async (web3, accounts) => {
    await Promise.all(accounts.map(async (ele) => {
        balance = await web3.eth.getBalance(ele)
        web3.utils.fromWei(balance, 'ether')
        console.log(ele + " eth:" + web3.utils.fromWei(balance, 'ether'))
    }));
    console.log("\n")
}

let sendTransaction = (contract, fromAddress, toAddress, value) => {
    return contract.methods
    .transfer(toAddress, value)
    .send({from: fromAddress})
}

let mintToken = async (contract, account, value) => {
    const res5 = await contract.methods
        .mint(account, value)
        .send({from: account})
    console.log(res5)
}

main()
