package com.ordering.helper;

import com.ordering.entity.EntityInspectionOrderDto;
import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.model.Order;
import org.springframework.security.core.Authentication;


public class OrderHelper {


  public static FormInspectionOrderDto convertForm(Order order) {
    
    return new FormInspectionOrderDto();
  }

  public static Order convertEntity(
      EntityInspectionOrderDto entityInspectionOrderDto,
      Authentication authentication) {
    Order order = new Order();
    order.setInspectionId
        (entityInspectionOrderDto.getInspectionId().replace(",", ""));
    order.setPatientId(entityInspectionOrderDto.getPatientShowId());
    order.setStatus("未実施");
    order.setDetails(entityInspectionOrderDto.getDetails());
    order.setDate(entityInspectionOrderDto.getDate());
    order.setCreatedBy(authentication.getName());
    return order;
  }

}
