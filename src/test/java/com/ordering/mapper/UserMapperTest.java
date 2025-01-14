package com.ordering.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ordering.model.User;
import com.ordering.repository.UserMapper;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserMapperTest {

  @Autowired
  UserMapper userMapper;

  @Autowired
  JdbcTemplate jdbcTemplate;

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
  @Sql(scripts = "classpath:com/ordering/repository/insertUsersList.sql")
  void testInsert() {
    userMapper.insert(userSample);
    User checkUser = userMapper.findByName(userSample.getName());
    System.out.println(checkUser);
    assertEquals(checkUser.getBirthday(), userSample.getBirthday());
    assertEquals(checkUser.getGender(), userSample.getGender());
  }
}
