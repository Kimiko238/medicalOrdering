package com.ordering.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ordering.config.SecurityConfig;
import com.ordering.entity.FormInspectionOrderDto;
import com.ordering.service.OrderService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
public class OrderControllerTest {

  FormInspectionOrderDto sampleOrder;
  List<FormInspectionOrderDto> mockOrderList;
  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  OrderService orderService;

  @MockitoBean
  Authentication authentication;

  @BeforeEach
  void setUp() {
    sampleOrder = new FormInspectionOrderDto(
        "id",
        "1",
        1,
        "テスト患者",
        "採血",
        List.of(),
        "2024-01-01 10:00:00",
        "未実施",
        "詳細"
    );
  }


  //検査依頼：一覧画面遷移時、のテスト
  @Test
  void test_GET_index() throws Exception {
    mockOrderList = List.of(
        new FormInspectionOrderDto(
            "id1",
            "2",
            2,
            "テスト患者1",
            "心電図",
            List.of(),
            "2024-01-01 10:00:00",
            "未実施",
            "詳細1"
        ),
        new FormInspectionOrderDto(
            "id2",
            "3",
            2,
            "テスト患者1",
            "心電図",
            List.of(),
            "2024-01-01 10:00:00",
            "未実施",
            "詳細1"));
    Mockito.when(orderService.findAll()).thenReturn(mockOrderList);

    mockMvc.perform(
            get("/")
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("formInspectionOrdersDto", mockOrderList))
        .andExpect(model().attribute("zeroList", Boolean.FALSE));
  }

  //検査依頼：詳細画面遷移時、のテスト
  @Test
  void test_GET_orderDetails() throws Exception {
    Mockito.when(orderService.findById("id")).thenReturn(sampleOrder);
    mockMvc.perform(
            get("/orderDetails/{id}", sampleOrder.getOrderId())
                .with(csrf())
                .with(user("test").password("testTest"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderDetails"))
        .andExpect(model().attribute("formInspectionOrderDto", sampleOrder));
  }

  //  検査依頼：編集画面への遷移時、のテスト
  @Test
  void test_GET_edit() throws Exception {
    Mockito.when(orderService.findById("id")).thenReturn(sampleOrder);
    mockMvc.perform(
            get("/edit/{id}", sampleOrder.getOrderId())
                .with(csrf())
                .with(user("test").password(("pass")))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderForm"))
        .andExpect(model().attribute("formInspectionOrderDto", sampleOrder));
  }

  //  検査依頼：新規登録画面遷移時、のテスト
  @Test
  void test_GET_newInspection() throws Exception {
    doReturn(sampleOrder).when(orderService)
        .settingOrder(anyInt());
    mockMvc.perform(
            get("/newOrder")
                .with(csrf())
                .with(user("test").password("pass"))
                .param("showId", String.valueOf(1000))

        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("orderForm"))
        .andExpect(model().attribute("formInspectionOrderDto", sampleOrder));
  }

  //  検査依頼：新規登録時にバリデーションOKだった、テスト
  @Test
  void test_POST_newOrderSubmitOk() throws Exception {
    doReturn(sampleOrder).when(orderService)
        .save(any(FormInspectionOrderDto.class), any(Authentication.class));
    mockMvc.perform(
            post("/newOrderSubmit")
                .with(csrf())
                .with(user("test").password("pass"))
                .param("id", "id")
                .param("details", "検査詳細")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(flash().attribute("message", "登録しました"))
        .andExpect(flash().attributeExists("formInspectionOrderDto"));

  }

  //  検査依頼：新規登録時にバリデーションNGだった、テスト
  @Test
  void test_POST_newSubmitNG() throws Exception {
    mockMvc.perform(
            post("/newOrderSubmit")
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
    doReturn(sampleOrder).when(orderService)
        .edit(any(FormInspectionOrderDto.class), any(Authentication.class));
    mockMvc.perform(
            post("/editOrderSubmit")
                .with(csrf())
                .with(user("test").password("pass"))
                .param("id", "id")
                .param("details", "テスト検査")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/"))
        .andExpect(flash().attribute("message", "更新しました"))
        .andExpect(flash().attributeExists("formInspectionOrderDto"));

  }

  //検査依頼：編集登録時にバリデーションNGだった、テスト
  @Test
  void test_POST_editSubmitNG() throws Exception {
    mockMvc.perform(
            post("/editOrderSubmit")
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
            get("/delete/{id}", sampleOrder.getOrderId())
                .with(csrf())
                .with(user("test").password("pass"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("index"))
        .andExpect(model().attribute("message", "削除しました"));
  }
}
