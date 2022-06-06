package com.laed.demo;

import com.laed.demo.config.Web3Config;
import com.laed.demo.dstt.DrafMeme;
import com.laed.demo.dstt.DsttHelper;
import com.laed.demo.dto.BalanceDto;
import com.laed.demo.dto.GenerateMnemonicDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import static org.web3j.crypto.Bip32ECKeyPair.HARDENED_BIT;

@Slf4j
@Component
public class Web3Service {

    private final String contractAddress;
    private final Web3j web3;
    private final ContractGasProvider staticGasProvider;

    private  String toAddress = "0x3126081ee598F6658eF6b1aA6A067484759DE4cA";

    public Web3Service(@Autowired Web3Config web3Config) {
        // gasLimit = 900 000, gasPrice =  1 0000 000 000
        // DefaultGasProvider.GAS_LIMIT 9_000_000L
        // DefaultGasProvider.GAS_PRICE 4_100_000_000L
        var gasLimit = DefaultGasProvider.GAS_LIMIT; //BigInteger.valueOf(21_000L);
        var gasPrice = Convert.toWei("10", Unit.GWEI)
            .toBigInteger();
        log.info(String.format("gasLimit: %s, gasPrice: %s", gasLimit, gasPrice));
        web3 = Web3j.build(new HttpService(web3Config.getHostUrl()));
        staticGasProvider = new StaticGasProvider(gasPrice, gasLimit);
        contractAddress = web3Config.getDsttContractAddress();
    }

    public String generateMnemonic() {
        var initialEntropy = new byte[16];
        var secureRandom = new SecureRandom();
        secureRandom.nextBytes(initialEntropy);
        return MnemonicUtils.generateMnemonic(initialEntropy);
    }

    public boolean issueDsttToken(Credentials receiver, String amount) throws Exception {
        log.info("issueDsttToken");
        var contract = DrafMeme.load(contractAddress, web3, receiver, staticGasProvider);
        var amountInWei = DsttHelper.convertToWei(amount);
        log.info(String.format("requesting %s wei", amountInWei));
        var response = contract.mint(receiver.getAddress(), amountInWei.toBigInteger()).send();
        log.info(response.toString());
        return true;
    }

    public boolean sendDsttToken(Credentials sender, String receiver, String amount) throws Exception {
        log.info("sendDsttToken");
        var contract = DrafMeme.load(contractAddress, web3, sender, staticGasProvider);
        var amountInWei = DsttHelper.convertToWei(amount);
        contract.transfer(receiver, amountInWei.toBigInteger()).send();
        return true;
    }

    public BalanceDto getDsttBalance(Credentials credentials) throws Exception {
        log.info("getDsttBalance");
        var contract = DrafMeme.load(contractAddress, web3, credentials, staticGasProvider);
        var balanceInWei = contract.balanceOf(credentials.getAddress()).send().toString();
        var tokenName = contract.symbol().send();
        var weiFactor = DsttHelper.getWeiFactor();
        var decimal = DsttHelper.getDecimals();
        log.info(String.format("balance: %s wei", balanceInWei));
        return new BalanceDto(credentials.getAddress(), tokenName, weiFactor, decimal, balanceInWei);
    }

    public BalanceDto getDsttBalance(String mnemonic, Integer accountIndex) throws Exception {
        log.info("getDsttBalance");
        var credentials = getAccountByMnemonicAndIndex(mnemonic, accountIndex);
        return getDsttBalance(credentials);
    }

    public BigInteger getEthBalance(String mnemonic) throws Exception {
        log.info("getEthBalance");
        var credentials = getAccountByMnemonicAndIndex(mnemonic, 0);
        var balanceWei =
                web3.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                        .sendAsync()
                        .get();
        var balanceInEther = DsttHelper.convertFromWei(balanceWei.getBalance().toString());
        log.info(String.format("balance: %s ether", balanceInEther));
        return balanceInEther.toBigInteger();
    }

    public Credentials getAccountByMnemonicAndIndex(String mnemonic, Integer index) {
        // m / purpose' / coin_type' / account' / change / address_index
        // purpose' = 44' (fixed, represent BIP44)
        // coin_type' (Bitcoin = 0' | Ethereum  = 60')
        // m/44'/60'/0'/0/0 = first eth account

        final int[] firstIndex = {44 | HARDENED_BIT, 60 | HARDENED_BIT, HARDENED_BIT, 0, index};
        var seed = MnemonicUtils.generateSeed(mnemonic, "");
        var masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        var childKeypair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, firstIndex);
        return Credentials.create(childKeypair);
    }
}
