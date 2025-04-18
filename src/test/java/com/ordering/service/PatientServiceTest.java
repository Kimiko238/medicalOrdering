package com.ordering.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ordering.model.Patient;
import com.ordering.repository.PatientMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

  @InjectMocks
  PatientService patientService;

  @Mock
  PatientMapper patientMapper;

  @Mock
  Authentication authentication;

  Patient patientSample;


  @BeforeEach
  void setUp() {
    patientSample = new Patient(
        "id",
        99,
        "小町さん",
        LocalDate.parse("1993-10-20"),
        '2',
        "アシュ",
        null,
        null,
        null,
        null,
        null
    );
  }


  //  連番IDから患者情報を取得、をテスト
  @Test
  void findByIdTest() {
    int showId = 1;
    doReturn(patientSample).when(patientMapper).selectByShowId(showId);
    Patient patientDto = patientService.findByShowId(showId);
    assertEquals(patientSample.getId(), patientDto.getId());
    assertEquals(patientSample.getName(), patientDto.getName());
    assertEquals(patientSample.getBirthday(), patientDto.getBirthday());
  }

  //  重複登録がないかチェックする処理、のテスト
//  @Test
//  void checkNameAndBirthdayTest() {
//    String message = "確認チェック";
//    doReturn(message)
//        .when(patientMapper)
//        .selectByNameAndBirthday(patientSample.getName(), patientSample.getBirthday());
//    String checkMessage = patientService
//        .checkNameAndBirthday(patientSample, authentication);
//  }

  //  新規登録のテスト
  @Test
  void saveTest() {
    doNothing().when(patientMapper).insert(patientSample);
    doReturn("花子").when(authentication).getName();
    patientService.save(patientSample, authentication);
    verify(patientMapper, times(1)).insert(patientSample);
  }


}
