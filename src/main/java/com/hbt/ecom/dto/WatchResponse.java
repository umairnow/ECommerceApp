package com.hbt.ecom.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WatchResponse {
  private final BigDecimal price;

  public WatchResponse(BigDecimal totalPrice) {
    this.price = totalPrice;
  }
}
