package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ordering.entity.PatientOrderDto;
import com.ordering.model.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@ActiveProfiles("test")
@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderMapperTest {

  @Autowired
  OrderMapper orderMapper;

  Order orderSample;
  PatientOrderDto OrderDtoSample;

  @BeforeEach
  void setUp() {
    String now = String.valueOf(LocalDateTime.now());
    orderSample = new Order(
        "orderId",
        "1",
        100,
        "未実施",
        "テストテスト",
        "2021-05-10 12:00:00",
        "アシュ",
        now,
        null,
        null,
        null,
        null
    );

    OrderDtoSample = new PatientOrderDto(
        "id",
        "2",
        200,
        "テスト 花子",
        "レントゲン",
        List.of(),
        "未実施",
        "2021-05-10 12:00:00",
        "未実施",
        "テスト詳細",
        "テスト ユーザー",
        now,
        "",
        "",
        ""
    );

  }


  @Test
  @Sql("init/data.sql")
  void testInsert() {
    orderMapper.insert(orderSample);
    Order checkOrder = orderMapper.selectById(
        orderSample.getId());
    assertEquals(checkOrder.getInspectionId(), orderSample.getInspectionId());
    assertEquals(checkOrder.getInspectionDate(), orderSample.getInspectionDate());
  }

  @Test
  @Sql("./data.sql")
  void testSelectAll() {
    List<PatientOrderDto> inspectionsList = orderMapper.selectAll();
    PatientOrderDto patientOrderDto = inspectionsList.stream().findFirst().orElseThrow();
    assertEquals("テスト 花子", patientOrderDto.getPatientName());
    assertEquals("2021-05-10 12:00:00", patientOrderDto.getDate());
    assertEquals("テストテスト", patientOrderDto.getDetails());
  }

  @Test
  @Sql("./data.sql")
  void testSelectById() {
    Order checkOrder = orderMapper.selectById("orderId");
    assertEquals("1", checkOrder.getInspectionId());
    assertEquals("2021-05-10 12:00:00", checkOrder.getInspectionDate());
    assertEquals("テストテスト", checkOrder.getInspectionDetails());
  }

  @Test
  @Sql("./data.sql")
  void testUpdate() {
    Order checkOrder = orderMapper.selectById("orderId");
    orderMapper.update(checkOrder);
    assertEquals("1", checkOrder.getInspectionId());
    assertEquals("2021-05-10 12:00:00", checkOrder.getInspectionDate());
    assertEquals("テストテスト", checkOrder.getInspectionDetails());
  }

  @Test
  @Sql("./data.sql")
  void testDelete() {
    Order checkOrder = orderMapper.selectById("orderId");
    orderMapper.delete(checkOrder);
    Order deletedOrder = orderMapper.selectById("orderId");
    assertNull(deletedOrder);
  }
}
