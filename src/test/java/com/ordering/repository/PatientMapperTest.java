package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ordering.entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PatientMapperTest {

  @Autowired
  PatientMapper patientMapper;

  //テストするためのサンプルモデル
  Patient patientSample;

  @BeforeEach
  void setUp() {

    patientSample = new Patient(
        "44",
        null,
        "冴子",
        "2023-01-15",
        "2",
        "アシュ",
        null,
        null,
        null,
        null,
        null
    );
  }

  @Test
  @Sql("./insertPatientList.sql")
  void testInsert() {
    patientMapper.insert(patientSample);
    Patient checkPatient = patientMapper.selectByNameAndBirthday("冴子", "2023-01-15");
    assertEquals(patientSample.getShowId(), checkPatient.getShowId());
    assertEquals(patientSample.getGender(), checkPatient.getGender());
  }

  @Test
  @Sql("./insertPatientList.sql")
  void testSelectById() {
    patientMapper.insert(patientSample);
    Patient patientDto = patientMapper.selectByNameAndBirthday("冴子", "2023-01-15");
//    Patient checkPatient = patientMapper.selectById(patientDto.getShowId());
    assertEquals("2", patientDto.getGender());
    assertEquals("アシュ", patientDto.getCreatedBy());
  }

  @Test
  @Sql("./insertPatientList.sql")
  void testSelectByNameBirthday() {
    Patient patientDto = patientMapper.selectByNameAndBirthday("トム", "2025-01-17");
    assertEquals("2", patientDto.getGender());
    assertEquals("アシュ", patientDto.getCreatedBy());
  }
}
