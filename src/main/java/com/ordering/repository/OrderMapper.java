package com.ordering.repository;

import com.ordering.dto.FormInspectionOrderDto;
import com.ordering.entity.InspectionOrder;
import com.ordering.model.InspectionOrderSummary;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

  void insert(InspectionOrder inspectionOrder);

  //  DB：inspection_order からorderのデータを取得
  List<InspectionOrder> selectAll();

  List<InspectionOrderSummary> selectInspectionOrderSummaryList();

  Optional<InspectionOrderSummary> selectInspectionOrderSummary(String orderId);

  //  RequestInspectionOrderDtoへデータをセットするための取得
  List<FormInspectionOrderDto> selectAllDto();

  InspectionOrder selectById(String id);

  void update(InspectionOrder inspectionOrder);

  void delete(InspectionOrder inspectionOrder);
}
