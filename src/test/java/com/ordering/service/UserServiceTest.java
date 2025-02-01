package com.ordering.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ordering.entity.User;
import com.ordering.repository.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  //  検証したいserviceクラスを選択
  @InjectMocks
  UserService userService;

  @Mock
  UserMapper userMapper;

  @Mock
  PasswordEncoder passwordEncoder;

  private User userSample;

  @BeforeEach
  void setUp() {
    userSample = new User(
        "id",
        "taro",
        "2024-12-30",
        '1',
        "pass",
        "アシュ",
        null,          // createdAt
        null,          // updatedBy
        null,          // updatedAt
        null,          // deletedBy
        null
    );
  }


  @Test
  void testSave() {
//  戻り値がない、voidで返却する処理（実際に登録しないためのダミー）
    doNothing().when(userMapper).insert(userSample);
//  サービスクラスを呼び出す
    userService.save(userSample);
//  検証（モックが呼び出された事をカウントする）
    verify(userMapper, times(1)).insert(userSample);
  }

  @Test
  void testFindByName() {
    String name = "taro";
    UserDetails userDetailsSample = new UserPrincipal(userSample);
    doReturn(userDetailsSample).when(userMapper).findByName(name);
    UserDetails userDetailsDto = userService.loadUserByUsername(name);
    assertEquals(userDetailsDto.getUsername(), userDetailsSample.getUsername());
    assertEquals(userDetailsDto.getPassword(), userDetailsSample.getPassword());

  }


}


