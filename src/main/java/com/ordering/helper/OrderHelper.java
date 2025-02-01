package com.ordering.helper;

import com.ordering.dto.EntityInspectionOrderDto;
import com.ordering.dto.FormInspectionOrderDto;
import com.ordering.entity.InspectionOrder;
import org.springframework.security.core.Authentication;


public class OrderHelper {
  public static FormInspectionOrderDto convertForm(InspectionOrder inspectionOrder) {
    return new FormInspectionOrderDto();
  }

  public static InspectionOrder convertEntity(
      EntityInspectionOrderDto entityInspectionOrderDto,
      Authentication authentication) {
    InspectionOrder inspectionOrder = new InspectionOrder();
    inspectionOrder.setInspectionId(entityInspectionOrderDto.getInspectionId());
    inspectionOrder.setPatientId(entityInspectionOrderDto.getPatientId());
    inspectionOrder.setStatus("未実施");
    inspectionOrder.setDetails(entityInspectionOrderDto.getDetails());
    inspectionOrder.setDate(entityInspectionOrderDto.getDate());
    inspectionOrder.setCreatedBy(authentication.getName());
    return inspectionOrder;
  }

}
