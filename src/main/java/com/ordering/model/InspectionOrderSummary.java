package com.ordering.model;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InspectionOrderSummary {

  private String orderId;

  private String inspectionId;

  private String patientId;

  private String name;

  private LocalDateTime date;

  private String status;

  @NotEmpty(message = "入力しなさい")
  private String details;
}
