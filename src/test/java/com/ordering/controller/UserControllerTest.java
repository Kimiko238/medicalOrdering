package com.ordering.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.ordering.config.SecurityConfig;
import com.ordering.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean  // UserServiceのモックを作成
  UserService userService;


  //  ユーザーのログイン画面遷移、のテスト
  @Test
  void test_GET_login() throws Exception {
    mockMvc.perform(
            get("/login")
                .with(csrf())
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("login"));
  }


  //  ユーザー登録画面遷移、テスト
  @Test
  void test_GET_userRegister() throws Exception {
    mockMvc.perform(
            get("/userRegister")
                .with(csrf())
//              .with(user("testUser").password("USER"))
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("userRegister"));
  }

  //  新規登録処理、のテスト
  @Test
  void test_POST_createUser() throws Exception {
    mockMvc.perform(
            post("/createUser")
                .with(csrf())
                .param("id", "id")
                .param("name", "taro")
                .param("birthday", "2023-10-22")
        )
        .andExpect(status().is2xxSuccessful())
        .andExpect(view().name("login"));
  }
}
