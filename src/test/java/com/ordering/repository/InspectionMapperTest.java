package com.ordering.repository;

import com.ordering.model.Inspection;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

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
        "2021-5-10",
        "テストテスト",
        "アシュ",
        now,
        null,
        null,
        null,
        null
    );

  }


}
