package com.ordering.repository;

import com.ordering.entity.RequestInspectionOrderDto;
import com.ordering.model.Order;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

  void insert(Order order);

  //  DB：inspection_order からorderのデータを取得
  List<Order> selectAll();

  //  RequestInspectionOrderDtoへデータをセットするための取得
  List<RequestInspectionOrderDto> selectAllDto();

  Order selectById(String id);

  void update(Order order);

  void delete(Order order);
}
