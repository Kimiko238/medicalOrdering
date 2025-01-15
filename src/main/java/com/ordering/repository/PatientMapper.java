package com.ordering.repository;

import com.ordering.model.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PatientMapper {

  /**
   * 患者情報を登録します。
   */
  void insert(Patient patient);

  /**
   * 指定されたIDに対応する「すること」を取得します。
   */
  Patient selectById(@Param("showId") Integer showId);


  Patient selectByNameAndBirthday(String name, String birthday);
}

