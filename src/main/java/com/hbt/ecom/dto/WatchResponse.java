package com.hbt.ecom.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WatchResponse {
  private final BigDecimal totalPrice;

  public WatchResponse(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }
}
