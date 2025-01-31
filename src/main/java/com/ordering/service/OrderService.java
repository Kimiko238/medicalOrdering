package com.ordering.service;

import com.ordering.entity.EntityInspectionOrderDto;
import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.helper.OrderHelper;
import com.ordering.model.Inspection;
import com.ordering.model.Order;
import com.ordering.repository.InspectionMapper;
import com.ordering.repository.OrderMapper;
import com.ordering.repository.UserMapper;
import java.util.List;
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
    Inspection inspection = inspectionMapper.selectById(
        entityInspectionOrderDto.getInspectionId());
    Order order = OrderHelper.convertEntity(entityInspectionOrderDto, authentication);
    orderMapper.insert(order);
  }

  //  新規保存後、または編集保存後の表示
  public FormInspectionOrderDto showViewSaveData(
      EntityInspectionOrderDto entityInspectionOrderDto) {
    Order orderSavedData = orderMapper.selectById(entityInspectionOrderDto.getOrderId());
    FormInspectionOrderDto formInspectionOrderDto = OrderHelper.convertForm(orderSavedData);
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
  public List<Order> findAll() {
    return orderMapper.selectAll();
  }

  //  検査の詳細を取得
  public Order findById(String id) {

    return orderMapper.selectById(id);
  }

  //  検査の詳細を編集
  public void edit(Order order, Authentication authentication) {
    order.setUpdatedBy(authentication.getName());
    orderMapper.update(order);
  }

  //  検査を削除
  public void delete(Order order) {
    orderMapper.delete(order);
  }


}


