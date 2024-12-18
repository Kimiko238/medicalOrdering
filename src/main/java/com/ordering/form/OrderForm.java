package com.ordering.form;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderForm {

  //  検査の種類
  private List<String> inspection;
  //検査日
  private String date;

  //  検査内容
  @NotEmpty(message = "入力しなさい")
  private String content;
}
