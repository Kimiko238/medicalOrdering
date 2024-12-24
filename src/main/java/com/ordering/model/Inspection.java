package com.ordering.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inspection {

  //ビューの切り替えに使う目印
  private int number = 0;

  //検査ID
  private String id;

  //  検査の種類
  private String name;
  //検査日
  private String date;

  //  検査内容
  @NotEmpty(message = "入力しなさい")
  private String details;

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
