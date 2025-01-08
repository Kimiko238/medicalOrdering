package com.ordering.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Patient {


  /**
   * することID
   */
  private String id;

  /**
   * 　見えるID（検索用）
   */
  private Integer showId;

  /**
   * 名前
   */
  private String name;
  /**
   * 生年月日
   */
  private LocalDate birthday;
  /**
   * 性別
   */
  private String gender;
  /**
   * 作成者
   */
  private String createdBy;
  /**
   * 作成日時
   */
  private LocalDateTime createdAt;
  /**
   * 更新者
   */
  private String updatedBy;
  /**
   * 更新日時
   */
  private LocalDateTime updatedAt;
  /**
   * 削除者
   */
  private String deletedBy;
  /**
   * 削除日時
   */
  private LocalDateTime deletedAt;
}

