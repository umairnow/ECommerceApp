package com.hbt.ecom.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hbt.ecom.model.Watch;
import com.hbt.ecom.repository.WatchRepository;

import jakarta.annotation.PostConstruct;

@Service
public class WatchService {
  public final WatchRepository watchRepository;

  public WatchService(WatchRepository watchRepository) {
    this.watchRepository = watchRepository;
  }

  // TODO: Remove this method after testing
  @PostConstruct
  public void populateTestData() {
    List<Watch> watches = List.of(
      new Watch("Rolex", BigDecimal.valueOf(100), BigDecimal.valueOf(200), 3),
      new Watch("Michael Kors", BigDecimal.valueOf(80), BigDecimal.valueOf(120), 2),
      new Watch("Swatch", BigDecimal.valueOf(50), null, null),
      new Watch("Casio", BigDecimal.valueOf(30), null, null)
                                       );
    watchRepository.saveAll(watches);
  }

  public List<Watch> getAllWatches() {
    return (List<Watch>) watchRepository.findAll();
  }

  public BigDecimal checkout(List<Long> watchIds) {
    return watchIds.stream()
                   .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                   .entrySet().stream()
                   .map(this::calculateTotalPriceForWatchId)
                   .filter(Optional::isPresent)
                   .map(Optional::get)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private Optional<BigDecimal> calculateTotalPriceForWatchId(Map.Entry<Long, Long> entry) {
    return watchRepository.findById(entry.getKey())
                          .map(watch -> calculateTotalPriceForWatch(watch, entry.getValue()));
  }

  private BigDecimal calculateTotalPriceForWatch(Watch watch, long count) {
    BigDecimal unitPrice = watch.getUnitPrice();
    BigDecimal discountPrice = watch.getDiscount();
    int quantityForDiscount = watch.getQuantityForDiscount() == null ? 0 : watch.getQuantityForDiscount();

    if (discountPrice != null && quantityForDiscount > 0) {
      long discountedSets = count / quantityForDiscount;
      long remainingWatches = count % quantityForDiscount;

      BigDecimal priceForDiscountedSets = discountPrice.multiply(BigDecimal.valueOf(discountedSets));
      BigDecimal priceForRemainingWatches = unitPrice.multiply(BigDecimal.valueOf(remainingWatches));

      return priceForDiscountedSets.add(priceForRemainingWatches);
    } else {
      return unitPrice.multiply(BigDecimal.valueOf(count));
    }
  }
}
