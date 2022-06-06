package com.laed.demo.dstt;

import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class DsttHelper {

    public static BigDecimal getWeiFactor() {
        return Convert.Unit.ETHER.getWeiFactor();
    }

    public static BigDecimal getDecimals() {
        return BigDecimal.valueOf(Math.log10(Convert.Unit.ETHER.getWeiFactor().doubleValue()));
    }

    public static BigDecimal convertFromWei(String amount) {
        return Convert.fromWei(amount, Convert.Unit.ETHER);
    }

    public static BigDecimal convertToWei(String amount) {
        return Convert.toWei(amount, Convert.Unit.ETHER);
    }
}
