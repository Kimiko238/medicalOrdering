package com.ordering.service;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.helper.OrderConvert;
import com.ordering.model.Inspection;
import com.ordering.model.Order;
import com.ordering.repository.InspectionMapper;
import com.ordering.repository.OrderMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

  private OrderMapper orderMapper;
  private InspectionMapper inspectionMapper;
  private OrderConvert orderConvert;
  private Order order;

  @Autowired
  public OrderService(OrderMapper orderMapper, InspectionMapper inspectionMapper,
      OrderConvert orderConvert) {
    this.orderMapper = orderMapper;
    this.inspectionMapper = inspectionMapper;
    this.orderConvert = orderConvert;
    this.order = new Order(); // ここで Order を初期化
  }

  //  新規保存時
  public void save(FormInspectionOrderDto formInspectionOrderDto,
      Authentication authentication) {
    Inspection inspection = inspectionMapper.selectById(
        formInspectionOrderDto.getInspectionId());
    order = orderConvert.convertEntity(formInspectionOrderDto);
    order.setCreatedBy(authentication.getName());
    orderMapper.insert(order);
  }

  //  新規保存後、または編集保存後の表示
  public FormInspectionOrderDto showViewSaveData() {
    Order savedOrderData = orderMapper.selectById(order.getId());
    FormInspectionOrderDto formInspectionOrderDto = orderConvert.convertForm(savedOrderData);
    order = new Order();
    return formInspectionOrderDto;
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
    List<Order> orders = orderMapper.selectAll();
    List<FormInspectionOrderDto> formInspectionOrderDtos = new ArrayList<>();
    for (Order order : orders) {
      FormInspectionOrderDto formInspectionOrderDto = orderConvert.convertForm(order);
      formInspectionOrderDtos.add(formInspectionOrderDto);
    }
    return formInspectionOrderDtos;
  }

  //  検査の詳細を取得
  public FormInspectionOrderDto findById(String id) {
    Order findOrder = orderMapper.selectById(id);
    return orderConvert.convertForm(findOrder);
  }

  //  検査の詳細を編集


  public void edit(FormInspectionOrderDto formInspectionOrderDto, Authentication authentication) {
    Order order = orderConvert.convertEntity(formInspectionOrderDto);
    order.setUpdatedBy(authentication.getName());
    this.order = order;
    orderMapper.update(order);
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
        throw new RuntimeException();
      }
      updateStatus(formInspectionOrderDto, status);
    }
    return formInspectionOrderDto;
  }


  public void updateStatus(FormInspectionOrderDto formInspectionOrderDto, String showStatus) {
    Optional.ofNullable(formInspectionOrderDto)
        .flatMap(dto -> Optional.ofNullable(showStatus).map(s -> {
          dto.setStatus(s);
          order = orderMapper.selectById(formInspectionOrderDto.getOrderId());
          order.setStatus(s);
          orderMapper.update(order);
          return dto;
        }));
  }
}


