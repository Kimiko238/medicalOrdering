package com.ordering.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ordering.config.SecurityConfig;
import com.ordering.exception.PatientAlreadyExistsException;
import com.ordering.model.Patient;
import com.ordering.service.PatientService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(PatientController.class)
@Import(SecurityConfig.class)
public class PatientControllerTest {


  @Autowired
  MockMvc mockMvc;
  @MockitoBean
  PatientService patientService;
  @MockitoBean
  Authentication authentication;
  private Patient samplePatient;

  @BeforeEach
  void setUp() {
    samplePatient = new Patient(
        "id",
        3,
        "小町",
        LocalDate.parse("1988-10-5"),
        '2',
        "アシュ",
        null,
        null,
        null,
        null,
        null);
  }

  @Test
  void test_GET_newPatient() throws Exception {
    mockMvc.perform(
            get("/newPatient")
                .with(csrf())
                .with(user("testUser2").password("USERaa"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientForm"))
        .andExpect(model().attribute("isNewPatient", Boolean.TRUE))
        .andExpect(model().attributeExists("patient"));
  }

  @Test
  void test_GET_editPatient() throws Exception {
    doReturn(samplePatient).when(patientService).findById(any(String.class));
    mockMvc.perform(
            get("/editPatient/{id}", samplePatient.getId())
                .with(csrf())
                .with(user("test").password("pass"))
                .param("id", "id")
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientForm"))
        .andExpect(model().attributeExists("isNewPatient"))
        .andExpect(model().attributeExists("patient"));
  }

  //  患者検索の遷移、のテスト
  @Test
  void test_GET_searchPatient() throws Exception {
    doReturn(samplePatient).when(patientService).findByShowId(any(Integer.class));

    UserDetails userDetails = User.withUsername("test3")
        .password("pass")
        .roles("USER") // 必要ならロールを追加
        .build();

    mockMvc.perform(
            get("/searchPatient")
                .param("showId", "1")
                .with(csrf())
                .with(user(userDetails))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientDetails"))
        .andExpect(model().attributeExists("patient"))
        .andExpect(model().attributeExists("formInspectionOrdersDto"))
        .andExpect(model().attributeExists("isZeroList"));
  }

  //  患者新規作成（バリデーションOK）の場合のテスト
  @Test
  void test_POST_createPatientOK() throws Exception {
    doReturn(samplePatient).when(patientService)
        .save(any(Patient.class), any(Authentication.class));
    mockMvc.perform(
            post("/createPatient")
                .with(csrf())
                .with(user("testman").password("eivkb"))
                .param("id", "id")
                .param("showId", "1")
                .param("name", "akane komatsu")
                .param("birthday", "2021-3-19")
                .param("gender", "2")
                .param("createdBy", "アシュ")
                .param("createdAt", "")
                .param("updatedBy", "")
                .param("updatedAt", "")
                .param("deletedBy", "")
                .param("deletedAt", ""))

        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:newPatient"));

  }


  //  患者新規作成（バリデーションNG）の場合のテスト　※あとで
  @Test
  void test_POST_createPatientNG() throws Exception {
    mockMvc.perform(
            post("/createPatient")
                .with(csrf())
                .with(user("testman").password("eivkb"))
                .param("id", "id")
                .param("showId", "1")
                .param("name", "")
                .param("birthday", LocalDate.now().toString())
                .param("gender", "2")
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientForm"));
  }

  //  すでに患者が登録されていて、Exceptionをスローした時のテスト
  @Test
  @WithMockUser
  void test_POST_createPatientException() throws Exception {
    when(patientService.save(any(
        Patient.class), any(Authentication.class))).thenThrow(
        (new PatientAlreadyExistsException()));
    mockMvc.perform(
            post("/createPatient")
                .with(csrf())
                .param("id", "id")
                .param("showId", "1")
                .param("name", "akane komatsu")
                .param("birthday", "2021-3-19")
                .param("gender", "2")
                .param("createdBy", "アシュ")
                .param("createdAt", "")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:newPatient"))
        .andExpect(flash().attribute("message", "この患者は登録済みです"));
  }
}
