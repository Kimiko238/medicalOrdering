package com.ordering.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityInspectionOrderDto {

  private String orderId;

  private String inspectionId;

  private String patientId;

  private String inspectionName;

  private String date;

  @NotEmpty(message = "入力しなさい")
  private String details;
}
