package com.ordering.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ordering.entity.ResponseInspectionOrderDto;
import com.ordering.model.Order;
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
public class OrderServiceTest {

  @InjectMocks
  OrderService orderService;

  @Mock
  OrderMapper orderMapper;

  @Mock
  Authentication authentication;

  Order orderSample;

  @BeforeEach
  void setUp() {
    orderSample = new Order(
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
    doNothing().when(orderMapper).insert(orderSample);
    orderService.save(orderSample, authentication);
    verify(orderMapper, times(1)).insert(orderSample);
  }

  //  全件取得のテスト
  @Test
  void findAllTest() {
    doReturn(List.of(orderSample)).when(orderMapper).selectAll();
    List<Order> orderDtoList = orderService.findAll();
    Order orderDto = orderDtoList.stream().findFirst()
        .orElseThrow();
    assertEquals(orderDto.getId(), orderSample.getId());
    assertEquals(ResponseInspectionOrderDto.getName(), orderSample.getName());
    assertEquals(orderDto.getDate(), orderSample.getDate());
    assertEquals(orderDto.getDetails(), orderSample.getDetails());
  }

  //  検査の詳細取得のテスト
  @Test
  void findByIdTest() {
    String id = "id";
    doReturn(orderSample).when(orderMapper).selectById(id);
    Order orderDto = orderMapper.selectById(id);
    assertEquals(orderDto.getId(), orderSample.getId());
    assertEquals(orderDto.getName(), orderSample.getName());
    assertEquals(orderDto.getDate(), orderSample.getDate());
    assertEquals(orderDto.getDetails(), orderSample.getDetails());
  }

  //検査の詳細を編集する、のテスト
  @Test
  void editTest() {
    doNothing().when(orderMapper).update(orderSample);
    orderService.edit(orderSample, authentication);
    verify(orderMapper, times(1)).update(orderSample);
  }

  //検査を削除する、のテスト
  @Test
  void deleteTest() {
    doNothing().when(orderMapper).delete(orderSample);
    orderService.delete(orderSample);
    verify(orderMapper, times(1)).delete(orderSample);
  }
}
