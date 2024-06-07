package com.hbt.ecom.model;

import java.math.BigDecimal;

import org.springframework.lang.NonNull;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Watch {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NonNull
  private String name;

  @NonNull
  private BigDecimal unitPrice;

  private BigDecimal discount;

  private Integer quantityForDiscount;

  public Watch(String name, BigDecimal unitPrice, BigDecimal discount, Integer quantityForDiscount) {
    this.name = name;
    this.unitPrice = unitPrice;
    this.discount = discount;
    this.quantityForDiscount = quantityForDiscount;
  }
}
