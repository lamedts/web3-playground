package com.laed.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class GenerateMnemonicDto {
    String mnemonicPhrase;

    String accountAddress;
}
