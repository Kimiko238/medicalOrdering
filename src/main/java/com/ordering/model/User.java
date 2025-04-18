package com.ordering.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

  //ユーザーID
  private String id;

  //  ユーザー名
  @Pattern(regexp = "^\\S+\\s+\\S+$", message = "{patientName.pattern}")
  private String name;

  //ユーザーの生年月日
  @NotNull(message = "{birthday.null}")
  private LocalDate birthday;

  //  ユーザーの性別
  @NotNull(message = "{gender.null}")
  private Character gender;

  //ユーザーのパスワード
  private String pass;

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
