package com.ordering.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.entity.PatientOrderDto;
import com.ordering.exception.OrderStatusException;
import com.ordering.helper.OrderConvert;
import com.ordering.model.Order;
import com.ordering.repository.OrderMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @InjectMocks
  OrderService orderService;

  @Mock
  OrderMapper orderMapper;

  @Mock
  Authentication authentication;

  @Mock
  OrderConvert orderConvert;

  Order orderSample;
  FormInspectionOrderDto formDtoSample;
  PatientOrderDto patientOrderDtoSample;


  @BeforeEach
  void setUp() {
    orderSample = new Order(
        "addId",
        "2",
        100,
        "未実施",
        "テストテスト",
        LocalDateTime.parse("2021-05-10T12:00:00"),
        "アシュ",
        LocalDateTime.parse("2021-05-10T12:00:00"),
        null,
        null,
        null,
        null
    );

    formDtoSample = new FormInspectionOrderDto(
        "id1",
        "2",
        2,
        "テスト患者1",
        "心電図",
        List.of(),
        LocalDateTime.parse("2024-01-01 10:00:00"),
        "未実施",
        "詳細1"
    );

    patientOrderDtoSample = new PatientOrderDto(
        "id1",
        "2",
        2,
        "テスト患者1",
        "心電図",
        List.of(),
        LocalDateTime.parse("2024-01-01 10:00:00"),
        "未実施",
        "テストテスト",
        "アシュ",
        "2024-05-10 12:00:00",
        null,
        null,
        null,
        null
    );
  }

  //  新規保存のテスト
  @Test
  void saveTest() {
    doReturn(orderSample).when(orderConvert)
        .convertEntity(formDtoSample);
    doNothing().when(orderMapper).insert(orderSample);
    doReturn(orderSample).when(orderMapper).selectById(orderSample.getId());
    doReturn(formDtoSample).when(orderConvert).convertForm(orderSample);
    FormInspectionOrderDto result = orderService.save(formDtoSample, authentication);
    assertEquals("心電図", result.getInspectionName());

  }

  //    全件取得のテスト
  @Test
  void findAllTest() {
    doReturn(List.of(patientOrderDtoSample)).when(orderMapper).selectAll();
    doReturn(formDtoSample).when(orderConvert).convertForm(patientOrderDtoSample);
    List<FormInspectionOrderDto> orderDtoList = orderService.findAll();
    for (FormInspectionOrderDto orderDto : orderDtoList) {
      assertEquals(formDtoSample.getInspectionId(), orderDto.getInspectionId());
      assertEquals(formDtoSample.getPatientShowId(), orderDto.getPatientShowId());
    }
  }

  //  検査の詳細取得のテスト
  @Test
  void findByIdTest() {
    String id = "id";
    doReturn(orderSample).when(orderMapper).selectById(id);
    doReturn(formDtoSample).when(orderConvert).convertForm(orderSample);
    FormInspectionOrderDto resultFormDto = orderService.findById(id);
    assertEquals(formDtoSample.getInspectionId(), resultFormDto.getInspectionId());
    assertEquals(formDtoSample.getDate(), resultFormDto.getDate());
    assertEquals(formDtoSample.getDetails(), resultFormDto.getDetails());
  }

  //検査の詳細を編集する、のテスト
  @Test
  void editTest() {
    doReturn(orderSample).when(orderConvert).convertEntity(formDtoSample);
    doNothing().when(orderMapper).update(orderSample);
    doReturn(orderSample).when(orderMapper).selectById(orderSample.getId());
    doReturn(formDtoSample).when(orderConvert).convertForm(orderSample);
    FormInspectionOrderDto resultDto = orderService.edit(formDtoSample, authentication);
    assertEquals(formDtoSample.getInspectionId(), resultDto.getInspectionId());
    assertEquals(formDtoSample.getDate(), resultDto.getDate());
    assertEquals(formDtoSample.getDetails(), resultDto.getDetails());
  }

  //検査を削除する、のテスト
  @Test
  void deleteTest() {
    doReturn(orderSample).when(orderConvert).convertEntity(formDtoSample);
    doNothing().when(orderMapper).delete(orderSample);
    orderService.delete(formDtoSample);
    verify(orderMapper, times(1)).delete(orderSample);
  }

  @Test
  void setStatusTestOK() {
    doReturn(orderSample).when(orderMapper).selectById(formDtoSample.getOrderId());
    FormInspectionOrderDto result = orderService.setStatus(formDtoSample, "受付済");
    assertEquals("受付済", result.getStatus());
  }

  @Test
  void setStatusTestException() {
    assertThrows(OrderStatusException.class, () -> {
      orderService.setStatus(formDtoSample, "実施済");
    });
  }
}

