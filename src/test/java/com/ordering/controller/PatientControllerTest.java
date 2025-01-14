package com.ordering.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ordering.service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.SecurityConfig;
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

  @Test
  void test_GET_newPatientView() throws Exception {
    mockMvc.perform(
            get("/newPatientView")
                .with(csrf())
                .with(user("アシュ").password("238"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("patientForm"));
  }

}
