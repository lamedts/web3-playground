package com.laed.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BalanceDto {

    String address;

    String tokenName;

    BigDecimal weiFactor;

    BigDecimal decimal;

    String amountInWei;
}
