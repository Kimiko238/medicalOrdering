package com.ordering.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
  @Size(min = 1, message = "{patientName.size}")
  @Pattern(regexp = "^\\S+\\s+\\S+$", message = "{patientName.pattern}")
  private String name;
  /**
   * 生年月日
   */
  @NotBlank(message = "{birthday.null}")
  private String birthday;
  /**
   * 性別
   */
  @NotNull(message = "{gender.null}")
  private Character gender;
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

