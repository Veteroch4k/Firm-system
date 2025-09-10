package com.veteroch4k.order.dto;

import java.time.LocalDate;
import lombok.Data;
import org.springframework.cglib.core.Local;

@Data
public class DateRange {

  private final LocalDate start;
  private final LocalDate finish;

  public DateRange(LocalDate start, LocalDate finish) {
    this.start = start;
    this.finish = finish;
  }


}
