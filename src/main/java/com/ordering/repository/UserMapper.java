package com.ordering.repository;

import com.ordering.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

  void insert(User user);

  User findByName(String username);

}


