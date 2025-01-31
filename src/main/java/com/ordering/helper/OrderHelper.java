package com.ordering.helper;

import com.ordering.entity.ResponseInspectionOrderDto;
import com.ordering.model.Order;
import org.springframework.security.core.Authentication;


public class OrderHelper {


  public static Order convertOrder(
      ResponseInspectionOrderDto responseInspectionOrderDto,
      Authentication authentication) {
    Order order = new Order();
    order.setInspectionId
        (responseInspectionOrderDto.getInspectionId().replace(",", ""));
    order.setPatientId(responseInspectionOrderDto.getPatientShowId());
    order.setStatus("未実施");
    order.setDetails(responseInspectionOrderDto.getDetails());
    order.setDate(responseInspectionOrderDto.getDate());
    order.setCreatedBy(authentication.getName());
    return order;

  }


}
