package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.ordering.model.Inspection;
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
public class InspectionMapperTest {

  @Autowired
  InspectionMapper inspectionMapper;

  Inspection inspectionSample;

  @BeforeEach
  void setUp() {
    String now = String.valueOf(LocalDateTime.now());
    inspectionSample = new Inspection(
        1,
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
    inspectionMapper.insert(inspectionSample);
    Inspection checkInspection = inspectionMapper.selectById(inspectionSample.getId());
    assertEquals(checkInspection.getName(), inspectionSample.getName());
    assertEquals(checkInspection.getDate(), inspectionSample.getDate());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testSelectAll() {
    List<Inspection> inspectionsList = inspectionMapper.selectAll();
    Inspection inspection = inspectionsList.stream().findFirst().orElseThrow();
    assertEquals("テスト検査", inspection.getName());
    assertEquals("2025-01-17 10:30:00", inspection.getDate());
    assertEquals("検査テストです", inspection.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testSelectById() {
    Inspection checkInspection = inspectionMapper.selectById("id");
    assertEquals("テスト検査", checkInspection.getName());
    assertEquals("2025-01-17 10:30:00", checkInspection.getDate());
    assertEquals("検査テストです", checkInspection.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testUpdate() {
    Inspection checkInspection = inspectionMapper.selectById("id");
    inspectionMapper.update(checkInspection);
    assertEquals("テスト検査", checkInspection.getName());
    assertEquals("2025-01-17 10:30:00", checkInspection.getDate());
    assertEquals("検査テストです", checkInspection.getDetails());
  }

  @Test
  @Sql("./insertInspectionList.sql")
  void testDelete() {
    Inspection checkInspection = inspectionMapper.selectById("id");
    inspectionMapper.delete(checkInspection);
    Inspection deletedInspection = inspectionMapper.selectById("id");
    assertNull(deletedInspection);
  }
}
