package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ordering.model.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class OrderMapperTest {

  @Autowired
  OrderMapper orderMapper;

  Order orderSample;

  @BeforeEach
  void setUp() {
    String now = String.valueOf(LocalDateTime.now());
    orderSample = new Order(
        "addId",
        "検査テスト",
        "2021-05-10 00:00:00",
        "テストテスト",
        "アシュ",
        now,
        null,
        null,
        null,
        null
    );

  }


  @Test
  @Sql("./insertInspectionList.sql")
  void testInsert() {
    orderMapper.insert(orderSample);
    Order checkOrder = orderMapper.selectById(
        orderSample.getId());
    assertEquals(checkOrder.getName(), orderSample.getName());
    assertEquals(checkOrder.getDate(), orderSample.getDate());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testSelectAll() {
    List<Order> inspectionsList = orderMapper.selectAll();
    Order order = inspectionsList.stream().findFirst().orElseThrow();
    assertEquals("テスト検査", order.getName());
    assertEquals("2025-01-17 10:30:00", order.getDate());
    assertEquals("検査テストです", order.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testSelectById() {
    Order checkOrder = orderMapper.selectById("id");
    assertEquals("テスト検査", checkOrder.getName());
    assertEquals("2025-01-17 10:30:00", checkOrder.getDate());
    assertEquals("検査テストです", checkOrder.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testUpdate() {
    Order checkOrder = orderMapper.selectById("id");
    orderMapper.update(checkOrder);
    assertEquals("テスト検査", checkOrder.getName());
    assertEquals("2025-01-17 10:30:00", checkOrder.getDate());
    assertEquals("検査テストです", checkOrder.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testDelete() {
    Order checkOrder = orderMapper.selectById("id");
    orderMapper.delete(checkOrder);
    Order deletedOrder = orderMapper.selectById("id");
    assertNull(deletedOrder);
  }
}
