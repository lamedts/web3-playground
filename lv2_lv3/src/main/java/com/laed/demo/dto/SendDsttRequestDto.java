package com.laed.demo.dto;

import lombok.Data;

@Data
public class SendDsttRequestDto {
        String mnemonicPhrase;

        String amountInDstt;

        String receiverAddress;
}
