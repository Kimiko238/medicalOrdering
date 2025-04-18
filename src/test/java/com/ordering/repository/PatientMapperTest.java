package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ordering.model.Patient;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class PatientMapperTest {

  @Autowired
  PatientMapper patientMapper;

  //テストするためのサンプルモデル
  Patient patientSample;

  @BeforeEach
  void setUp() {

    patientSample = new Patient(
        "patientId",
        100,
        "テスト 太郎",
        LocalDate.parse("2000-05-18"),
        '2',
        "テスト ユーザー",
        LocalDateTime.now(),
        null,
        null,
        null,
        null
    );
  }

  @Test
  @Sql("data.sql")
  void testInsert() {
    patientMapper.insert(patientSample);
    Patient checkPatient = patientMapper.selectByNameAndBirthday("テスト 太郎",
        LocalDate.parse("2000-05-18"));
    assertEquals(patientSample.getGender(), checkPatient.getGender());
  }

  @Test
  @Sql("data.sql")
  void testSelectById() {
    Patient checkPatient = patientMapper.selectById("patientId");
//    Patient checkPatient = patientMapper.selectById(patientDto.getShowId());
    assertEquals("2", checkPatient.getGender());
    assertEquals("テスト ユーザー", checkPatient.getCreatedBy());
  }

  @Test
  @Sql("data.sql")
  void testSelectByNameBirthday() {
    Patient patientDto = patientMapper.selectByNameAndBirthday("テスト 花子",
        LocalDate.parse("2000-05-18"));
    assertEquals("2", patientDto.getGender());
    assertEquals("テスト ユーザー", patientDto.getCreatedBy());
  }

  @Test
  @Sql("data.sql")
  void testUpdate() {
    Patient updatePatient = patientMapper.selectById("patientId");
    updatePatient.setGender('1');
    updatePatient.setBirthday(LocalDate.parse("2000-05-18"));
    patientMapper.update(updatePatient);
    Patient checkPatient = patientMapper.selectById("patientId");
    assertEquals(updatePatient.getGender(), checkPatient.getGender());
    assertEquals(updatePatient.getBirthday(), checkPatient.getBirthday());

  }
}
