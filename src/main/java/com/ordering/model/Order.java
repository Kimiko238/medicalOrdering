package com.ordering.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  //オーダーID
  private String id;

  //  検査ID
  private String inspectionId;

  //  患者ID
  private int patientId;

  //  検査ステータス
  private String status;

  //  検査内容
  @NotEmpty(message = "入力しなさい")
  private String details;

  //検査日
  private String date;

  //  作成者
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
