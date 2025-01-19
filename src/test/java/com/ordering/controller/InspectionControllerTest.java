package com.ordering.controller;

import com.ordering.config.SecurityConfig;
import com.ordering.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(InspectionController.class)
@Import(SecurityConfig.class)
public class InspectionControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  InspectionService inspectionService;

//  @Test
//  void test_GET_inspectionDetails() {
//    Inspection targetInspection = new Inspection();
//    Mockito.when(inspectionService.findById("id")).thenReturn(targetInspection);
//    mockMvc.perform(
//        get("/inspectionDetails/{id}")
//    );
//  }
}
