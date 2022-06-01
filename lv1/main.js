const inquirer = require('inquirer');
const web3Helper = require('./web3_helper.js');

function main() {
    inquirer.prompt([{
        type: 'list',
        name: 'action',
        message: 'What do you want to do?',
        choices: ['Issue token', 'Transfer'],
    }, {
        type: 'input',
        name: 'mnemonicPhrase',
        message: "What is mnemonic phrase?",
        default: "match party wagon success uphold ridge search exist sunset believe illegal bonus",
        validate(input) {
            if (input !== "") {
                return true;
            }
            throw Error('Please valid mnemonic phrase');
        },
    }]).then(async (answers) => {
        let {
            web3,
            provider
        } = web3Helper.initWeb3(answers.mnemonicPhrase)
        let account = await web3Helper.getFirstAccount(web3)
        let contract = web3Helper.getContract(web3)
        if (answers.action === 'Transfer') transfer(account, contract, web3)
        if (answers.action === 'Issue token') issueToken(account, contract, web3)
        console.log("Finish")
        provider.engine.stop()
    });
}

function transfer(account, contract, web3) {
    inquirer.prompt([{
        type: 'input',
        name: 'What amount (DSTT)?',
        message: "amount",
        default: "1000",
        validate(input) {
            if (/^[1-9]\d*$/g.test(input)) {
                return true;
            }
            throw Error('Please valid amount');
        },
    }, {
        type: 'input',
        name: 'To Address: ',
        message: "toAddress",
        default: "0x3126081ee598F6658eF6b1aA6A067484759DE4cA",
        validate(input) {
            if (true) {
                return true;
            }
            throw Error('Please valid address');
        },
    }]).then(async (answers) => {
        let value = web3.utils.toWei(answers.amount, 'ether')
        await web3Helper.transfer(contract, account, answers.toAddress, value)
    });
}


function issueToken(account, contract, web3) {
    inquirer.prompt({
        type: 'input',
        name: 'amount',
        message: "What amount (DSTT)?",
        default: "1000",
        validate(input) {
            if (/^[1-9]\d*$/g.test(input)) {
                return true;
            }
            throw Error('Please valid amount');
        },
    }).then(async (answers) => {
        let value = web3.utils.toWei(answers.amount, 'ether')
        await web3Helper.mintToken(contract, account, value)
    });
}

main();
