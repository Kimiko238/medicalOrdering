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
import com.ordering.entity.Patient;
import com.ordering.service.PatientService;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PatientController.class)
@Import(SecurityConfig.class)
public class PatientControllerTest {


  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  PatientService patientService;

  @MockitoBean
  Authentication authentication;


  @BeforeEach
  void setUp() {
    when(patientService.findById(1))
        .thenReturn(new Patient(
            "id",
            3,
            "小町",
            "1988-10-5",
            "2",
            "アシュ",
            null,
            null,
            null,
            null,
            null));
  }

  @Test
  void test_GET_newPatient() throws Exception {
    mockMvc.perform(
            get("/newPatient")
                .with(csrf())
                .with(user("testUser2").password("USERaa"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientForm"));
  }

  @Test
  void test_GET_editPatient() throws Exception {
    mockMvc.perform(
            get("/editPatient")
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name(""));
  }

  //  患者検索の遷移、のテスト
  @Test
  void test_GET_searchPatient() throws Exception {
    mockMvc.perform(
            get("/searchPatient")
                .param("showId", "1")
                .with(csrf())
                .with(user("test3").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientDetails"))
        .andExpect(model().attributeExists("patient"))
        .andExpect(model().attribute("readOnly", true));
  }

  //  患者新規作成（バリデーションOK）の場合のテスト
  @Test
  void test_POST_createPatientOK() throws Exception {
    doReturn("登録しました").when(patientService).checkNameAndBirthday(any(), any());
    mockMvc.perform(
            post("/createPatient")
                .with(csrf())
                .with(user("testman").password("eivkb"))
                .param("id", "id")
                .param("showId", "1")
                .param("name", "akane")
                .param("birthday", "2021-3-19")
                .param("gender", "2")
                .param("createdBy", "アシュ")
                .param("createdAt", "")
                .param("updatedBy", "")
                .param("updatedAt", "")
                .param("deletedBy", "")
                .param("deletedAt", ""))

        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:newPatientView"))
        .andExpect(flash().attribute("message", "登録しました"));
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
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:newPatientView"));
  }
}
