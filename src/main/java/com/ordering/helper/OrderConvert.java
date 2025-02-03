package com.ordering.helper;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.model.Inspection;
import com.ordering.model.Order;
import com.ordering.repository.InspectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class OrderConvert {

  @Autowired
  InspectionMapper inspectionMapper;


  public FormInspectionOrderDto convertForm(Order order) {
    FormInspectionOrderDto formInspectionOrderDto = new FormInspectionOrderDto();
    Inspection inspection = inspectionMapper.selectById(order.getInspectionId());
    formInspectionOrderDto.setOrderId(order.getId());
    formInspectionOrderDto.setInspectionId(order.getInspectionId());
    formInspectionOrderDto.setInspectionName(inspection.getName());
    formInspectionOrderDto.setDate(order.getInspectionDate());
    formInspectionOrderDto.setDetails(order.getInspectionDetails());
    if (formInspectionOrderDto.getInspections() == null) {
      formInspectionOrderDto.setInspections(inspectionMapper.selectAll());
    }
    return formInspectionOrderDto;
  }


  public Order convertEntity(
      FormInspectionOrderDto entityInspectionOrderDto,
      Authentication authentication) {
    Order order = new Order();
    order.setInspectionId
        (entityInspectionOrderDto.getInspectionId());
    order.setPatientId(entityInspectionOrderDto.getPatientShowId());
    order.setStatus("未実施");
    order.setInspectionDetails(entityInspectionOrderDto.getDetails());
    order.setInspectionDate(entityInspectionOrderDto.getDate());
    order.setCreatedBy(authentication.getName());
    return order;
  }

}
