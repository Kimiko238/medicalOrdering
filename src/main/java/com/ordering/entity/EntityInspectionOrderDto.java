package com.ordering.entity;

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

  private int patientShowId;

  private String inspectionName;

  private String date;

//  private String status;

  @NotEmpty(message = "入力しなさい")
  private String details;

//  //  作成者
//  private String createdBy;
//
//  //  作成日時
//  private String createdAt;
//
//  //  更新者
//  private String updatedBy;
//
//  //  更新日時
//  private String updatedAt;
//
//  //  削除者
//  private String deletedBy;
//
//  //  削除日時
//  private String deletedAt;


}
