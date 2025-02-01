package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ordering.entity.InspectionOrder;
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
public class InspectionOrderMapperTest {

  @Autowired
  OrderMapper orderMapper;

  InspectionOrder inspectionOrderSample;

  @BeforeEach
  void setUp() {
    String now = String.valueOf(LocalDateTime.now());
    inspectionOrderSample = new InspectionOrder(
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
    orderMapper.insert(inspectionOrderSample);
    InspectionOrder checkInspectionOrder = orderMapper.selectById(
        inspectionOrderSample.getId());
    assertEquals(checkInspectionOrder.getName(), inspectionOrderSample.getName());
    assertEquals(checkInspectionOrder.getDate(), inspectionOrderSample.getDate());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testSelectAll() {
    List<InspectionOrder> inspectionsList = orderMapper.selectAll();
    InspectionOrder inspectionOrder = inspectionsList.stream().findFirst().orElseThrow();
    assertEquals("テスト検査", inspectionOrder.getName());
    assertEquals("2025-01-17 10:30:00", inspectionOrder.getDate());
    assertEquals("検査テストです", inspectionOrder.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testSelectById() {
    InspectionOrder checkInspectionOrder = orderMapper.selectById("id");
    assertEquals("テスト検査", checkInspectionOrder.getName());
    assertEquals("2025-01-17 10:30:00", checkInspectionOrder.getDate());
    assertEquals("検査テストです", checkInspectionOrder.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testUpdate() {
    InspectionOrder checkInspectionOrder = orderMapper.selectById("id");
    orderMapper.update(checkInspectionOrder);
    assertEquals("テスト検査", checkInspectionOrder.getName());
    assertEquals("2025-01-17 10:30:00", checkInspectionOrder.getDate());
    assertEquals("検査テストです", checkInspectionOrder.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testDelete() {
    InspectionOrder checkInspectionOrder = orderMapper.selectById("id");
    orderMapper.delete(checkInspectionOrder);
    InspectionOrder deletedInspectionOrder = orderMapper.selectById("id");
    assertNull(deletedInspectionOrder);
  }
}
