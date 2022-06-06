package com.laed.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "web3")
public class Web3Config {
  String hostUrl;

  String dsttContractAddress;

  String gasPriceInGwei;
}
