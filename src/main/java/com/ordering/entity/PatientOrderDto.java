package com.ordering.entity;

import com.ordering.model.Inspection;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientOrderDto {

  private String orderId;

  private String inspectionId;

  private int patientShowId;

  private String patientName;

  private String inspectionName;

  private List<Inspection> inspections;

  private LocalDateTime date;

  private String status;

  private String details;

  //    作成者
  private String createdBy;

  //  作成日時
  private String createdAt;

  //  更新者
  private String updatedBy;

  //  更新日時
  private String updatedAt;

  //  削除者
  private String deletedBy;

  //  削除日時
  private String deletedAt;

}
