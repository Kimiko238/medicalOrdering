package com.ordering.helper;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.model.Inspection;
import com.ordering.model.Order;
import com.ordering.repository.InspectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    formInspectionOrderDto.setStatus(order.getStatus());
    formInspectionOrderDto.setInspectionName(inspection.getName());
    formInspectionOrderDto.setDate(order.getInspectionDate());
    formInspectionOrderDto.setDetails(order.getInspectionDetails());
    if (formInspectionOrderDto.getInspections() == null) {
      formInspectionOrderDto.setInspections(inspectionMapper.selectAll());
    }
    return formInspectionOrderDto;
  }


  public Order convertEntity(
      FormInspectionOrderDto formDto) {
    Order order = new Order();
    order.setInspectionId
        (formDto.getInspectionId());
    order.setPatientId(formDto.getPatientShowId());
    order.setStatus(formDto.getStatus());
    order.setInspectionDetails(formDto.getDetails());
    order.setInspectionDate(formDto.getDate());
    if (formDto.getOrderId() != "") {
      order.setId(formDto.getOrderId());
    } else {
      order.setStatus("未実施"); // Order ID が空の場合、"未実施" を設定
    }
    return order;
  }

}
