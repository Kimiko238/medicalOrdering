package com.ordering.rest.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleInspection {

  //  検査ID
  private String id;

  //  検査名
  private String name;
}
