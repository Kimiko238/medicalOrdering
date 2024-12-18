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
   * 名前
   */
  private String name;
  /**
   * すること詳細
   */
  private LocalDate birthday;
  /**
   * 作成日時
   */
  private String gender;


  private String createdBy;
  /**
   * 作成日時
   */
  private LocalDateTime createdAt;

  private String updatedBy;
  /**
   * 更新日時
   */
  private LocalDateTime updatedAt;

  private String deletedBy;
  /**
   * 更新日時
   */
  private LocalDateTime deletedAt;
}

