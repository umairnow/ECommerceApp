package com.hbt.ecom.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbt.ecom.dto.RequestBodyWrapper;
import com.hbt.ecom.dto.WatchResponse;
import com.hbt.ecom.repository.WatchRepository;
import com.hbt.ecom.service.WatchService;

@RestController
@RequestMapping("/api/v1/watches")
public class WatchController {

  private final WatchRepository watchRepository;

  public WatchController(WatchRepository watchRepository) {
    this.watchRepository = watchRepository;
  }

  @GetMapping
  public ResponseEntity<Object> getAllWatches() {
    return ResponseEntity.ok(watchRepository.findAll());
  }

  @PostMapping
  public ResponseEntity<Object> watches(@RequestBody RequestBodyWrapper request) {
    BigDecimal totalPrice = new WatchService(watchRepository).checkout(request.getWatchIds());
    return ResponseEntity.ok(new WatchResponse(totalPrice));
  }
}
