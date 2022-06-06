package com.laed.demo;

import com.laed.demo.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Web3 Demo")
@RequestMapping("/api")
@Slf4j
public class Web3Controller {

    private final Web3Service web3Service;

    public Web3Controller(@Autowired Web3Service web3Service) {
        this.web3Service = web3Service;
    }

    @Operation(summary = "Generate Mnemonic and account")
    @PostMapping("/generate-mnemonic-and-account")
    public GenerateMnemonicDto generateMnemonic() {
        var mnemonic = web3Service.generateMnemonic();
        var account = web3Service.getAccountByMnemonicAndIndex(mnemonic, 0);
        return new GenerateMnemonicDto(mnemonic, account.getAddress());
    }

    @Operation(summary = "Create Account")
    @PostMapping("/create-account")
    public GenerateAccountDto createAccount(@RequestBody CreateAccountRequestDto createAccountRequestDto) {
        var mnemonic = createAccountRequestDto.getMnemonicPhrase();
        var account = web3Service.getAccountByMnemonicAndIndex(mnemonic, 0);
        return new GenerateAccountDto(account.getAddress());
    }

    @Operation(summary = "Issue DSTT Token")
    @PostMapping("/issue-dstt")
    public CommonResponse issueDsttToken(@RequestBody IssueDsttRequestDto issueDsttRequestDto)
            throws Exception {
        var receiver = web3Service.getAccountByMnemonicAndIndex(issueDsttRequestDto.getMnemonicPhrase(), 0);
        var actionSuccess = web3Service.issueDsttToken(receiver, issueDsttRequestDto.getAmountInDstt());
        if (actionSuccess){
            return new CommonResponse().setStatus("OK");
        }
        return new CommonResponse().setStatus("Fail");
    }

    @Operation(summary = "Send DSTT to specific address")
    @PostMapping("/send-dstt")
    public CommonResponse sendDsttToken(@RequestBody SendDsttRequestDto sendDsttRequestDto)
            throws Exception {
        var account =
                web3Service.getAccountByMnemonicAndIndex(sendDsttRequestDto.getMnemonicPhrase(), 0);
        var actionSuccess = web3Service.sendDsttToken(
                account,
                sendDsttRequestDto.getReceiverAddress(),
                sendDsttRequestDto.getAmountInDstt());
        if (actionSuccess){
            return new CommonResponse().setStatus("OK");
        }
        return new CommonResponse().setStatus("Fail");
    }

    @Operation(summary = "Get Balance in first account")
    @PostMapping("/get-dstt-balance-first-account")
    public BalanceDto getDsttBalance(@RequestBody GetBalanceRequestDto getBalanceRequestDto)
            throws Exception {
        return web3Service.getDsttBalance(getBalanceRequestDto.getMnemonicPhrase(), 0);
    }

    @Operation(summary = "Randomly generate and transfer DSTT to 2 other account")
    @PostMapping("/generate-and-transfer")
    public GenerateAndTransferDto generateAndTransfer(@RequestBody GenerateAndTransferRequestDto generateAndTransferRequestDto)
        throws Exception {
        var generateAndTransferDto = new GenerateAndTransferDto();
        try {
            var mnemonic = generateAndTransferRequestDto.getMnemonicPhrase();
            var account0 = web3Service.getAccountByMnemonicAndIndex(mnemonic, 0);
            var account1 = web3Service.getAccountByMnemonicAndIndex(mnemonic, 1);
            var account2 = web3Service.getAccountByMnemonicAndIndex(mnemonic, 2);
            web3Service.sendDsttToken(account0, account1.getAddress(), "1000");
            web3Service.sendDsttToken(account0, account2.getAddress(), "1000");
            generateAndTransferDto.setBalanceDtoList(
                    List.of(
                        web3Service.getDsttBalance(account0),
                        web3Service.getDsttBalance(account1),
                        web3Service.getDsttBalance(account2)));
        }catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return generateAndTransferDto;
    }
}
