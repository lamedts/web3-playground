package com.laed.demo.dto;

import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenerateAndTransferRequestDto {
  String mnemonicPhrase;
}
