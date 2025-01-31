package com.ordering.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ordering.config.SecurityConfig;
import com.ordering.entity.ResponseInspectionOrderDto;
import com.ordering.model.Order;
import com.ordering.service.OrderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerTest {

  Order sampleOrder;
  List<Order> mockOrderList;
  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  OrderService orderService;

  @MockitoBean
  Authentication authentication;

  @BeforeEach
  void setUp() {
    sampleOrder = new Order(
        "id",
        "検査",
        "2024-12-22 11:05:15",
        "検査の詳細",
        "アシュ",
        null,// createdAt
        null,          // updatedBy
        null,          // updatedAt
        null,          // deletedBy
        null
    );
  }


  //検査依頼：一覧画面遷移時、のテスト
  @Test
  void test_GET_index() throws Exception {
    mockOrderList = List.of(
        new Order(
            "id1",
            "検査1",
            "2024-01-01 10:00:00",
            "詳細1",
            "太郎",
            null,
            null,
            null,
            null,
            null),
        new Order(
            "id2",
            "検査2",
            "2024-01-02 10:00:00",
            "詳細2",
            "次郎",
            null,
            null,
            null,
            null,
            null));
    Mockito.when(orderService.findAll()).thenReturn(mockOrderList);

    mockMvc.perform(
            get("/")
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("inspections", mockOrderList))
        .andExpect(model().attributeExists("authentication"));
    ;
  }

  //検査依頼：詳細画面遷移時、のテスト
  @Test
  void test_GET_inspectionDetails() throws Exception {
    Mockito.when(orderService.findById("id")).thenReturn(sampleOrder);
    mockMvc.perform(
            get("/inspectionDetails/{id}", sampleOrder.getId())
                .with(csrf())
                .with(user("test").password("testTest"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("inspectionDetails"))
        .andExpect(model().attribute("inspection", sampleOrder));
  }

  //  検査依頼：編集画面への遷移時、のテスト
  @Test
  void test_GET_edit() throws Exception {
    Mockito.when(orderService.findById("id")).thenReturn(sampleOrder);
    mockMvc.perform(
            get("/edit/{id}", sampleOrder.getId())
                .with(csrf())
                .with(user("test").password(("pass")))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderForm"))
        .andExpect(model().attribute("inspection", sampleOrder));
  }

  //  検査依頼：新規登録画面遷移時、のテスト
  @Test
  void test_GET_newInspection() throws Exception {
    mockMvc.perform(
            get("/newInspection")
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderForm"));
  }

  //  検査依頼：新規登録時にバリデーションOKだった、テスト
  @Test
  void test_POST_newSubmitOk() throws Exception {
    doNothing().when(orderService)
        .save(any(ResponseInspectionOrderDto.class), any(Authentication.class));
    doReturn(sampleOrder).when(orderService).findById(any(String.class));
    mockMvc.perform(
            post("/newInspectionSubmit")
                .with(csrf())
                .with(user("test").password("pass"))
                .param("id", "id")
                .param("details", "検査詳細")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(flash().attribute("message", "登録しました"))
        .andExpect(flash().attributeExists("inspectionDto"));

    verify(orderService, times(1)).save(any(ResponseInspectionOrderDto.class),
        any(Authentication.class));
  }

  //  検査依頼：新規登録時にバリデーションNGだった、テスト
  @Test
  void test_POST_newSubmitNG() throws Exception {
    mockMvc.perform(
            post("/newInspectionSubmit")
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderForm"))
    ;
  }

  //検査依頼：編集登録時にバリデーションOKだった、テスト
  @Test
  void test_POST_editSubmitOK() throws Exception {
    doNothing().when(orderService)
        .edit(any(Order.class), any(Authentication.class));
    doReturn(sampleOrder).when(orderService).findById(any(String.class));
    mockMvc.perform(
            post("/editInspectionSubmit")
                .with(csrf())
                .with(user("test").password("pass"))
                .param("id", "id")
                .param("details", "テスト検査")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(flash().attribute("message", "更新しました"))
        .andExpect(flash().attributeExists("inspectionCorrection"));

    verify(orderService, times(1)).edit(any(Order.class),
        any(Authentication.class));
  }

  //検査依頼：編集登録時にバリデーションNGだった、テスト
  @Test
  void test_POST_editSubmitNG() throws Exception {
    mockMvc.perform(
            post("/editInspectionSubmit")
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderForm"));
  }

  //依頼の削除、のテスト
  @Test
  void test_GET_delete() throws Exception {
    Mockito.when(orderService.findById("id")).thenReturn(sampleOrder);
    mockMvc.perform(
            get("/delete/{id}", sampleOrder.getId())
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("authentication"))
        .andExpect(model().attribute("message", "削除しました"));
  }
}
