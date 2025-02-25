package com.ordering.service;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.entity.PatientOrderDto;
import com.ordering.exception.OrderStatusException;
import com.ordering.helper.OrderConvert;
import com.ordering.model.Inspection;
import com.ordering.model.Order;
import com.ordering.repository.InspectionMapper;
import com.ordering.repository.OrderMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class OrderService {

  private OrderMapper orderMapper;
  private InspectionMapper inspectionMapper;
  private OrderConvert orderConvert;


  //  新規保存時
  public FormInspectionOrderDto save(FormInspectionOrderDto catchFormInspectionOrderDto,
      Authentication authentication) {
    Order saveOrder = orderConvert.convertEntity(catchFormInspectionOrderDto);
    saveOrder.setCreatedBy(authentication.getName());
    orderMapper.insert(saveOrder);
    return this.showViewSaveData(saveOrder);
  }

  //  新規保存後、または編集保存後の表示
  public FormInspectionOrderDto showViewSaveData(Order targetOrder) {
    Order savedOrderData = orderMapper.selectById(targetOrder.getId());
    return orderConvert.convertForm(savedOrderData);
  }

  public FormInspectionOrderDto settingOrder(int showId) {
    FormInspectionOrderDto formInspectionOrderDto = new FormInspectionOrderDto();
    formInspectionOrderDto.setPatientShowId(showId);
    List<Inspection> inspections = inspectionMapper.selectAll();
    formInspectionOrderDto.setInspections(inspections);
    return formInspectionOrderDto;
  }


  //  全件取得
  public List<FormInspectionOrderDto> findAll() {
    List<PatientOrderDto> patientOrdersDto = orderMapper.selectAll();
    List<FormInspectionOrderDto> formInspectionOrdersDto = new ArrayList<>();
    for (PatientOrderDto patientOrderDto : patientOrdersDto) {
      FormInspectionOrderDto formInspectionOrderDto = orderConvert.convertForm(patientOrderDto);
      formInspectionOrdersDto.add(formInspectionOrderDto);
    }
    return formInspectionOrdersDto;
  }

  //  検査の詳細を取得
  public FormInspectionOrderDto findById(String id) {
    Order findOrder = orderMapper.selectById(id);
    return orderConvert.convertForm(findOrder);
  }

  //  検査の詳細を編集
  public FormInspectionOrderDto edit(FormInspectionOrderDto formInspectionOrderDto,
      Authentication authentication) {
    Order updateOrder = orderConvert.convertEntity(formInspectionOrderDto);
    updateOrder.setUpdatedBy(authentication.getName());
    orderMapper.update(updateOrder);
    return this.showViewSaveData(updateOrder);
  }

  //  検査を削除
  public void delete(FormInspectionOrderDto deleteDto) {
    Order deleteOrder = orderConvert.convertEntity(deleteDto);
    orderMapper.delete(deleteOrder);
  }

  //オーダーステータスの変更
  public FormInspectionOrderDto setStatus(FormInspectionOrderDto formInspectionOrderDto,
      String status) {
    Map<String, String> validTransitions = Map.of(
        "受付済", "未実施",
        "実施済", "受付済"
    );

    if (validTransitions.containsKey(status)) {
      if (!validTransitions.get(status).equals(formInspectionOrderDto.getStatus())) {
        throw new OrderStatusException();
      }
      updateStatus(formInspectionOrderDto, status);
    }
    return formInspectionOrderDto;
  }


  public void updateStatus(FormInspectionOrderDto formInspectionOrderDto, String showStatus) {
    Optional.ofNullable(formInspectionOrderDto)
        .flatMap(dto -> Optional.ofNullable(showStatus).map(s -> {
          dto.setStatus(s);
          Order catchOrder = orderMapper.selectById(formInspectionOrderDto.getOrderId());
          catchOrder.setStatus(s);
          orderMapper.update(catchOrder);
          return dto;
        }));
  }
}


