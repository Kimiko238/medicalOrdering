package com.ordering.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ordering.dto.EntityInspectionOrderDto;
import com.ordering.entity.InspectionOrder;
import com.ordering.repository.OrderMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
public class InspectionOrderServiceTest {

  @InjectMocks
  OrderService orderService;

  @Mock
  OrderMapper orderMapper;

  @Mock
  Authentication authentication;

  InspectionOrder inspectionOrderSample;

  @BeforeEach
  void setUp() {
    inspectionOrderSample = new InspectionOrder(
        "id",
        "検査",
        "2024-12-22 11:05:15",
        "検査の詳細",
        "アシュ",
        null,// createdAt
        null,          // updatedBy
        null,          // updatedAt
        null,          // deletedBy
        null
    );
  }

  //  新規保存のテスト
  @Test
  void saveTest() {
    doNothing().when(orderMapper).insert(inspectionOrderSample);
    orderService.save(inspectionOrderSample, authentication);
    verify(orderMapper, times(1)).insert(inspectionOrderSample);
  }

  //  全件取得のテスト
  @Test
  void findAllTest() {
    doReturn(List.of(inspectionOrderSample)).when(orderMapper).selectAll();
    List<InspectionOrder> inspectionOrderDtoList = orderService.findAll();
    InspectionOrder inspectionOrderDto = inspectionOrderDtoList.stream().findFirst()
        .orElseThrow();
    assertEquals(inspectionOrderDto.getId(), inspectionOrderSample.getId());
    assertEquals(EntityInspectionOrderDto.getName(), inspectionOrderSample.getName());
    assertEquals(inspectionOrderDto.getDate(), inspectionOrderSample.getDate());
    assertEquals(inspectionOrderDto.getDetails(), inspectionOrderSample.getDetails());
  }

  //  検査の詳細取得のテスト
  @Test
  void findByIdTest() {
    String id = "id";
    doReturn(inspectionOrderSample).when(orderMapper).selectById(id);
    InspectionOrder inspectionOrderDto = orderMapper.selectById(id);
    assertEquals(inspectionOrderDto.getId(), inspectionOrderSample.getId());
    assertEquals(inspectionOrderDto.getName(), inspectionOrderSample.getName());
    assertEquals(inspectionOrderDto.getDate(), inspectionOrderSample.getDate());
    assertEquals(inspectionOrderDto.getDetails(), inspectionOrderSample.getDetails());
  }

  //検査の詳細を編集する、のテスト
  @Test
  void editTest() {
    doNothing().when(orderMapper).update(inspectionOrderSample);
    orderService.edit(inspectionOrderSample, authentication);
    verify(orderMapper, times(1)).update(inspectionOrderSample);
  }

  //検査を削除する、のテスト
  @Test
  void deleteTest() {
    doNothing().when(orderMapper).delete(inspectionOrderSample);
    orderService.delete(inspectionOrderSample);
    verify(orderMapper, times(1)).delete(inspectionOrderSample);
  }
}
