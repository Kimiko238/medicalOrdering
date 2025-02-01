package com.ordering.service;

import com.ordering.dto.EntityInspectionOrderDto;
import com.ordering.dto.FormInspectionOrderDto;
import com.ordering.entity.InspectionOrder;
import com.ordering.entity.InspectionType;
import com.ordering.helper.OrderHelper;
import com.ordering.model.InspectionOrderSummary;
import com.ordering.repository.InspectionMapper;
import com.ordering.repository.OrderMapper;
import com.ordering.repository.UserMapper;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {

  private OrderMapper orderMapper;
  private InspectionMapper inspectionMapper;
  private UserMapper userMapper;


  //  新規保存時
  public void save(EntityInspectionOrderDto entityInspectionOrderDto,
      Authentication authentication) {
    InspectionOrder inspectionOrder = OrderHelper.convertEntity(
        entityInspectionOrderDto,
        authentication
    );
    orderMapper.insert(inspectionOrder);
  }

  //  新規保存後、または編集保存後の表示
  public FormInspectionOrderDto showViewSaveData(
      EntityInspectionOrderDto entityInspectionOrderDto) {
    InspectionOrder inspectionOrderSavedData = orderMapper.selectById(
        entityInspectionOrderDto.getOrderId());
    FormInspectionOrderDto formInspectionOrderDto = OrderHelper.convertForm(
        inspectionOrderSavedData);
    return formInspectionOrderDto;
  }

  public FormInspectionOrderDto settingOrder(String patientId) {
    FormInspectionOrderDto formInspectionOrderDto = new FormInspectionOrderDto();
    List<InspectionType> inspectionTypes = inspectionMapper.selectAll();
    formInspectionOrderDto.setInspectionTypes(inspectionTypes);
    formInspectionOrderDto.setPatientId(patientId);
    return formInspectionOrderDto;
  }


  //  全件取得
  public List<InspectionOrder> findAll() {
    return orderMapper.selectAll();
  }

  //  全件取得
  public List<InspectionOrderSummary> findInspectionOrderSummaryList() {
    return orderMapper.selectInspectionOrderSummaryList();
  }

  //  全件取得
  public Optional<InspectionOrderSummary> findInspectionOrderSummary(String orderId) {
    return orderMapper.selectInspectionOrderSummary(orderId);
  }

  //  検査の詳細を取得
  public InspectionOrder findById(String id) {
    return orderMapper.selectById(id);
  }

  //  検査の詳細を編集
  public void edit(InspectionOrder inspectionOrder, Authentication authentication) {
    inspectionOrder.setUpdatedBy(authentication.getName());
    orderMapper.update(inspectionOrder);
  }

  //  検査を削除
  public void delete(InspectionOrder inspectionOrder) {
    orderMapper.delete(inspectionOrder);
  }
}


