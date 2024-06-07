package com.hbt.ecom.dto;

import java.util.List;

import lombok.Data;

@Data
public class RequestBodyWrapper {
  private List<Long> watchIds;
}
