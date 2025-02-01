package com.ordering.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ordering.entity.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

  @Autowired
  UserMapper userMapper;

  User userSample;


  @BeforeEach
  void setUp() {
    String now = String.valueOf(LocalDateTime.now());
    userSample = new User(
        "addId",
        "太郎",
        "2024-12-30",
        '1',
        "pass",
        "アシュ",
        now,          // createdAt
        null,          // updatedBy
        null,          // updatedAt
        null,          // deletedBy
        null
    );

  }

  @Test
  @Sql(scripts = "./insertUsersList.sql")
  void testInsert() {
    userMapper.insert(userSample);
    User checkUser = userMapper.findByName(userSample.getName());
    assertEquals(checkUser.getBirthday(), userSample.getBirthday());
    assertEquals(checkUser.getGender(), userSample.getGender());
  }

  @Test
  @Sql(scripts = "classpath:com/ordering/repository/insertUsersList.sql")
  void testFindByName() {
    User checkUser = userMapper.findByName("たろう");
    assertEquals("2024-12-10", checkUser.getBirthday());
    assertEquals('1', checkUser.getGender());
  }
}
