package com.ordering.repository;

import com.ordering.model.Patient;
import java.time.LocalDate;
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
  Patient selectByShowId(@Param("showId") Integer showId);

  Patient selectById(String id);

  Patient selectByNameAndBirthday(String name, LocalDate birthday);

  void update(Patient patient);
}

