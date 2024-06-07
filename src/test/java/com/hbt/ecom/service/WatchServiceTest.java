package com.hbt.ecom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hbt.ecom.model.Watch;
import com.hbt.ecom.repository.WatchRepository;

@ExtendWith (MockitoExtension.class)
public class WatchServiceTest {

  @Mock
  private WatchRepository watchRepository;

  @InjectMocks
  private WatchService watchService;

  private Watch mockWatch(BigDecimal unitPrice, BigDecimal discount, Integer quantityForDiscount) {
    Watch watch = mock(Watch.class);
    Mockito.when(watch.getUnitPrice()).thenReturn(unitPrice);
    if (discount != null) {
      Mockito.when(watch.getDiscount()).thenReturn(discount);
    }
    if (quantityForDiscount != null) {
      Mockito.when(watch.getQuantityForDiscount()).thenReturn(quantityForDiscount);
    }
    return watch;
  }

  @Test
  public void testCheckout_emptyWatchList() {
    List<Long> watchIds = Collections.emptyList();
    BigDecimal expectedTotal = BigDecimal.ZERO;

    BigDecimal total = watchService.checkout(watchIds);

    assertEquals(expectedTotal, total);
  }

  @Test
  public void testCheckout_singleWatchNoDiscount() {
    BigDecimal unitPrice = BigDecimal.valueOf(50);
    Watch watch = mockWatch(unitPrice, null, null);

    Mockito.when(watchRepository.findById(anyLong())).thenReturn(Optional.of(watch));

    BigDecimal total = watchService.checkout(Collections.singletonList(anyLong()));

    assertEquals(unitPrice, total);
  }

  @Test
  public void testCheckout_multipleWatchesNoDiscount() {
    BigDecimal unitPrice1 = BigDecimal.valueOf(50);
    BigDecimal unitPrice2 = BigDecimal.valueOf(30);

    Watch watch1 = mockWatch(unitPrice1, null, null);
    Watch watch2 = mockWatch(unitPrice2, null, null);

    Mockito.when(watchRepository.findById(3L)).thenReturn(Optional.of(watch1));
    Mockito.when(watchRepository.findById(4L)).thenReturn(Optional.of(watch2));

    BigDecimal expectedTotal = unitPrice1.add(unitPrice2);

    BigDecimal total = watchService.checkout(Arrays.asList(3L, 4L));

    assertEquals(expectedTotal, total);
  }

  @Test
  public void testCheckout_singleWatchWithDiscount() {
    Watch watch = mockWatch(BigDecimal.valueOf(100), BigDecimal.valueOf(200), 3);

    Mockito.when(watchRepository.findById(anyLong())).thenReturn(Optional.of(watch));

    BigDecimal expectedTotal = BigDecimal.valueOf(100);

    BigDecimal total = watchService.checkout(List.of(1L));

    assertEquals(expectedTotal, total);
  }

  @Test
  public void testCheckout_multipleWatchesWithDiscount() {
    BigDecimal unitPrice = BigDecimal.valueOf(100);
    BigDecimal discountPrice = BigDecimal.valueOf(200);
    int quantityForDiscount = 3;

    Watch watch = mockWatch(unitPrice, discountPrice, quantityForDiscount);
    Mockito.when(watchRepository.findById(1L)).thenReturn(Optional.of(watch));

    BigDecimal total = watchService.checkout(List.of(1L, 1L, 1L));

    assertEquals(discountPrice, total);
  }

  @Test
  public void testCheckout_multipleWatchesWithAndWithoutDiscount() {
    BigDecimal unitPrice1 = BigDecimal.valueOf(100);
    BigDecimal discountPrice1 = BigDecimal.valueOf(200);
    int quantityForDiscount = 3;
    Watch watch1 = mockWatch(unitPrice1, discountPrice1, quantityForDiscount);
    Mockito.when(watchRepository.findById(1L)).thenReturn(Optional.of(watch1));

    BigDecimal unitPrice2 = BigDecimal.valueOf(50);
    BigDecimal unitPrice3 = BigDecimal.valueOf(30);
    Watch watch2 = mockWatch(unitPrice2, null, null);
    Watch watch3 = mockWatch(unitPrice3, null, null);
    Mockito.when(watchRepository.findById(3L)).thenReturn(Optional.of(watch2));
    Mockito.when(watchRepository.findById(4L)).thenReturn(Optional.of(watch3));

    BigDecimal total = watchService.checkout(List.of(1L, 1L, 1L, 3L, 4L));
    BigDecimal expectedTotal = BigDecimal.valueOf(280);

    assertEquals(expectedTotal, total);
  }

}

